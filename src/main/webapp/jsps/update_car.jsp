<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 29.09.2024
  Time: 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" href="styles/update.css">
<html>
<head>
    <title>Update car</title>
    <meta charset="utf-8" />
</head>
<body>
    <div id = "wrapper">
        <div id = "header" align="center">
            <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/info?number=${param.auto_id}&whereBack=${param.whereBack}'">
            <h1>Update car in autoservise</h1>
        </div>
        <div id = "content" align="center">
            <form method="post">
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
                <p>
                    <button class = "but" type="submit">Подтвердить</button>
                </p>
                <p></p>
            </form>
        </div>
        <div id = "footer">
            <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
            <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
        </div>
    </div>
</body>
</html>
