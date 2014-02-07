package bdd;

import java.util.ArrayList;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;
import mapping.*;

public class ManagerMarket{
	// id d'un des carnets de commmande
    private int idMarche;
    // les deux carnets sysmetriques
    private ArrayList<Market> marches;
    private Market marche;



    public ManagerMarket(){
    	idMarche = 1;
		marche = new Market();
        marches = new ArrayList<Market>();
    }

    public void getSymetriquesMarkets() throws SQLException, NamingException {
    	getSymetriquesMarkets(idMarche);
    }

    public void getSymetriquesMarkets(int id) throws SQLException, NamingException {
    	getMarket(id);
    }

    public void focusMarket(int id) throws SQLException, NamingException {
    	idMarche = id;
    	Connection con = null;
        int valeur,quantite;
        String acheteur, offreDate;
    	try{
	    	con = ConnectionMarket.getConnection();
            
			PreparedStatement pst = con.prepareStatement("SELECT m.question,u.user_name,count(valeur) as quantite,o.valeur,to_char(o.offre_date,'DD/MM/YYYY HH24:MI:SS') as date,m.inverse,m.marche_id FROM (marche as m LEFT JOIN offre as o ON o.marche = m.marche_id) LEFT JOIN users as u ON u.user_id = o.acheteur WHERE m.marche_id = ? GROUP BY m.marche_id,m.inverse,m.question,o.valeur,o.offre_date,u.user_name ORDER BY o.valeur ASC;");
			pst.setInt(1,idMarche);
			ResultSet rs = pst.executeQuery();

			if(rs.next()){
    			marche.setQuestion(rs.getString(1));
                marche.setMarcheId(rs.getInt(7));
                marche.setInverse(rs.getInt(6));
    			marches.add(marche);
    			idMarche = rs.getInt(6);
                acheteur = rs.getString(2);
                quantite = rs.getInt(3);
                valeur = rs.getInt(4);
                offreDate = rs.getString(5);
    			marche.getOffres().add(setOffres(acheteur, offreDate, quantite,valeur));

    			while(rs.next()){
                    idMarche = rs.getInt(6);
                    acheteur = rs.getString(2);
                    quantite = rs.getInt(3);
                    valeur = rs.getInt(4);
                    offreDate = rs.getString(5);
                    marche.getOffres().add(setOffres(acheteur, offreDate, quantite,valeur));
    			}
            }
		}finally{
            if(con != null)
			    con.close();
		}
    }

    public Market getLeMarche(){
        return marche;
    }

    public void focusMarket() throws SQLException, NamingException {
        focusMarket(idMarche);
    }

// Marche avant
    // public void getMarket(int id) throws SQLException, NamingException {
    //     idMarche = id;
    //     Connection con = null;
    //     int qute,val;
    //     try{
    //         con = ConnectionMarket.getConnection();
    //         /* la requête qui va bien
    //         * 
    //         */
    //         /* cette requête est plus faite pour afficher le détail dans la rubrique historique ou détail */
    //         PreparedStatement pst = con.prepareStatement("SELECT m.question,count(valeur) as quantite,o.valeur,m.inverse,m.marche_id FROM marche as m LEFT JOIN offre as o ON o.marche = m.marche_id WHERE m.marche_id = ? GROUP BY m.inverse,m.question,o.valeur,m.marche_id ORDER BY o.valeur ASC;");
    //         pst.setInt(1,idMarche);
    //         ResultSet rs = pst.executeQuery();

    //         if(rs.next()){
    //             Market market = new Market();
    //             market.setQuestion(rs.getString(1));
    //             market.setMarcheId(rs.getInt(5));
    //             market.setInverse(rs.getInt(4));
    //             marches.add(market);
    //             idMarche = market.getInverse();
    //             qute = rs.getInt(2);
    //             val  = rs.getInt(3);
    //             market.getOffres().add(setOffres(qute,val));

