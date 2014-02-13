package mapping;

import java.sql.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;
import bdd.ConnectionMarket;

public class Trader {

    private String user_name;
    private String user_id;
    private String nom;
    private String prenom;
    private String cash;
    private String pass;
    private String titres;
    private Connection con = null;

    public Trader(String user_name){
	this.user_name = user_name;
	try{
	    Context initCtx = new InitialContext();
	    Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource) envCtx.lookup("marche");
	    con = ds.getConnection();

	    PreparedStatement pst = con.prepareStatement("SELECT user_id, user_name, nom, prenom, cash, user_pass FROM users WHERE user_name = ?");
	    PreparedStatement pst2 = con.prepareStatement("SELECT COUNT(*) FROM offre JOIN users on user_id = acheteur and user_name = ? and offre.achat = true");
	    pst2.setString(1, user_name);
	    pst.setString(1, user_name);
	    ResultSet rs = pst.executeQuery();
	    rs.next();
	    user_id = rs.getString(1);
	    user_name = rs.getString(2);
	    nom = rs.getString(3);
	    prenom = rs.getString(4);
	    cash = rs.getString(5);
	    pass = rs.getString(6);
	    
	    rs = pst2.executeQuery();
	    rs.next();
	    titres = rs.getString(1);	    
		con.close();
	}
	catch(Exception e){
	    try{
		con.close();
	    }
	    catch(Exception ee){
	    }
	}
    }

    public String getUserName(){
	return user_name;
    }

    public String getUserId(){
	return user_id;
    }

    public void reloadCash() throws SQLException, NamingException{
    	con = ConnectionMarket.getConnection();
    	PreparedStatement pst = con.prepareStatement("SELECT cash FROM users WHERE user_name = ?");
    	pst.setString(1,user_name);
    	ResultSet rs = pst.executeQuery();
    	if(rs.next())
    		cash = rs.getString(1);
    }

    public String getCash(){
	return cash;
    }
    
    public String getNom(){
		return nom;
	}
	
	public String getPrenom(){
		return prenom;
	}
	
	public boolean compareMdp(String mdp){
		return pass.equals(mdp);
	}
	
	public String getTitres(){
		return titres;
	}
	
	public String getTitresDetails(String user_name){
		Connection conn = null;
		String ret = "";
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("marche");
			conn = ds.getConnection();
		
			PreparedStatement pst = con.prepareStatement("select question, COUNT(*) from offre join users on user_id = acheteur join marche on offre.marche = marche.marche_id WHERE user_name = ? and offre.achat = true group by marche.question");
			pst.setString(1, user_name);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				ret += rs.getString(1) + " : " + rs.getString(2) + " <br/> ";
			}
			conn.close();
			return ret;
		
			
		}
		catch(Exception e){
			try{
				conn.close();
				return ret;				
			}catch(Exception ee){
				return ret;
			}
		}
	}

}
