<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.09.2024
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/delete.css">
<html>
<head>
    <title>Delete car</title>
</head>
<body>
    <div id = "wrapper">
        <div id = "header" align="center">
            <img src = "icons/back.jpg" class = "myImage" onclick="location.href = '${pageContext.servletContext.contextPath}/info?number=${param.auto_id}&whereBack=${param.whereBack}'">
            <h1>Delete car</h1>
        </div>
        <div id = "content" align="center">
            <form method="post">
                <p style="color: red">Вы уверены, что хотите удалить объявление?</p>
                <button type="submit">Подтвердить</button>
            </form>
        </div>
        <div class="clear"></div>
        <div id="footer">
            <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
            <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
            <img class = "logo_bottom" src="icons/логотип-без-фона.png">
        </div>
    </div>
</body>
</html>
