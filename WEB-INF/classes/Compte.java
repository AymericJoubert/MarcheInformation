import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import mapping.*;
//
// ATTENTION CETTE PAGE EST A AJOUTER AU REALM DANS LES PAGES NECESSITANT LE ROLE 1
//
// Servlet inscription.java
@WebServlet("compte")
public class Compte extends HttpServlet
{
    public void doGet( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);
		req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
		res.setContentType("text/html");
		Trader current_user = new Trader(req.getRemoteUser());
		out.println("<section id='marche'>");
		out.println("<h1>Modifiez les param&egrave;tres de votre compte !</h1>");
		out.println("<p>");
		out.println("Pseudo : <span class='info'>"+current_user.getUserName()+"</span>");
		out.println("<form method='post' action='#'>");
		out.println("<input type='text' name='pseudo' placeholder='Nouveau Pseudo' />");
		out.println("<input type='password' name='mdp' placeholder='Mot de passe' />");
		out.println("<input type='submit' value='Envoyer'/>");
		out.println("</form>");
		out.println("<br/><br/>");
		out.println("Nom : <span class='info'>"+current_user.getNom()+"</span>");
		out.println("<br/><br/>");
		out.println("Prenom : <span class='info'>"+current_user.getPrenom()+"</span>");
		out.println("</p>");
		out.println("<br/><br/>");
		out.println("Mot de passe : <span class='info'>********</span>");
		out.println("</p>");
		out.println("<form method='post'>");
		out.println("<input name='oldPasswd' type='password' placeholder='Ancien Mot de Passe' />");
		out.println("<input name='newPasswd' type='password' placeholder='Nouveau Mot de Passe' />");
		out.println("<input name='newPasswd2' type='password' placeholder='Repetez' />");
		out.println("<input type='submit' value='Envoyer'/>");
		out.println("</form>");
		out.println("</section>");
	}


    public void doPost( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection con=null;
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);
		req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
		res.setContentType("text/html");
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("marche");
			con = ds.getConnection();
							
			Trader current_user = new Trader(req.getRemoteUser());
			String pseudo, mdp, oldPasswd, newPasswd, newPasswd2;
			
			pseudo = req.getParameter("pseudo");
			mdp = req.getParameter("mdp");
			oldPasswd = req.getParameter("oldPasswd");
			newPasswd = req.getParameter("newPasswd");
			newPasswd2 = req.getParameter("newPasswd2");
			if(pseudo != null && mdp != null){				
				if(current_user.compareMdp(mdp)){
					PreparedStatement pst = con.prepareStatement("UPDATE users SET user_name = ? WHERE user_name = ?");
					PreparedStatement pst2 = con.prepareStatement("UPDATE user_roles SET user_name = ? WHERE user_name = ?");
					pst.setString(1, pseudo);
					pst.setString(2, current_user.getUserName());
					pst2.setString(1, pseudo);
					pst2.setString(2, current_user.getUserName());
					if(pst.executeUpdate() > 0 && pst2.executeUpdate() > 0){
						out.println("<section id=marche><h1>Mise à jour du pseudo effectuée. Veuillez vous reconnecter.</h1></section>");
						HttpSession hs = req.getSession();
						hs.invalidate();
					}
					else
						out.println("<section id=marche><h1>Désolé, nous n'avons pas pu mettre à jour votre compte.</section>");
				}
				else
					out.println("<section id=marche><h1>Mauvais mot de passe.</h1></section>");
			}
			else{
				if(oldPasswd != null && newPasswd != null && (newPasswd.equals(newPasswd2))){
					if(current_user.compareMdp(oldPasswd)){
						PreparedStatement pst = con.prepareStatement("UPDATE users SET user_pass = ? WHERE user_name = ?");
						pst.setString(1, newPasswd);
						pst.setString(2, current_user.getUserName());
						if(pst.executeUpdate() > 0)
							out.println("<section id=marche><h1>Changement de mot de passe effectué.</h1></section>");
						else
							out.println("<section id=marche><h1>Désolé, nous n'avons pas pu mettre a jour votre compte.</h1></section>");
					}
					else
						out.println("<section id=marche><h1>Mauvais mot de passe.</h1></section>");
				}
				else{
					out.println("<section id=marche><h1>Veuillez renseigner tous les champs.</h1></section>");
				}
			}
			
			con.close();
			
		}
		catch(Exception e){
			try{
				con.close();
			}catch(Exception ee){}
			out.println("exception de competition");
			out.println(e.getMessage());
			out.println(e.getStackTrace());
			out.println(e.toString());
		}
	}

}
