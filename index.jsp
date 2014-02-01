    <%@ include file="hautdepage.jsp" %>
    <section id="marche">
      <h1>Marché</h1>
      <h2><%= marche.getTitre() %></h2>
      <%= marche.getTable() %>
      <%= marcheInverse.getTable() %>
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