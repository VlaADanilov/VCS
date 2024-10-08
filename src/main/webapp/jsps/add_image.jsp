<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.09.2024
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="styles/add_image.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Add image</title>
    <link rel="icon" href="pages/ico.png" type="image/png">
</head>
<body>
    <div id = "wrapper">
        <div id="header" align="center">
            <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/back_dispetcher?from=toInfo&number=${param.auto_id}&whereBack=all'">
            <h1>Добавить фотографию к объявлению</h1>
        </div>
        <div align="center">
            <form method="post" enctype="multipart/form-data">
                <input type="file" name="image"  accept="image/*"/><br/><br/>
                <input id = "sub" type="submit"/>
            </form>
            <c:if test="${flag != null}">
                <c:if test="${flag}">
                    <p style="color: green">Успешно</p>
                </c:if>
                <c:if test="${!flag}">
                    <p style="color: red">Ошибка</p>
                </c:if>
            </c:if>
        </div>
        <div id = "footer">
            <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
            <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
            <img class = "logo_bottom" src="icons/логотип-без-фона.png">
        </div>
    </div>
</body>
</html>
