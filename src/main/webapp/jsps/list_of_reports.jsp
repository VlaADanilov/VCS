<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.10.2024
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/w3.css">
<html>
<head>
    <title>List of reports</title>
    <script type="text/javascript" src="js/functionsForDelete.js"></script>
    <link rel="icon" href="pages/ico.png" type="image/png">
</head>
<body>
<div id = wrapper>
    <div id = "header" align="center">
        <img class="myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/'">
        <h1>Список жалоб</h1>
    </div>
    <div>
        <ul class = "list1a">
            <c:forEach var = "report" items="${list}" >
                <li>
                    <p class="nameOfAuthor">${pageContext.servletContext.getAttribute("database").getUserById(report.getUser_id()).getName()  }
                                            ${pageContext.servletContext.getAttribute("database").getUserById(report.getUser_id()).getPhone()}
                    </p>
                    <img name = "delete" id = "del" class="myImage2" src="icons/delete-1487-svgrepo-com.svg" onclick="openForm(${report.getId()})">
                    <p>
                        <b>Номер объявления: </b>
                        <c:out value="${report.getAuto_id()}" />
                        <button style="color: #337AB7" onclick="location.href = '${pageContext.servletContext.contextPath}/info?number=${report.getAuto_id()}&whereBack=report'">Посмотреть объявление</button>
                    </p>
                    <p>
                        <b> Комментарий: </b>
                        <c:out value="${report.getComment()}" />
                    </p>

                    <div class="noner" id = "success${report.getId()}">
                        <p>Вы уверены?</p>
                        <div align="center">
                            <button onclick="location.href='${pageContext.servletContext.contextPath}/delete_report?report_id=${report.getId()}'">Да</button>
                            <button onclick="closeForm(${report.getId()})">Нет</button>
                        </div>

                    </div>
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