    //             while(rs.next()){
    //                 qute = rs.getInt(2);
    //                 val  = rs.getInt(3);
    //                 market.getOffres().add(setOffres(qute,val));
    //             }
    //     }
    //     }finally{
    //         if(con != null)
    //             con.close();
    //     }
    // }

    public void getMarket(int id) throws SQLException, NamingException {
        idMarche = id;
        Connection con = null;
        int qute,val;
        boolean achat;
        try{
            con = ConnectionMarket.getConnection();
            /* la requête qui va bien
            * 
            */
            /* cette requête est plus faite pour afficher le détail dans la rubrique historique ou détail */
            PreparedStatement pst = con.prepareStatement("SELECT m.question,count(valeur) as quantite,o.valeur, o.achat, m.inverse FROM marche as m LEFT JOIN offre as o ON o.marche = m.marche_id WHERE m.marche_id = ? AND o.acheteur_inverse is null GROUP BY m.inverse,m.question,o.valeur,m.marche_id,o.achat,o.acheteur_inverse ORDER BY o.valeur,o.achat ASC;");
            pst.setInt(1,idMarche);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                marche.setQuestion(rs.getString(1));
                marche.setMarcheId(idMarche);
                marche.setInverse(rs.getInt(5));
                qute = rs.getInt(2);
                val  = rs.getInt(3);
                achat = rs.getBoolean(4);
                // marche.getOffres().add(setOffres(qute,val,achat));
                setOffres(qute,val,achat);
                while(rs.next()){
                    qute = rs.getInt(2);
                    val  = rs.getInt(3);
                    achat = rs.getBoolean(4);
                    // marche.getOffres().add(setOffres(qute,val,achat));
                    setOffres(qute,val,achat);
                }
                    marches.add(marche);
            }
        }finally{
            if(con != null)
                con.close();
        }
    }

    private Offre setOffres(String acheteur, String offreDate, int quantite, int valeur){
    	Offre result = new Offre();

    	result.setAcheteur(acheteur);
    	result.setQuantite(quantite);
    	result.setValeur(valeur);
        //result.setOffreDate(offreDate);
        //result.setOffreDate(rs.getString(5));
        // if(rs.getString(5).matches("\.")){
             // tmp = rs.getString(5).split(Pattern.quote("."),2);
             // tmp = tmp[0].split(" ");
        // }
        // else
        result.setOffreDate(getDateCalendar(offreDate));
  
		return result;

    }

     private Offre setOffres(int quantite, int valeur, boolean achat){
        Offre result = new Offre();

        result.setQuantite(quantite);
        result.setValeur(valeur);
        result.setAchat(achat);
        if(achat){
            marche.getOffres().add(result); 
        }else{
            marche.getVentes().add(result);
        }
        return result;

    }

    private Calendar getDateCalendar(String date){
        String[] tmp,heure,jour;
        Calendar cal = null;
        tmp = date.split(" ");
        if(tmp.length > 0){
            cal = Calendar.getInstance();
            jour = tmp[0].split("/",3);
            heure = tmp[1].split(":",3);
            cal.set(Integer.parseInt(jour[2]),Integer.parseInt(jour[1]),Integer.parseInt(jour[0]),Integer.parseInt(heure[0]),Integer.parseInt(heure[1]),Integer.parseInt(heure[2]));
            return cal;
        }
        return cal;
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
            if(con != null){
    			con.close();
            }
		}
		return ret;
    }

    public String getHistoriqueMarkets() throws SQLException, NamingException,ParseException{
        Connection con = null;
        String ret = "";

        try{
            con = ConnectionMarket.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche WHERE fermeture < ? ORDER BY fermeture, question, ouverture ASC LIMIT 5;");
            //PreparedStatement pst = con.prepareStatement("SELECT marche_id, question FROM marche WHERE fermeture < '2014-02-13 00:00:00' ORDER BY fermeture, ouverture , question ASC LIMIT 5;");
            pst.setTimestamp(1,new Timestamp(new Date().getTime()));
            ResultSet rs = pst.executeQuery();
            ret = "<ul>";
            while(rs.next()){
                ret += "<a href='marche.jsp?market=";
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