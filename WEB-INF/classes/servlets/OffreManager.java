import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet('/OffreManager')
public class OffreManager extends HttpServlet{
	
	public void doPost()(HttpServletRequest req, HttpServletResponse res) throws ServletException{
		// récupérer la nouvelle offre d'achat : quantité, prix, acheteur
		req.getParameter('quantite');
		req.getParameter('prix');
		req.getParameter('market');
	}
}