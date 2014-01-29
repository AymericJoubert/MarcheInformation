// Servlet Test.java  de test de la configuration
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/servlet/ConnectionMarket")
public class ConnectionMarket extends HttpServlet
{
  public void doPost( HttpServletRequest req, HttpServletResponse res ) 
       throws ServletException, IOException
  {
    res.setContentType( "text/html" );
    
    Context initCtx = new InitialContext();
    Context envCtx = (Context) initCtx.lookup("java:comp/env");
    DataSource ds = (DataSource) envCtx.lookup("marche");
    Connection con = ds.getConnection();
    PreparedStatement st = con.prepareStatement("SELECT ? FROM marche WHERE marche_id = ?");
    
    ResultSet rs = st.("");
  }


}
