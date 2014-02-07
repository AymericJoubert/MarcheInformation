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
@WebServlet("admin")
public class Admin extends HttpServlet
{
    public void doGet( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { 
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);
		req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
		res.setContentType("text/html");
		Connection con = null;
		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("marche");
			con = ds.getConnection();
			
			PreparedStatement pst = con.prepareStatement("select m1.marche_id, m1.question, m1.fermeture from marche m1, marche m2 where m1.marche_id = m2.inverse and m1.marche_id > m2.marche_id and m1.fermeture < current_date;");
			ResultSet rs = pst.executeQuery();
			out.println("<section id='marche'/>");
			out.println("<h1>Marches fermes</h2>");
			out.println("<table>");
			out.println("<tr><th>Id</th><th>Question</th><th>Date</th><th>Terminer</th></tr>");
			while(rs.next()){
				out.println("<tr>");
				out.println("<td>"+rs.getString(1)+"</td>");
				out.println("<td>"+rs.getString(2)+"</td>");
				out.println("<td>"+rs.getString(3)+"</td>");
				out.println("<td>");
				out.println("<form name='end' method='post'>");
				out.println("<input type='hidden' name="+rs.getString(1)+"/>");
				out.println("<input type='submit' value='Fermer'");
				out.println("</form>");
				out.println("</td>");
				out.println("<tr>");				
			}
			out.println("</table>");
			out.println("<h1>Creer un marche</h1>");
			out.println("<form name='begin' method='post'>");
			out.println("Createur  : <input type='text' name='createur' /><br/><br/>");
			out.println("Question  : <input type='text' name='question' /><br/><br/>");
			out.println("Inverse   : <input type='text' name='inverse' /><br/><br/>");			
			out.println("Fermeture : <input type='datetime-local' name='fermeture' /><br/><br/>");
			out.println("<input type='submit' value='Creer'/>");
			out.println("</form>");
			out.println("</section");
			con.close();
		}
		catch(Exception e){
			try{
				con.close();
			}catch(Exception ee){}
		}
	}


    public void doPost( HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection con=null;
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);
		req.getRequestDispatcher("/hautdepage.jsp").include(req, res);
		res.setContentType("text/html");
		
		if(req.getParameter("createur") != null && req.getParameter("question") != null && req.getParameter("inverse") != null && req.getParameter("fermeture") != null){
			try{
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource ds = (DataSource) envCtx.lookup("marche");
				con = ds.getConnection();
				int createur, id;
				String question, inverse, fermeture;
				createur  = Integer.parseInt(req.getParameter("createur"));
				question  = req.getParameter("question");
				inverse   = req.getParameter("inverse");
				fermeture = req.getParameter("fermeture");
				PreparedStatement pst  = con.prepareStatement("insert into marche (createur,question,ouverture,fermeture) values (?, ?, localtimestamp, ? )");
				PreparedStatement pst2 = con.prepareStatement("select marche_id from marche where createur = ? and question = ? ");
				PreparedStatement pst3 = con.prepareStatement("insert into marche (createur,question,ouverture,fermeture, inverse) values (?, ? , localtimestamp, ?, ? )");
				PreparedStatement pst4 = con.prepareStatement("select marche_id from marche where createur = ? and question = ? ");
				PreparedStatement pst5 = con.prepareStatement("update  marche set inverse = ? where marche_id = (select marche_id from marche where createur = ? and question = ? LIMIT 1)");
				pst.setInt(1, createur);
				pst.setString(2, question);
				pst.setString(3, fermeture);
				pst2.setInt(1, createur);
				pst2.setString(2, question);
				pst.executeUpdate();
				ResultSet rs = pst2.executeQuery();
				rs.next();
				id = Integer.parseInt(rs.getString(1));
				pst3.setInt(1, createur);
				pst3.setString(2, inverse);
				pst3.setString(3, fermeture);
				pst3.setInt(4, id);
				pst3.executeUpdate();
				pst4.setInt(1, createur);
				pst4.setString(2, inverse);
				rs = pst4.executeQuery();
				rs.next();
				id = Integer.parseInt(rs.getString(1));
				pst5.setInt(1, id);
				pst5.setInt(2, createur);
				pst5.setString(3, question);
				pst5.executeUpdate();
				out.println("<section id=marche><h1>Ajout reussi.</h1></section>");
				con.close();
			}
			catch(Exception e){
				try{
					con.close();
				}catch(Exception ee){}
			}
		}
	}

}
