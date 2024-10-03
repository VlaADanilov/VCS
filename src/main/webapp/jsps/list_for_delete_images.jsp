<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 03.10.2024
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="styles/style.css">
<html>
<head>
    <title>List for delete images</title>
</head>
<body>
<div id = "wrapper">
    <div id = "header" align="center">
        <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/info?number=${param.auto_id}&whereBack=${param.whereBack}'">
        <h1> Delete images from this car</h1>
    </div>
    <c:if test="${pravo}">
        <div align="center" id = "content">
            <div id = "left-content">
                <ul>
                    <c:forEach var = "numb" items="${list}" >
                        <div align="center">
                            <p>
                                <img class = "carImage" src="jsps/getImage.jsp?auto_id=${param.auto_id}&number=${numb}" width="400px">
                                <form method="post">
                                    <input type="hidden" name = "numbIm" value="${numb}">
                                    <button type="submit">Удалить</button>
                                </form>
                            </p>
                            <p></p>
                        </div>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>

    <c:if test="${!pravo}">
        <div align="center">
            <h1>Не имеете права!</h1>
        </div>
    </c:if>
    <div class="clear"></div>
    <div id="footer">
        <p class="fon"><strong>Телефон:<br> +7 953 015 62 18</strong> </p>
        <p class="mail"><strong>E-mail<br>helloampro@gmail.com</strong></p>
        <img class = "logo_bottom" src="icons/логотип-без-фона.png">
    </div>

</div>
</body>
</html>
