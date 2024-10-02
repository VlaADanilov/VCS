<%@ page import="org.DB.MySQL_helper" %>
<%@ page import="java.io.OutputStream" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 02.10.2024
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%
    byte[] list = MySQL_helper.getImageFromThisEmp(Integer.parseInt(request.getParameter("emp_id")));
    response.setContentType("image/*");
    OutputStream os = response.getOutputStream();
    os.write(list);
    os.flush();
    os.close();
%>
