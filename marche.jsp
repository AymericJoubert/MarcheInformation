    <%@ page import="mapping.Market,mapping.Offre,java.util.ArrayList" %>
     <%@ include file="hautdepage.jsp" %>

    <% ManagerMarket marches = new ManagerMarket();
    	// le premier carnet
      if(request.getParameter("market") != null){
    	  int idMarche = Integer.parseInt(request.getParameter("market"));
        try{
        marches.focusMarket(idMarche);
        }catch(Exception e){}
      }else{
    		marches.focusMarket();
      }
    	// recupere le premier carnet
    	Market marche = marches.getLeMarche();
    	ArrayList<Offre> offres = marche.getOffres();
    	%>

    <section id="marche">
      <h1>Marché</h1>
      <h2><%= marche.getQuestion() %></h2>
      <% if(!offres.isEmpty()){%>
      <table id="market_table">
      	<tr>
      		<th>Nom</th><th>Quantité</th><th>Prix</th><th>Date</th>
      	</tr>
     	<%for(Offre o : offres){
     		if(o != null){%>
     		<tr>
     			<td><%= o.getAcheteur() %></td><td><%= o.getQuantite() %></td><td> <%= o.getValeur() %></td><td><%= o.toStringDate() %></td>
     		</tr>
     	<% }
     	} %>
     </table>
 <% } %>
      <img src="./images/fleche.gif" alt="image marché inverse"/><a href="marche.jsp?market=<%= marche.getInverse()%>">Accédez au marché inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br /><a alt="video marche financier" href="http://www.youtube.com/watch?v=gxXhwaZewlQ">Explication video marché financier</a><br/>Mentions Légales - Tous droits réservés <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    
  </body>
</html>
