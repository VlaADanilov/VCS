<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 29.09.2024
  Time: 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java"  contentType="text/html;charset=utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="/styles/add.css">
<fmt:requestEncoding value="UTF-8"/>
<html lang="ru">
<head>
    <title>Update car</title>
    <link rel="icon" href="/pages/ico.png" type="image/png">
    <meta charset="UTF-8">
</head>
<body>
    <div id = "wrapper">
        <div id = "header" align="center">
            <img class="myImage" src="/icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}${uri}'">
            <h1>Обновить данные</h1>
        </div>
        <div id = "content" align="center">
            <form method="post" accept-charset="UTF-8">
                <label for="brand_select">Марка:</label>
                <select name="brand" id="brand_select">
                    <c:forEach var = "brand_id" items="${list}" >
                        <option value="${brand_id.getId()}">${brand_id.getName()}</option>
                    </c:forEach>
                </select>
                <p><label> Модель автомобиля:
                    <input type = "text" name = "car_model" placeholder="${car.getModel()}">
                </label></p>
                <p><label> Год выпуска:
                    <input type="number" name = "year" placeholder="${car.getYear()}">
                </label></p>
                <p><label> Цена:
                    <input type="number" name = "price" placeholder="${car.getPrice()}">
                </label></p>
                <p><label> Пробег:
                    <input type="number" name = "mileage" placeholder="${car.getMileage()}">
                </label></p>
                <p><label> Город:
                    <input type="text" name = "city" placeholder="${car.getCity()}">
                </label></p>
                <p><label> Описание:
                    <input type="text" name = "description" placeholder="${car.getDescription()}">
                </label></p>
                <p>
                    <button class = "but" type="submit">Подтвердить</button>
                </p>
                <p></p>
            </form>
        </div>
        <div id = "footer">
            <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
            <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
            <img class = "logo_bottom" src="/icons/логотип-без-фона.png">
        </div>
    </div>
</body>
</html>
