package bdd;

import java.util.ArrayList;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class Market{

    private String titre,table,tableInverse;
    private int idMarche,idInverse;

 // private int marcheId, createur, inverse;
 //    private String question;
 //    private Date ouverture, fermeture;

 //    public int getMarcheId(){
 //    	return marcheId;
 //    }

 //    public void setMarcheId(int m){
 //    	marcheId = m;
 //    }

 //    public int getCreateur(){
 //    	return createur;
 //    }

 //    public void setCreateur(int c){
 //    	createur = c;
 //    }

 //    public int getInverse(){
 //    	return inverse;
 //    }

 //    public void setInverse(int i){
 //    	inverse = i;
 //    }

 //    public String getQuestion(){
	// return question;
 //    }

 //    public void setQuestion(String q){
	// question = q;
 //    }

 //    public Date getOuverture(){
 //    	return ouverture;
 //    }

 //    public void setOuverture(Date o){
 //    	ouverture = o;
 //    }

 //    public Date getFermeture(){
 //    	return fermeture;
 //    }

 //    public void setFermeture(Date f){
 //    	fermeture = f;
 //    }
    public Market(){
    	idMarche = 1;
		titre = null;
		table = null;
    }

    public Connection getConnection() throws NamingException,SQLException{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("marche");

		return ds.getConnection();
    }

    public void getMarket(int market) throws SQLException, NamingException{
		Connection con = getConnection();
		PreparedStatement pst = con.prepareStatement("SELECT m.question,o.acheteur,o.quantite,o.valeur,o.offre_date,m.inverse FROM marche as m LEFT JOIN offre as o ON o.marche = m.marche_id WHERE m.marche_id = ?;");
		pst.setInt(1,market);
		ResultSet rs = pst.executeQuery();
		String result = "<table id='market_table'> <tr><th>Nom</th><th>Quantité</th><th>Prix</th><th>Date</th></tr>";
		while(rs.next()){
			if(titre == null) titre = rs.getString(1);
			if(((Integer)idInverse) == null) idInverse = Integer.parseInt(rs.getString(6));
			result += "<tr>";
			result += "<td>"+rs.getString(2)+"</td>";
			result +="<td>"+rs.getString(3)+"</td>";
			result +="<td>"+rs.getString(4)+"</td>";
			result +="<td>"+rs.getString(5)+"</td>";
			result += "</tr>";
		}
		result += "</table>";
		table = result;
		con.close();
    }

    public void getMarket() throws SQLException, NamingException{
    	getMarket(idMarche);
    }

    public String getTitre(){
    	return titre;
    }

    public String getTable(){
    	return table;
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