<%@ page import="java.util.List" %>
<%@ page import="org.DB.MySQL_helper" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="org.DB.DB_helper" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.09.2024
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%
    DB_helper db_helper = (DB_helper) getServletConfig().getServletContext().getAttribute("database");
    List<byte[]> list = db_helper.getImageFromThisAuto(Integer.parseInt(request.getParameter("auto_id")));
    int numb = Integer.parseInt(request.getParameter("number"));
    response.setContentType("image/*");
    OutputStream os = response.getOutputStream();
    os.write(list.get(numb - 1));
    os.flush();
    os.close();
%>

