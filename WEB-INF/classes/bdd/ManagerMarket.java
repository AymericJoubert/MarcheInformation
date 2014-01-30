package bdd;

import java.util.ArrayList;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

import mapping.*;

public class ManagerMarket{
	// id d'un des carnets de commmande
    private int idMarche;
    // les deux carnets sysmetriques
    private ArrayList<Market> marches;


    public ManagerMarket(){
    	idMarche = 1;
		marches = new ArrayList<Market>();
    }

    public void getMarket(int id) throws SQLException, NamingException{
    	idMarche = id;
    	Connection con = null;
    	try{
	    	con = ConnectionMarket.getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT m.question,o.acheteur,o.quantite,o.valeur,o.offre_date,m.inverse FROM marche as m LEFT JOIN offre as o ON o.marche = m.marche_id WHERE m.marche_id = ?;");
			pst.setInt(1,idMarche);
			ResultSet rs = pst.executeQuery();

			rs.next();
			Market market = new Market();
			market.setQuestion(rs.getString(1));
			marches.add(market);
			idMarche = rs.getInt(6);
			market.getOffres().add(setOffres(rs));

			while(rs.next()){
				market.getOffres().add(setOffres(rs));
			}
		}finally{
			con.close();
		}
    }

    private Offre setOffres(ResultSet rs) throws SQLException{
    	Offre result = new Offre();
    	result.setAcheteur(rs.getString(2));
    	result.setQuantite(rs.getInt(3));
    	result.setValeur(rs.getInt(4));
    	result.setOffreDate(new Date(rs.getLong(5)));

    	return result;
    }

    public void getMarket() throws SQLException, NamingException{
    	// il faudrait faire une petite requete pour récupérer le premier marché en cours
    	getMarket(idMarche);
    }

    public ArrayList<Market> getMarches(){
    	return marches;
    }

    public String getLastMarkets() throws SQLException, NamingException{
    	Connection con = null;
    	String ret = "";
	    try{
			con = ConnectionMarket.getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche LIMIT 5;");
			ResultSet rs = pst.executeQuery();
			ret = "<ul>";
			while(rs.next()){
			    ret += "<a href='marche?id=";
			    ret += rs.getString(1);
			    ret += "'><li>";
			    ret += rs.getString(2);
			    ret += "</li></a>\n";
			}
			ret+="</ul>";
		}finally{
			con.close();
		}
		return ret;
    }
}