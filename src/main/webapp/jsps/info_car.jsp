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
<link rel="stylesheet" href="/styles/style.css">
<html>
<head>
    <title>Info about car</title>
    <link rel="icon" href="/pages/ico.png" type="image/png">
</head>
<body>
<div id = "wrapper">
    <div id = "header" align="center">
        <img class = "myImage" src="/icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}${end}'">
        <c:if test="${pravo}">
            <img class="myImage2" src="/icons/delete-1487-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}${uri}/delete?auto_id=${car.getId()}'">
            <img class="myImage2" src="/icons/settings-2-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}${uri}/update?auto_id=${car.getId()}'">
        </c:if>
        <c:if test="${sessionScope.get('username') != null}">
            <c:if test="${!exist}">
                <img class="myImage2" src="/icons/heart-black.svg" onclick="location.href = '${pageContext.servletContext.contextPath}${uri}/setting_like?auto_id=${car.getId()}&do=add'">
            </c:if>
            <c:if test="${exist}">
                <img class="myImage2" src="/icons/heart-red.svg" onclick="location.href = '${pageContext.servletContext.contextPath}${uri}/setting_like?auto_id=${car.getId()}&do=delete'">
            </c:if>
            <img class="myImage2" src = "/icons/report-among-us-online-game-svgrepo-com.svg" onclick="location.href = '${pageContext.servletContext.contextPath}${uri}/report?auto_id=${car.getId()}'">
        </c:if>
        <h1>Подробнее об автомобиле</h1>
    </div>
    <div id = "content">
        <div id = "left-content">
            <ul>
                <c:forEach var = "numb" items="${list}" >
                    <div align="center">
                        <p>
                            <!--<img class = "carImage" src="jsps/getImage.jsp?auto_id=${car.getId()}&number=${numb}" width="400px">-->
                            <img class = "carImage" src="${pageContext.servletContext.contextPath}/getImage?auto_id=${car.getId()}&number=${numb}" width="400px">
                        </p>
                        <p></p>
                    </div>
                </c:forEach>
            </ul>
            <div align="center">
                <c:if test="${pravo}">
                    <button type="button" onclick="location.href = '${pageContext.servletContext.contextPath}${uri}/image?auto_id=${car.getId()}'">Добавить картинку</button>
                    <button type="button" onclick="location.href = '${pageContext.servletContext.contextPath}${uri}/delete_image?auto_id=${car.getId()}'">Удалить картинку</button>
                </c:if>
            </div>
        </div>
        <div id = "right-pannel">
            <div id = "right-content">
                <p><strong>Марка: </strong> ${pageContext.servletContext.getAttribute('database').getBrandById(car.getBrand_id()).getName() }</p>
                <p ><strong>Модель: </strong>${car.getModel()}</p>
                <p><strong>Страна производства: </strong>${brand.getCountry()}</p>
                <p><strong>Год выпуска: </strong>${car.getYear()}</p>
                <p><strong>Цена: </strong>${car.getNicePrice()} руб.</p>
                <p><strong>Пробег: </strong>${car.getNiceMileage()} км.</p>
                <p><strong>Описание: </strong>${car.getDescription()}</p>
            </div>
            <div id = "bottom-content">
                <p><strong>Телефон для связи: </strong> ${phone}</p>
                <p><strong>Город: </strong> ${car.getCity()}</p>
                <p><strong>Имя пользователя: </strong> ${pageContext.servletContext.getAttribute("database").getUserById(car.getUser_id()).getName()}</p>
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
        <img class = "logo_bottom" src="/icons/логотип-без-фона.png">
    </div>

</div>
</body>
</html>
