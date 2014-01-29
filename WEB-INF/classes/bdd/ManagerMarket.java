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
    private ArrayList<Marche> marches;


    public Market(){
    	idMarche = 1;
		offres = new ArrayList<Offre>();
		offreInverse = new ArrayList<Offre>();
		marches = new ArrayList<Marche>();
    }

    public void getMarket(int market) throws SQLException, NamingException{
		Connection con = Connection.getConnection();
		PreparedStatement pst = con.prepareStatement("SELECT m.question,o.acheteur,o.quantite,o.valeur,o.offre_date,m.inverse FROM marche as m LEFT JOIN offre as o ON o.marche = m.marche_id WHERE m.marche_id = ?;");
		pst.setInt(1,market);
		ResultSet rs = pst.executeQuery();

		// si la requete ne renvoie pas de resultat

		if(!rs.next()) return null;
		Market market = new Market();
		market.setQuestion(rs.getString(1));
		marches.add(market);
		idMarche = rs.getString(6);
		market.getOffres().add(setOffre(rs));
		while(rs.next()){
			market.getOffres().add(setOffre(rs));
		}
		con.close();
    }

    private setOffres(ResultSet rs){
    	Offre result = new Offre();
    	result.setAcheteur(rs.getString(2));
    	result.setQuantite(rs.getInt(3));
    	result.setValeur(rs.getInt(4));
    	result.setDate(rs.getString(5));

    	return result;
    }

    public void getMarket() throws SQLException, NamingException{
    	// il faudrait faire une petite requete pour récupérer le premier marché en cours
    	getMarket(idMarche);
    }

    public getMarches(){
    	return marches;
    }

    public String getLastMarkets() throws SQLException, NamingException{
	Connection con = getConnection();
	PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche LIMIT 5;");
	ResultSet rs = pst.executeQuery();
	String ret = "<ul>";
	while(rs.next()){
	    ret += "<a href='marche?id=";
	    ret += rs.getString(1);
	    ret += "'><li>";
	    ret += rs.getString(2);
	    ret += "</li></a>\n";
	}
	ret+="</ul>";
	con.close();
	return ret;
    }
}