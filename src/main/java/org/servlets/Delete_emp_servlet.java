package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_emp")
public class Delete_emp_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int emp_id = Integer.parseInt(req.getParameter("emp_id"));
        MySQL_helper.changeStatusThisUser(MySQL_helper.getEmpById(emp_id).getUser_id(), "default");
        MySQL_helper.deleteEmpById(emp_id);
        resp.sendRedirect(req.getContextPath() + "/list_of_emp");
    }
}
