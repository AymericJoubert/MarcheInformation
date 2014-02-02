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
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException{
		// récupérer la nouvelle offre d'achat : quantité, prix, acheteur
		String nomSeller;
		int qute,idMarket,price;

		// Penser au XSS
		qute = Integer.parseInt(req.getParameter("quantite"));
		price = Integer.parseInt(req.getParameter("prix"));
		idMarket = Integer.parseInt(req.getParameter("market"));
		nomSeller = req.getRemoteUser();
		Trader seller = new Trader(nomSeller);

		Connection con = null;
		try{
			ManagerMarket marches = new ManagerMarket();
			marches.getSymetriquesMarkets(idMarket);
			con = ConnectionMarket.getConnection();
			PreparedStatement pst = con.prepareStatement("INSERT INTO offre(quantite,valeur,marche,acheteur,offre_date) VALUES(?,?,?,?,?)");
			pst.setInt(1,qute);
			pst.setInt(2,price);
			pst.setInt(3,idMarket);
			pst.setInt(4,Integer.parseInt(seller.getUserId()));
			pst.setTimestamp(5,new Timestamp(new Date().getTime()));
			pst.executeQuery();
			req.setAttribute("market",idMarket);
			res.sendRedirect("index.jsp");
		}catch(Exception e){}
		finally{
			try{
				con.close();
			}catch(Exception e1){
				try{
				res.sendRedirect("erreur.jsp");
				}
				catch(Exception e2){}
			}
		}
	}
}