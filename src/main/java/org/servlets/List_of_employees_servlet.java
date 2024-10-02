package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/list_of_emp")
public class List_of_employees_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", MySQL_helper.getAllEmployees());
        req.getRequestDispatcher("jsps/list_of_emp.jsp").forward(req, resp);
    }
}
