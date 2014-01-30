<%@ page import="java.net.URLEncoder,bdd.ManagerMarket" %>
<%@ page contentType="text/html; charset=utf-8" %> 
    <header>
      <div id="haut">
	<div id="logo">
	  <div id="gauche">
	    <img src="images/bourse.jpg" alt="bourse.jpg"/>
	  </div>
	  <div id="droite">
	    <h1>Bourse de l'information</h1>
	  </div>
	</div>
	<div id="panelConnection">
	  <!--<h3>Connectez vous !</h3>-->
	  <div id="gauche">
	    <img src="images/profil.jpg" alt="imageProfil"/>
	  </div>
	  <div id="droite">
	    <% if(request.getRemoteUser()!=null) {%>
	    <h4>Bienvenue <%= request.getRemoteUser()  %></h4>
	    <p>
	      Argent : 5000
	      Titres : 5
	    </p>
	    <% } else { %>
	    <a href="factice.jsp">Inscrivez Vous !</a>
	    <% } %>

	  </div>
	</div>
      </div>
    </header>

    <div id="container">
      <nav>
	<div id="menu">
	  <ul>
	    <a href=""><li>Les march&eacute;s</li></a>
	    <a href=""><li>Compte</li></a>
	    <% if(request.getRemoteUser()!=null) {%>
	    <a href="login.jsp"><li>Deconnection</li></a>
	    <% } else { %>
	    <a href="factice.jsp"><li>Connection</li></a>
	    <% } %>
	  </ul>
	</div>

	<div id="search">
	  <form id="cse-search-box" action="http://google.com/cse">  
	    <input type="text" name="q" size="20" />  
	    <input type="submit" name="sa" value="Rechercher" />
	  </form>
	</div>
      </nav>
      
      <section id="bandeau">
	<h3>Dernier March&eacute;s</h3>
	<% ManagerMarket listeMarche = new ManagerMarket();
		listeMarche.getLastMarkets(); %>
      </section>

