<!doctype html >
<html>
  <head>
    <meta http-equiv="Content-Type" CONTENT="text/html;charset=iso-8859-1">
    <link rel="stylesheet" type="text/css" href="CSS/base.css" /> 
    <title>Marché de l'Information</title>
    <%@ page import="bdd.ManagerMarket,mapping.Market,mapping.Offre,java.util.ArrayList" %>
  </head>
  <body>
  	<% try{%>
    <% ManagerMarket marches = new ManagerMarket();
    	// le premier carnet
      if(request.getParameter("market") != null){
    	int idMarche = Integer.parseInt(request.getParameter("market"));
        marches.getMarket(idMarche);
      }else{
    		marches.getMarket();
      }
    	// recupere le premier carnet
    	Market marche = marches.getMarches().get(0);
    	ArrayList<Offre> offres = marche.getOffres();
    	%>
    <%@ include file="hautdepage.jsp" %>
    <section id="marche">
      <h1>Marché</h1>
      <h2><%= marche.getQuestion() %></h2>
      <% if(!offres.isEmpty()){%>
      <table id="market_table">
      	<tr>
      		<th>Nom</th><th>Quantité</th><th>Prix</th><th>Date</th>
      	</tr>
     	<%for(Offre o : offres){
     		if(o.getAcheteurInverse() == null){%>
     		<tr>
     			<td>o.getAcheteur()</td><td>o.getQuantite()</td><td>o.getValeur()</td><td>o.getOffreDate()</td>
     		</tr>
     	<% }
     	} %>
     </table>
 <% } %>

 	<% // recupere le premier carnet
    	marche = marches.getMarches().get(1);
    	offres = marche.getOffres();
    	%>
      <% if(!offres.isEmpty()){%>
      <table id="market_table">
      	<tr>
      		<th>Nom</th><th>Quantité</th><th>Prix</th><th>Date</th>
      	</tr>
 	<%for(Offre o : offres){
 		if(o.getAcheteurInverse() == null){%>
 		<tr>
 			<td>o.getAcheteur()</td><td>o.getQuantite()</td><td>o.getValeur()</td><td>o.getOffreDate()</td>
 		</tr>
 	  <%}%>
 	<%}%>
 </table>
 <% } %>
	<!-- <% // if(session.getAttribute("user_login")!=null) {%>
	<tr></tr>
	<tr>
	  <form>
	    <td></td>
	    <td><input type="text" name="quantite"/></td>
	    <td><input type="text" name="prix"/></td>
	    <td><input type="submit" value="Achetez !"/></td>
	  </form>
	</tr>	 
	<% //} %>
      </table> -->
      <img src="./images/fleche.gif" alt="image marché inverse"/><a href="">Accédez au marché inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br/>Mentions Légales - Tous droits réservés <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    <% }catch(Exception e){%>
    	<p id="erreur"><%= e%></p>
    <%}%>
  </body>
</html>
