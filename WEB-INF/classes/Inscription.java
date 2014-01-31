import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

// Servlet inscription.java
@WebServlet("inscription")
public class Inscription extends HttpServlet
{
    public void doGet( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);
	req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
	res.setContentType("text/html");
	out.println("<section id=marche>");
	out.println("<h1>Inscrivez vous !</h1>");
	out.println("<form name=inscription method=post action=#>");
	out.println("Pseudo :        <input type='text' name='username'/><br/>");
	out.println("Prenom :        <input type='text' name='prenom'/><br/>");
	out.println("Nom :           <input type='text' name='nom'/><br/>");
	out.println("Mot de passe :  <input type='text' name='mdp'/><br/>");
	out.println("Confirmation :  <input type='text' name='mdp2'/><br/>");
	out.println("<input type=submit value='Inscription' />");
	out.println("</form>");
	out.println("</section>");
  }

    public void doPost( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	Connection con=null;
	try{
	    Context initCtx = new InitialContext();
	    Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource) envCtx.lookup("marche");
	    
	    
	    PrintWriter out = res.getWriter();
	    String nom, prenom, username, mdp;
	    nom = req.getParameter("nom");
	    prenom = req.getParameter("prenom");
	    username = req.getParameter("username");
	    mdp = req.getParameter("mdp");
	    if(req.getParameter("mdp").equals(req.getParameter("mdp2"))){
		if(nom != null && prenom != null && username != null && mdp != null){
		    con = ds.getConnection();
		    res.setContentType("text/html");
		    out.print("Inscription réussie.");
		    try{
			PreparedStatement pst = con.prepareStatement("INSERT INTO users_confirm VALUES (? , ? , ? , ? , ?)");
			pst.setString(1, username);
			pst.setString(2, mdp);
			pst.setString(3, nom);
			pst.setString(4, prenom);
			String confirm = generate(20);
			pst.setString(5, confirm);   
			pst.executeQuery();
		    }
		    catch(Exception e){
			try{
			    con.close();
			}
			catch(Exception ee){}
		    }
		}
	    }
	}
	catch(Exception e){}
	
	
    }
    

    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
	    int i = (int)Math.floor(Math.random() * (chars.length() -1));
	    pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
}