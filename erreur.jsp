<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
	<title>Page d'erreur</title>
        <%@ page
               contentType="text/html; charset=ISO-8859-15" 
	       isErrorPage="true" %>
	<link rel="stylesheet" href="CSS/base.css" type="text/css">
    </head>
<body>

   <h1> Page de gestion d'erreur</h1>
   <h3> Un probleme de type 
"<%
	String m = request.getParameter("message");
	//out.println(request);
	if (exception!=null) out.print(exception.getMessage());
 %>" est survenu.</h3> 


<a href=index.jsp>Retour</a>

<%@ include file="basDePage.html" %>







<%
String request_uri = (String)request.getAttribute("javax.servlet.error.request_uri");
out.println(request_uri);
%>

</body>
</html>
