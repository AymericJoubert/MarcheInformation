<!doctype html >
<html>
  <head>
    <meta http-equiv="Content-Type" CONTENT="text/html;charset=iso-8859-1">
    <link rel="stylesheet" type="text/css" href="CSS/base.css" /> 
    <title>Mache de l'Information - Login</title>
  </head>
  <body>    
  <%@ include file="hautdepage.html" %>
  <section id="marche">
    <h3>Connectez vous pour accéder à plus de fonctionnalités !</h3>
    <form name="bigfif" action="j_security_check" method="post">  
      Username : <input type="text" name="j_username"><br>  
      Password: <input type="password" name="j_password"><br>  
      <input type="submit" value="submit">  
    </form>
  </section>
  </div>
</html>
