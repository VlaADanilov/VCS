<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.09.2024
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="/styles/add.css">
<fmt:requestEncoding value="UTF-8"/>
<html>
<head>
    <link rel="icon" href="pages/ico.png" type="image/png">
    <title>Add car</title>
    <meta charset="UTF-8">
</head>
<body>
<div id="wrapper">
    <div id = "header" align="center">
        <img src="/icons/back.jpg" class = "myImage" onclick="location.href='${pageContext.servletContext.contextPath}/'">
        <h1>Добавление объявления</h1>
    </div>
    <div align="center">
        <c:if test="${sessionScope.get('username') != null}">
            <form method="post">
                <label for="brand_select">Марка:</label>
                <select name="brand" id="brand_select">
                    <c:forEach var = "brand_id" items="${list}" >
                        <option value="${brand_id.getId()}">${brand_id.getName()}</option>
                    </c:forEach>
                </select>
                <p><label> Модель автомобиля:
                    <input type = "text" name = "car_model">
                </label></p>
                <p><label> Год выпуска:
                    <input type="number" name = "year">
                </label></p>
                <p><label> Цена:
                    <input type="number" name = "price">
                </label></p>
                <p><label> Пробег:
                    <input type="number" name = "mileage">
                </label></p>
                <p><label> Город:
                    <input type = "text" name = "city">
                </label></p>
                <p>
                    <button class = "but" type="submit">Подтвердить</button>
                </p>
                <p></p>
            </form>
        </c:if>
        <c:if test="${sessionScope.get('username') == null}">
            <p><strong>Сначала зарегистрируйтесь!</strong></p>
            <p><button class = "but" type="submit" onclick="location.href='${pageContext.servletContext.contextPath}/'">Вернуться в меню</button></p>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${flag != null}">
            <c:if test="${flag.equals('true')}">
                <p style="color:green"> Успешно</p>
            </c:if>
            <c:if test="${flag.equals('false')}">
                <p style="color: red">Ошибка</p>
            </c:if>
        </c:if>
    </div>
    <div id = "footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
        <img class = "logo_bottom" src="/icons/логотип-без-фона.png">
    </div>
</div>

</body>
</html>
