<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.09.2024
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/w3.css">
<html>
<head>
    <title>List of cars</title>
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <c:if test="${whereBack.equals('user')}">
            <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/all_users'">
        </c:if>
        <c:if test="${!whereBack.equals('user')}">
            <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/'">
        </c:if>
        <h1>List of cars in autoservise</h1>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "car" items="${list}" >
                <li>
                    <p class="nameOfAuthor">${car.getUserName()}</p>
                    <p>
                        <b>Марка: </b>
                        <c:out value="${car.getBrandName()}" />
                        <b> Модель: </b>
                        <c:out value="${car.getModel()}" />
                        <b> Год выпуска: </b>
                        <c:out value="${car.getYear()}" />
                        <b> Цена: </b>
                        <c:out value="${car.getNicePrice()} руб." />
                        <b> Пробег: </b>
                        <c:out value="${car.getMileage()} км." />
                        <c:if test="${whereBack.equals('all')}">
                            <button class="buttonw" type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/info?number=${car.getId()}&whereBack=all`;">Подробнее</button>
                        </c:if>
                        <c:if test="${whereBack.equals('my')}">
                            <button class="buttonw" type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/info?number=${car.getId()}&whereBack=my`;">Подробнее</button>
                        </c:if>
                        <c:if test="${whereBack.equals('user')}">
                            <button class="buttonw" type="button" onclick="window.location.href = `${pageContext.servletContext.contextPath}/info?number=${car.getId()}&whereBack=user`;">Подробнее</button>
                        </c:if>
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


