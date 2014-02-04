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
    	%>

    <section id="marche">
      <h1>March�</h1>
      <h2><%= marche.getQuestion() %></h2>
      <% if(!offres.isEmpty()){%>
      <table id="market_table">
      	<tr>
      		<th>Quantit�</th><th>Prix</th>
      	</tr>    
          
        </tr>
     	<%for(Offre o : offres){
     		if(o.getAcheteurInverse() == null){%>
     		<tr>
     			<td><%= o.getQuantite() %></td><td> <%= o.getValeur() %></td>
     		</tr>
     	<% }
     	} %>
     </table>
 <% } %>
<HR>
 	<% // recupere le premier carnet
    	marche = marches.getMarches().get(1);
    	offres = marche.getOffres();
    	%>
      <% if(!offres.isEmpty()){%>
        <table id="market_table">
         	<%for(Offre o : offres){
         		if(o.getAcheteurInverse() == null){%>
         		<tr>
              <td><%= o.getQuantite() %></td><td> <%= o.getValeur() %></td>
         	  <%}%>
         	<%}%>
       </table>
     <% } %>

	<%  if(request.getRemoteUser()!=null) {%> 
            <form method='post' action='OffreManager'>
              <td><input type="text" name="quantite"/></td>
              <td><input type="text" name="prix"/></td>
              <td><input type="submit" value="Achetez !"/></td>
              <input type="hidden" name="market" value="<%= marche.getMarcheId() %>"/>
            </form>
        <% } %>
        
      <img src="./images/fleche.gif" alt="image march� inverse"/><a href="index.jsp?market=<%= marche.getMarcheId()%>">Acc�dez au march� inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br /><a alt="video marche financier" href="http://www.youtube.com/watch?v=gxXhwaZewlQ">Explication video march� financier</a><br/>Mentions L�gales - Tous droits r�serv�s <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    
  </body>
</html>
