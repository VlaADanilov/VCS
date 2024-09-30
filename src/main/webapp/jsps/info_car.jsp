<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.09.2024
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/style.css">
<html>
<head>
    <title>Info about car</title>
</head>
<body>
<div id = "wrapper">
    <div id = "header" align="center">
        <c:if test="${param.whereBack.equals('all')}">
            <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/list'">
        </c:if>
        <c:if test="${param.whereBack.equals('my')}">
            <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/my_cars'">
        </c:if>
        <c:if test="${param.whereBack.equals('user')}">
            <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/user_cars?user_id=${car.getUser_id()}'">
        </c:if>
        <c:if test="${pravo}">
            <img class="myImage2" src="icons/delete-1487-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}/delete?auto_id=${car.getId()}&whereBack=${param.whereBack}'">
            <img class="myImage2" src="icons/settings-2-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}/update?auto_id=${car.getId()}&whereBack=${param.whereBack}'">
        </c:if>
        <h1> Info about this car</h1>
    </div>
    <div id = "content">
        <div id = "left-content">
            <ul>
                <c:forEach var = "numb" items="${list}" >
                    <div align="center">
                        <p>
                            <img class = "carImage" src="jsps/getImage.jsp?auto_id=${car.getId()}&number=${numb}" width="400px">
                        </p>
                        <p></p>
                    </div>
                </c:forEach>
            </ul>
            <div align="center">
                <c:if test="${pravo}">
                    <button type="button" onclick="location.href = '${pageContext.servletContext.contextPath}/image?auto_id=${car.getId()}&whereBack=${param.whereBack}'">Добавить картинку</button>
                </c:if>
            </div>
        </div>
        <div id = "right-pannel">
            <div id = "right-content">
                <p><strong>Марка: </strong> ${car.getBrandName()}</p>
                <p ><strong>Модель: </strong>${car.getModel()}</p>
                <p><strong>Страна производства: </strong>${brand.getCountry()}</p>
                <p><strong>Год выпуска: </strong>${car.getYear()}</p>
                <p><strong>Цена: </strong>${car.getNicePrice()} руб.</p>
                <p><strong>Пробег: </strong>${car.getNiceMileage()} км.</p>
            </div>
            <div id = "bottom-content">
                <p><strong>Телефон для связи: </strong> ${phone}</p>
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
    </div>

</div>
</body>
</html>
