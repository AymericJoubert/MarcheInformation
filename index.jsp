    <%@ page import="mapping.Market,mapping.Offre,java.util.ArrayList" %>
     <%@ include file="hautdepage.jsp" %>

    <% ManagerMarket marches = new ManagerMarket();
    	// le premier carnet
      if(request.getParameter("market") != null){
    	int idMarche = Integer.parseInt(request.getParameter("market"));
        marches.getSymetriquesMarkets(idMarche);
      }else{
    		marches.getSymetriquesMarkets();
      }
    	// recupere le premier carnet
    	Market marche = marches.getMarches().get(0);
    	ArrayList<Offre> offres = marche.getOffres();

        //out.println("offre taille"+offres.size()+ "offre empty :"+offres.isEmpty());
    	%>

    <section id="marche">
      <h1>Marché</h1>
      <h2><%= marche.getQuestion() %></h2>
      <table id="market_table">
      	<tr>
      		<th>Quantité</th><th>Prix</th>
      	</tr>
      <% if(!offres.isEmpty() ){%>
      <tr><td colspan="2">offre d'achat du march&eacute;</td></tr>
       	<%for(Offre o : offres){
       		if(o.getAcheteurInverse() == null && o.getValeur() > 0 && o.getValeur() < 100){%>
       		<tr>
       			<td><%= o.getQuantite() %></td><td> <%= o.getValeur() %></td>
       		</tr>
       	<% } %>
       <%	} %>
      <% }else{%>
        <tr><td colspan="2">Pas encore d'offre d'achat</td></tr>
      <% } %>
    </table>
<HR>
      <% offres = marche.getVentes();
        //out.println("offre taille"+offres.size()+ "offre empty :"+offres.isEmpty());
        if(!offres.isEmpty()){%>
        <table id="market_table">
          <tr><td colspan="2">offre de vente du march&eacute;</td></tr>
         	<%for(Offre o : offres){
         		if(o.getAcheteurInverse() == null && o.getValeur() > 0 && o.getValeur() < 100){%>
         		<tr>
              <td><%= o.getQuantite() %></td><td> <%= o.getValeur() %></td>
         	  <%}%>
         	<%}%>
       </table>
     <% } else { %>
        <table><tr><td>Pas encore d'offre de vente</td></tr></table>
  <% } %>
	<%  if(request.getRemoteUser()!=null) {%> 
            <form method='post' action='OffreManager'>
              <td><label for="qute">quantite : </label><input id="qute" type="text" name="quantite"/></td>
              <td><label for="prix">prix :</label><input  id="prix" type="text" name="prix"/></td>
              <td><input type="submit" value="Achetez !"/></td>
              <input type="hidden" name="market" value="<%= marche.getMarcheId() %>"/>
            </form>
        <% } %>
        
      <img src="./images/fleche.gif" alt="image marché inverse"/><a href="index.jsp?market=<%= marche.getInverse()%>">Accédez au marché inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br /><a alt="video marche financier" href="http://www.youtube.com/watch?v=gxXhwaZewlQ">Explication video marché financier</a><br/>Mentions Légales - Tous droits réservés <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    
  </body>
</html>
