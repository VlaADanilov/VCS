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
<link rel="stylesheet" href="styles/w3.css">
<html>
<head>
    <title>List of emp</title>
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/'">
        <c:if test="${sessionScope.get('status').equals('owner')}">
            <img class="myImage2" src="icons/plus-svgrepo-com.svg" onclick="location.href='${pageContext.servletContext.contextPath}/add_employee'">
        </c:if>
        <h1>List of employees in autoservise</h1>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "emp" items="${list}" >
                <li>
                    <p>
                        <b>ФИО: </b>
                        <c:out value="${emp.getName()}" />
                        <b> Профессия: </b>
                        <c:out value="${emp.getProfession()}" />
                        <b> Описание: </b>
                        <c:out value="${emp.getDescription()}" />
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
        <img class = "logo_bottom" src="icons/логотип-без-фона.png">
    </div>
</div>
</body>
</html>
