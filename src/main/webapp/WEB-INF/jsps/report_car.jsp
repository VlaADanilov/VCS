<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.10.2024
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="/styles/add.css">

<html lang="ru">
<head>
    <title>Report car</title>
    <meta charset="utf-8" />
    <link rel="icon" href="/pages/ico.png" type="image/png">
</head>
<body>
    <div id = "wrapper">
        <div id = "header" align="center">
            <img class="myImage" src="/icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}${uri}?number=${param.auto_id}'">
            <h1>Пожаловаться на объявление</h1>
        </div>
        <div align="center">
            <form method="post">
                <p><label>Причина жалобы:
                    <input type="text" name = "desc">
                </label></p>
                <input type="hidden"  name = "uri" value="${uri}">
                <button type="submit">Подтвердить</button>
            </form>
            <c:if test="${flag.equals('false')}">
                <p style="color: red">Ошибка</p>
            </c:if>
        </div>
        <div class = "clear"></div>
        <div id = "footer">
            <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
            <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
            <img class = "logo_bottom" src="/icons/логотип-без-фона.png">
        </div>
    </div>
</body>
</html>
