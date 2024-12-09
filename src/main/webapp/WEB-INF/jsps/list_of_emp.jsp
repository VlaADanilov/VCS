<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 02.10.2024
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="/styles/w4.css">
<html>
<head>
    <title>List of emp</title>
    <script type="text/javascript" src="../../js/functionsForDelete.js"></script>
    <link rel="icon" href="/pages/ico.png" type="image/png">
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="/icons/back.jpg" onclick="location.href='../..'">
        <c:if test="${pravo}">
            <img class="myImage2" src="/icons/plus-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}/add_employee'">
        </c:if>
        <h1>Список сотрудников</h1>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "emp" items="${list}" >
                <li>
                        <div id = "left" align="center">
                            <img class = "listImage" src="${pageContext.servletContext.contextPath}/getImageEmp?emp_id=${emp.getId()}">
                        </div>
                        <div id = "right">
                            <p><strong>ФИО</strong>:
                            <c:out value="${emp.getName()}" /></p>
                            <p><strong>Профессия</strong>:
                            <c:out value="${emp.getProfession()}" /></p>
                            <p> <strong>Описание</strong>:
                            <c:out value="${emp.getDescription()}" /></p>
                            <p> <strong>Имя пользователя</strong>:
                                <c:out value="${pageContext.servletContext.getAttribute('database').getUserById(emp.getUser_id()).getName()}" /></p>
                            <p> <strong>Номер телефона</strong>:
                                <c:out value="${emp.getPhone()}" /></p>
                        </div>
                    <c:if test="${sessionScope.get('status').equals('owner')}">
                        <img name = "delete" id = "del" class="myImage2" src="../../icons/delete-1487-svgrepo-com.svg" onclick="openForm(${emp.getId()})">
                        <div class="noner" id = "success${emp.getId()}">
                            <p>Вы уверены?</p>
                            <div align="center">
                                <button onclick="location.href='${pageContext.servletContext.contextPath}/delete_emp?emp_id=${emp.getId()}'">Да</button>
                                <button onclick="closeForm(${emp.getId()})">Нет</button>
                            </div>

                        </div>
                    </c:if>

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
