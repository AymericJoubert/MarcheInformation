<!doctype html >
<html>
  <head>
    <meta http-equiv="Content-Type" CONTENT="text/html;charset=iso-8859-1">
    <link rel="stylesheet" type="text/css" href="CSS/base.css" /> 
    <title>Mache de l'Information</title>
    <jsp:useBean id="market" class="tools.Market" scope="page"/>
  </head>
  <body>
    <%@ include file="hautdepage.html" %>
    <jsp:setProperty name="market" property="question" />
    <section id="marche">
      <h1>Marché</h1>
      <h2><%= market.getQuestion() %></h2>
      <table border>
	<caption>Ventes</caption>
	<tr>
	  <th>Nom</th>
	  <th>Quantité</th>
	  <th>Prix</th>
	  <th>Date</th>
	</tr>
	<tr>
	  <td>Joubi</td>
	  <td>5</td>
	  <td>90</td>
	  <td>22/01/2014</td>
	</tr>
	<tr>
	  <td>Axoque</td>
	  <td>5000</td>
	  <td>9</td>
	  <td>21/01/2014</td>
	</tr>
      </table>
      <table border>
	<caption>Achats</caption>
	<tr>
	  <th>Nom</th>
	  <th>Quantité</th>
	  <th>Prix</th>
	  <th>Date</th>
	</tr>
	<tr>
	  <td>Blondeah</td>
	  <td>10</td>
	  <td>30</td>
	  <td>22/01/2014</td>
	</tr>
	<tr>
	  <td>Demoooooode</td>
	  <td>50</td>
	  <td>95</td>
	  <td>21/01/2014</td>
	</tr>	 
	<% if(session.getAttribute("user_login")!=null) {%>
	<tr></tr>
	<tr>
	  <form>
	    <td></td>
	    <td><input type="text" name="quantite"/></td>
	    <td><input type="text" name="prix"/></td>
	    <td><input type="submit" value="Achetez !"/></td>
	  </form>
	</tr>	 
	<% } %>
      </table>
      <img src="./images/fleche.gif" alt="image marché inverse"/><a href="">Accédez au marché inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br/>Mentions Légales - Tous droits réservés <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    
  </body>
</html>
