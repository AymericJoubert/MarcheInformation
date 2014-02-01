<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="bdd.Trader" %>
<%@ page import="bdd.Market" %>
    <% Market marche = new Market();
    	Market marcheInverse = new Market();
    	marche.getMarket(1) ;
    	marcheInverse.getMarket(2);
	%>
 <!doctype html >
<html>
  <head>
    <meta http-equiv="Content-Type" CONTENT="text/html;charset=iso-8859-1">
    <link rel="stylesheet" type="text/css" href="CSS/base.css" /> 
    <title>Mache de l'Information</title>
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
	    <h4>Bienvenue <%= request.getRemoteUser()  %></h4>
	    <% Trader current_user = new Trader(request.getRemoteUser());%>
	    <p>
	      Argent : <%= current_user.getCash() %><br/>
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
	    <a href="compte.jsp"><li>Compte</li></a>
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
	<h3>Derniers March&eacute;s Ouverts</h3>
	<%= marche.getLastMarkets() %>
	<h3>Derniers March&eacute;s Ferm&eacute;s</h3>
      </section>

