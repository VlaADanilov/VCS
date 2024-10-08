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
<html lang="ru">
<head>
    <meta charset="utf-8" />
    <title>List of cars</title>
    <link rel="icon" href="pages/ico.png" type="image/png">
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/back_dispetcher?whereBack=${whereBack}&from=list'">
        <h1>Список объявлений</h1>
    </div>
    <div align="center">
        <form action="${pageContext.servletContext.contextPath}/dispetcher" method="get">
            <label for="brand_select">Марка:</label>
            <select name="brand" id="brand_select">
                <c:forEach var = "brand_id" items="${brands}" >
                    <option value="${brand_id.getId()}">${brand_id.getName()}</option>
                </c:forEach>
            </select>
            <label>Модель:</label>
            <input type="text" name="car_model">
            <p>
                <label>Город:</label>
                <input type="text" name="city">
                <label for="brand_select">Сортировка:</label>
                <select name = "sort" id = "sort_select">
                    <option value="none">none</option>
                    <option value="priceUp">По возрастанию цены</option>
                    <option value="priceDown">По убыванию цены</option>
                    <option value="yearUp">По возрастанию года выпуска</option>
                    <option value="yearDown">По убыванию года выпуска</option>
                    <option value="mileageUp">По возрастанию пробега</option>
                    <option value="mileageDown">По убыванию пробега</option>
                    <option value="cityUp">По алфавиту(город)</option>
                    <option value="cityDown">Обратно алфавиту(город)</option>
                </select>
            </p>

            <input type = "hidden" name="whereBack" value="${whereBack}">
            <input type = "hidden" name="user_id" value="${param.user_id}">
            <p>
                <button type="submit">Поиск</button>
            </p>
        </form>
        <button type="button" onclick="location.href='${pageContext.servletContext.contextPath}/back_dispetcher?from=filter&user_id=${param.user_id}&whereBack=${whereBack}'">Очистить фильтр</button>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "car" items="${list}" >
                <li>
                    <p class="nameOfAuthor">${pageContext.servletContext.getAttribute("database").getUserById(car.getUser_id()).getName()  }</p>
                    <p>
                        <b>Марка: </b>
                        <c:out value="${car.getBrand()}" />
                        <b> Модель: </b>
                        <c:out value="${car.getModel()}" />
                        <b> Год выпуска: </b>
                        <c:out value="${car.getYear()}" />
                        <b> Цена: </b>
                        <c:out value="${car.getNicePrice()} руб." />
                        <b> Пробег: </b>
                        <c:out value="${car.getMileage()} км." />
                        <button class="buttonw" type="button" onclick="window.location.href = '${pageContext.servletContext.contextPath}/back_dispetcher?from=toInfo&number=${car.getId()}&whereBack=${whereBack}'">Подробнее</button>
                    </p>
                    <p class = "nameOfCity">${car.getCity()}</p>
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
        <img class = "logo_bottom" src="icons/логотип-без-фона.png">
    </div>
</div>

</body>
</html>


