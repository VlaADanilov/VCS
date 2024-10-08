<%@ page import="org.DB.MySQL_helper" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="org.DB.DB_helper" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 02.10.2024
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%
    DB_helper db_helper = (DB_helper) getServletConfig().getServletContext().getAttribute("database");
    byte[] list = db_helper.getImageFromThisEmp(Integer.parseInt(request.getParameter("emp_id")));
    response.setContentType("image/*");
    OutputStream os = response.getOutputStream();
    os.write(list);
    os.flush();
    os.close();
%>
