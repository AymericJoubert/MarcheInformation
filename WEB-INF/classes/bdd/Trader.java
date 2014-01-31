package bdd;

import java.sql.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;

public class Trader {

    private String user_name;
    private String user_id;
    private String nom;
    private String prenom;
    private String cash;
    private Connection con = null;

    public Trader(String user_name){
	this.user_name = user_name;
	try{
	    Context initCtx = new InitialContext();
	    Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource) envCtx.lookup("marche");
	    con = ds.getConnection();

	    PreparedStatement pst = con.prepareStatement("SELECT user_id, user_name, nom, prenom, cash FROM users WHERE user_name = ?");
	    pst.setString(1, user_name);
	    ResultSet rs = pst.executeQuery();
	    rs.next();
	    user_id = rs.getString(1);
	    user_name = rs.getString(2);
	    nom = rs.getString(3);
	    prenom = rs.getString(4);
	    cash = rs.getString(5);

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

    public String getCash(){
	return cash;
    }

}