<%@ include file="hautdepage.jsp" %>
<%
if(request.getRemoteUser() != null ) { 

Trader current_user = new Trader(request.getRemoteUser());
%>
<section id="marche">
<h1>Modifiez les param&egrave;tres de votre compte !</h1>

<p>
Pseudo : <span class='info'><%= current_user.getUserName() %></span>
<br/><br/>
Nom : <span class='info'></span>
<br/><br/>
Prenom : <span class='info'></span>

</p>
<form>
	<input type="text" placeholder="Ancien Mot de Passe" />
	<input type="text" placeholder="Nouveau Mot de Passe" />
	<input type="text" placeholder="Repetez" />
</form>
</section>
<% } %>