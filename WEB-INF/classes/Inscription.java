import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
	out.println("Email :         <input type='text' name='email'/><br/>");
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
	    String nom, prenom, username, mdp, email;
	    nom = req.getParameter("nom");
	    prenom = req.getParameter("prenom");
	    username = req.getParameter("username");
	    mdp = req.getParameter("mdp");
	    email = req.getParameter("email");
	    if(req.getParameter("mdp").equals(req.getParameter("mdp2"))){
		if(nom != null && prenom != null && username != null && mdp != null && email != null){
		    con = ds.getConnection();
		    res.setContentType("text/html");
		    try{
			PreparedStatement pst = con.prepareStatement("INSERT INTO users_confirm VALUES (? , ? , ? , ? , ?, ?)");
			pst.setString(1, username);
			pst.setString(2, mdp);
			pst.setString(3, nom);
			pst.setString(4, prenom);
			pst.setString(5, email);
			String confirm = generate(20);
			pst.setString(6, confirm);   
			int result = pst.executeUpdate();
			sendMail(email, confirm);
			if(result != 0){
			    req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
			    res.setContentType("text/html");
			    out.println("<section id=marche>");
			    out.println("<h1>Inscription réussie.</h1>");
			    out.println("</section>");
			}
			else{
			    req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
			    res.setContentType("text/html");
			    out.println("<section id=marche>");
			    out.println("<h1>Erreur lors de l'inscription.</h1>");
			    out.println("</section>");
			}
		    }
		    catch(Exception e){
			out.println(e.getMessage());
			try{
			    con.close();
			}
			catch(Exception ee){
			    out.println(ee.getMessage());
			}
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


    public void sendMail(String dest, String code){
	Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.iut-infobio.priv.univ-lille1.fr");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");
 
	Session session = Session.getDefaultInstance(props,
						     new javax.mail.Authenticator() {
							 protected PasswordAuthentication getPasswordAuthentication() {
							     return new PasswordAuthentication("deleruea@pop.iut-infobio.priv.univ-lille1.fr","1f331aee");						 }
						     });
 
	try {
 
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress("marcheInfoContact@gmail.com"));
	    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
	    message.setSubject("Marche Information - Confirmation d'inscription");
	    String mess="<p><h3>Merci pour votre inscription au Marché d'information</h3></p>"+
		"<p><h2>Afin de valider votre inscription, merci de cliquer sur ce <a href='http://localhost:8080/MarcheInformation/inscription?code="+code+"'>lien</a>"+
		"<p><h3>En espérant vous revoir prochainement sur notre site.</h3></p>"+
		"<p><h3>Marche Info team.</h3></p>";
	    message.setContent(mess, "text/html;charset=UTF-8");
 
	    Transport.send(message);

	} catch (MessagingException e) {
	    throw new RuntimeException(e);
	}
    }

}