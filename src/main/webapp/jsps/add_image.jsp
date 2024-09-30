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
</head>
<body>
    <div id = "wrapper">
        <div id="header" align="center">
            <c:if test="${param.whereBack.equals('all')}">
                <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/info?number=${param.auto_id}&whereBack=all'">
            </c:if>
            <c:if test="${param.whereBack.equals('my')}">
                <img class = "myImage" src="icons/back.jpg" onclick="location.href='${pageContext.servletContext.contextPath}/info?number=${param.auto_id}&whereBack=my'">
            </c:if>
            <h1> Add image to this car</h1>
        </div>
        <div align="center">
            <form method="post" enctype="multipart/form-data">
                <input type="file" name="image"  accept="image/*"/><br/><br/>
                <input id = "sub" type="submit"/>
            </form>
        </div>
    </div>
</body>
</html>
