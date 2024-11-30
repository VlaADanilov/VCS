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
<link rel="stylesheet" href="/styles/users.css">
<html>
<head>
    <title>All users</title>
    <meta charset="utf-8" />
    <link rel="icon" href="/pages/ico.png" type="image/png">
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="/icons/back.jpg" onclick="location.href='../..'">
        <h1>Список пользователей</h1>
    </div>
    <div>
        <div align="center">
            <form method="post">
                <label>
                    <input type="text" name = "filter" placeholder="Поиск">
                </label>
                    <button type="submit">Подтвердить</button>
            </form>
        </div>

        <ul class = "list1a">
            <c:forEach var = "user" items="${list}" >
                <li>
                    <p class="status">${user.getStatus()}</p>
                    <p>
                        <b>Имя: </b>
                        <c:out value="${user.getName()}" />
                        <b> Номер телефона: </b>
                        <c:out value="${user.getPhone()}" />
                        <c:if test="${sessionScope.get('status').equals('owner')}">
                            <button type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/doAdmin?user_id=${user.getId()}`;">Поменять статус</button>
                        </c:if>
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
        <img class = "logo_bottom" src="/icons/логотип-без-фона.png">
    </div>
</div>
</body>
</html>
