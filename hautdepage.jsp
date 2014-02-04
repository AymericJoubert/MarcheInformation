
<!doctype html >
<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="CSS/base.css" /> 
    <title>Marché de l'Information</title>
    <%@ page import="java.net.URLEncoder,bdd.ManagerMarket,mapping.Trader" %>
<%  
	Trader current_user = null;
   if(request.getRemoteUser()!=null){
   	current_user = new Trader(request.getRemoteUser());
   }
%>
  </head>
  <body>
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
	    <h4>Bienvenue <%= request.getRemoteUser() %></h4>
	    <p>
	      Cash : <%= current_user.getCash() %>
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
	    <a href="index.jsp"><li>Les march&eacute;s</li></a>
	    <a href=""><li>Compte</li></a>
	    <% if(request.getRemoteUser()!=null) {%>
	    <a href="logout.jsp"><li>Deconnection</li></a>
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
		<h3>Derniers March&eacute;s</h3>
		<% ManagerMarket listeMarche = new ManagerMarket(); %>
		<%= listeMarche.getLastMarkets() %>

		<h3>Derniers March&eacute;s ferm&eacute;s</h3>
		<%= listeMarche.getHistoriqueMarkets() %>
      </section>

