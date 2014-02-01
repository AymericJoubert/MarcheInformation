<%@ include file="hautdepage.jsp" %>    

<section id="marche">
<%@ page session="true"%>

<p>
Vous êtes deconnecte.
</p>
<% session.invalidate(); %>

<br/><br/>
<a href="index.jsp">Cliquez ici pour vous rendre sur la page d'accueil</a>
</section>