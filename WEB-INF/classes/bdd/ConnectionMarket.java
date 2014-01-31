 package bdd;

import javax.naming.*;
import java.sql.*;
import javax.sql.*;

public class ConnectionMarket
{
  private Connection con;

  public ConnectionMarket(){
    con = null;
  }

  public Connection getConnection() throws NamingException,SQLException{
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource) envCtx.lookup("marche");
      con = ds.getConnection();

      return con;
      }

  public void closeConnection() throws SQLException{
    if(con != null){
      con.close();
    }
  }
}
