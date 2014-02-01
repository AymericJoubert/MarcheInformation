package bdd;

import java.util.ArrayList;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

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

    public void getSymetriquesMarkets() throws SQLException, NamingException{
    	getSymetriquesMarkets(idMarche);
    }

    public void getSymetriquesMarkets(int id) throws SQLException, NamingException{
    	getMarket(id);
    	getMarket();
    }

    public void getMarket(int id) throws SQLException, NamingException{
    	idMarche = id;
    	Connection con = null;
    	try{
	    	con = ConnectionMarket.getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT m.question,u.user_name,o.quantite,o.valeur,o.offre_date,m.inverse,m.marche_id FROM (marche as m LEFT JOIN offre as o ON o.marche = m.marche_id) LEFT JOIN users as u ON u.user_id = o.acheteur WHERE m.marche_id = ?;");
			pst.setInt(1,idMarche);
			ResultSet rs = pst.executeQuery();

			if(rs.next()){
    			Market market = new Market();
    			market.setQuestion(rs.getString(1));
                market.setMarcheId(rs.getInt(7));
    			marches.add(market);
    			idMarche = rs.getInt(6);
    			market.getOffres().add(setOffres(rs));

    			while(rs.next()){
    				market.getOffres().add(setOffres(rs));
    			}
        }
		}finally{
			con.close();
		}
    }

    private Offre setOffres(ResultSet rs) throws SQLException{
    	Offre result = new Offre();
    	String[] jour,heure,tmp;
    	Calendar cal = Calendar.getInstance();
    	result.setAcheteur(rs.getString(2));
    	result.setQuantite(rs.getInt(3));
    	result.setValeur(rs.getInt(4));
    	tmp = rs.getString(5).split(" ");
    	if(tmp.length > 0){
    		jour = tmp[0].split("-",3);
    		heure = tmp[1].split(":",3);
    		cal.set(Integer.parseInt(jour[0]),Integer.parseInt(jour[1]),Integer.parseInt(jour[2]),Integer.parseInt(heure[0]),Integer.parseInt(heure[1]),Integer.parseInt(heure[2]));
    		result.setOffreDate(cal);
    	}


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
			PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche WHERE fermeture > ? ORDER BY ouverture, question ASC LIMIT 5;");
            pst.setTimestamp(1,new Timestamp(new Date().getTime()));
			ResultSet rs = pst.executeQuery();
			ret = "<ul>";
			while(rs.next()){
			    ret += "<a href='index.jsp?market=";
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

    public String getHistoriqueMarkets() throws SQLException, NamingException,ParseException{
        Connection con = null;
        String ret = "";

        try{
            con = ConnectionMarket.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche WHERE fermeture < ? ORDER BY fermeture, ouverture , question ASC LIMIT 5;");
            //PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche WHERE fermeture < '2014-02-13 00:00:00' ORDER BY fermeture, ouverture , question ASC LIMIT 5;");
            pst.setTimestamp(1,new Timestamp(new Date().getTime()));
            ResultSet rs = pst.executeQuery();
            ret = "<ul>";
            while(rs.next()){
                ret += "<a href='index.jsp?market=";
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