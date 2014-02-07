import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.sql.*;
import java.sql.*;
import java.util.Date;

import bdd.*;
import mapping.Trader;

@WebServlet("OffreManager")
public class OffreManager extends HttpServlet{

	private int qute,idMarket,price;
	private Trader seller;

	public void init() throws ServletException {
		super.init();
		qute = -1;
		idMarket = -1;
		price = -1;
		seller = null;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException{
		// récupérer la nouvelle offre d'achat : quantité, prix, acheteur
		String nomSeller;
		// Penser au XSS
		qute = Integer.parseInt(req.getParameter("quantite"));
		price = Integer.parseInt(req.getParameter("prix"));
		idMarket = Integer.parseInt(req.getParameter("market"));
		nomSeller = req.getRemoteUser();
		seller = new Trader(nomSeller);

		Connection con = null;
		try{
			con = ConnectionMarket.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			for (int i = 0; i < qute; i++){
				if (Integer.parseInt(seller.getCash()) >= price) {
					pst = con.prepareStatement("INSERT INTO offre(valeur,marche,acheteur,offre_date) VALUES(?,?,?,?)");
					pst.setInt(1,price);
					pst.setInt(2,idMarket);
					pst.setInt(3,Integer.parseInt(seller.getUserId()));
					pst.setTimestamp(4,new Timestamp(new Date().getTime()));
					pst.executeUpdate();
					
					pst = con.prepareStatement("SELECT o.offre_id, o.acheteur, m.inverse, o.valeur FROM offre o LEFT JOIN marche m ON m.marche_id = o.marche WHERE ? = m.marche_id AND ? >= o.valeur AND o.achat = false ORDER BY o.valeur, o.offre_date LIMIT 1");
					pst.setInt(1,idMarket);
					pst.setInt(2,price);
					rs = pst.executeQuery();
					
					if(rs.next())
						match(idMarket, price, seller, rs, con, pst);
					else{						
						notMatch(qute, price, idMarket, rs.getInt(3), seller, con, pst);
					}
					seller.reloadCash();
				}
		}
		req.setAttribute("market",idMarket);
		res.sendRedirect("index.jsp");
		}catch(Exception e){}
		finally{
			try{
				con.close();
			}catch(SQLException sqle1){
				try{
					res.sendRedirect("erreur.jsp");
				}
				catch(Exception e2){}
			}
		}
	}

	public void notMatch(int qute, int price, int idMarket, int marcheInverse, Trader seller, Connection con, PreparedStatement pst) throws SQLException {

		for(int i = 0; i < qute; i++){
			pst = con.prepareStatement("INSERT INTO offre(valeur,marche,acheteur,offre_date,achat) VALUES(?,?,?,?,false)");
			pst.setInt(1,100 - price);
			pst.setInt(2,marcheInverse);
			pst.setInt(3,Integer.parseInt(seller.getUserId()));
			pst.setTimestamp(4,new Timestamp(new Date().getTime()));
			pst.executeUpdate();
		}

	}

	public void manageCash(Connection con, int idUser, int cashToRemove) throws SQLException{
		PreparedStatement pst = con.prepareStatement("UPDATE users SET cash = cash - ? WHERE user_id = ?");
		pst.setInt(1, cashToRemove);
		pst.setInt(2, idUser);
		pst.executeUpdate();
	}

	public void match(int idMarket, int initPrice, Trader seller, ResultSet rs, Connection con, PreparedStatement pst) throws SQLException {
			int offre, vendeur, marcheInverse, inversePrice;
			offre = rs.getInt(1);
			vendeur = rs.getInt(2);
			marcheInverse = rs.getInt(3);
			price = rs.getInt(4);
			//prix reel paye
			inversePrice = 100 - price;
			// on achete la vente qui match
			pst = con.prepareStatement("UPDATE offre SET acheteur_inverse = ?, valeur = ? WHERE offre_id = (SELECT offre_id FROM offre WHERE valeur = ? AND acheteur = ? AND marche = ? LIMIT 1)");
			pst.setInt(1,Integer.parseInt(seller.getUserId()));
			pst.setInt(2,price);
			pst.setInt(3,inversePrice);
			pst.setInt(4,vendeur);
			pst.setInt(5,marcheInverse);
			pst.executeUpdate();
			manageCash(con, Integer.parseInt(seller.getUserId()), price);
			// on fait la meme chose sur le marche inverse, pour le vendeur de l'offre precedente
			pst = con.prepareStatement("UPDATE offre SET acheteur_inverse = ?, valeur = ? WHERE offre_id = (SELECT offre_id FROM offre WHERE valeur = ? AND acheteur = ? AND marche = ? ORDER BY offre_date LIMIT 1)");
			pst.setInt(1,vendeur);
			pst.setInt(2,inversePrice);
			pst.setInt(3,initPrice);
			pst.setInt(4,Integer.parseInt(seller.getUserId()));
			pst.setInt(5,idMarket);
			pst.executeUpdate();
			manageCash(con, vendeur, inversePrice);
			// supprime l'offre de vente qui a matche
			pst = con.prepareStatement("DELETE FROM offre WHERE marche = ? AND acheteur = ? AND achat = false");
			pst.setInt(1,idMarket);
			pst.setInt(2,vendeur);
			pst.executeUpdate();
	}
}