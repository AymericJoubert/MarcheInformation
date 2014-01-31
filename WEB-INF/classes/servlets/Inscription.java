import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;

// Servlet inscription.java
@WebServlet("inscription")
public class Inscription extends HttpServlet
{
    public void service( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);
	res.setContentType("text/html");
	req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
	out.print("<section id=marche>");
	out.print("<h1>Inscrivez vous !</h1>");
	out.print("<form name=inscription method=post>");
	out.print("Pseudo :        <input type=text name=username/>");
	out.print("Prenom :        <input type=text name=prenom/>");
	out.print("Nom :           <input type=text name=nom/>");
	out.print("Mot de passe :  <input type=text name=mdp/>");
	out.print("Confirmation :  <input type=text name=mdp2/>");
	out.print("</form>");
	out.print("</section>");
  }

    /*public void doPost( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	//Connection con=null;
	
	//PrintWriter out = res.getWriter();
	
	}*/
}

