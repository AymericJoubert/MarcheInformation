 package bdd;

import javax.naming.*;
import java.sql.*;
import javax.sql.*;

public class ConnectionMarket
{
   public static Connection getConnection() throws NamingException,SQLException{
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource) envCtx.lookup("marche");
      Connection con = ds.getConnection();

      return con;
      }
}
