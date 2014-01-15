// Servlet Test.java  de test de la configuration
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/servlet/RecupereMarche")
public class RecupereMarche extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res ) 
       throws ServletException, IOException
  {
    res.setContentType( "text/html" );
    
    Context initCtx = new InitialContext();
    Content envCtx = (Context) initCtx.lookup("java:comp/env");
    DataSource ds = (DataSource) envCtx.lookup("marche");
    Connection con = ds.getConnection();
    Statement st = con.prepareStatement("SELECT m.question, ");
    ResultSet rs = st.("");
  }
}
