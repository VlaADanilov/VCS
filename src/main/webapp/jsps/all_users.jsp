<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.09.2024
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/users.css">
<html>
<head>
    <title>All users</title>
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/'">
        <h1>List of users</h1>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "user" items="${list}" >
                <li>
                    <p class="status">${user.getStatus()}</p>
                    <p>
                        <b>Имя: </b>
                        <c:out value="${user.getName()}" />
                        <b> Пароль: </b>
                        <c:out value="${user.getPassword()}" />
                        <b> Номер телефона: </b>
                        <c:out value="${user.getPhone()}" />
                        <button type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/doAdmin?user_id=${user.getId()}`;">Поменять статус</button>
                        <button type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/user_cars?user_id=${user.getId()}`;">Его обьявления</button>
                    </p>
                </li>
            </c:forEach>
        </ul>

    </div>
    <!--Запрет наплывания-->
    <div class="clear"></div>
    <!--Подвал-->
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>

    </div>
</div>
</body>
</html>
