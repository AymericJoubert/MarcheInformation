    <%@ include file="hautdepage.jsp" %>
    <section id="marche">
      <h1>March�</h1>
      <h2><%= marche.getTitre() %></h2>
      <%= marche.getTable() %>
      <%= marcheInverse.getTable() %>
      <img src="./images/fleche.gif" alt="image march� inverse"/><a href="">Acc�dez au march� inverse !</a>
    </section>
</div>    
<footer>
      <div id="infs">
	<h5>DA2I 2014 <br/>Mentions L�gales - Tous droits r�serv�s <br/> Blondeau - Joubert</h5>
      </div>
    </footer>
    
  </body>
</html>