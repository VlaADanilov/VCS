<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 02.10.2024
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="styles/add.css">
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="UTF-8"/>
<html>
<head>
    <title>Add employee</title>
    <link rel="icon" href="pages/ico.png" type="image/png">
    <meta charset="UTF-8">
</head>
<body>
<div id="wrapper">
    <div id = "header" align="center">
        <img src="icons/back.jpg" class = "myImage" onclick="location.href='${pageContext.servletContext.contextPath}/list_of_emp'">
        <h1>Добавление сотрудника</h1>
    </div>
    <div align="center">
        <c:if test="${sessionScope.get('status').equals('owner') && flag == null}">
            <form method="post">
                <p><label> ФИО:
                    <input type = "text" name = "name">
                </label></p>
                <p><label> Специализация:
                    <input type="text" name = "profession">
                </label></p>
                <p><label> Описание:
                    <input type="text" name = "desc">
                </label></p>
                <p><label> UserName (создайте аккаунт, если его нет):
                    <input type="text" name = "his_name" >
                </label></p>
                <p>
                    <button class = "but" type="submit">Подтвердить</button>
                </p>
                <p></p>
            </form>
        </c:if>
        <c:if test="${!sessionScope.get('status').equals('owner')}">
            <p><strong>Не имеете парава!</strong></p>
            <p><button class = "but" type="submit" onclick="location.href='${pageContext.servletContext.contextPath}/'">Вернуться в меню</button></p>
        </c:if>
    </div>
    <div align="center">
        <c:if test="${flag != null}">
            <c:if test="${flag.equals('true')}">
                <form action="${pageContext.servletContext.contextPath}/emp_image" method="post" enctype="multipart/form-data">
                    <p><label> Фото:
                        <input type="file" name="image"  accept="image/*"/><br/><br/>
                    </label></p>
                    <input type="hidden" value="${emp_id}" name="emp_id">
                    <button type="submit"> Добавить фото </button>
                </form>
            </c:if>
            <c:if test="${flag.equals('false')}">
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
