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
	res.setContentType("text/html");0