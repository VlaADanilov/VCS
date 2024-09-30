package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/my_cars")
public class My_cars_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", MySQL_helper.getAllAuto((String) req.getSession().getAttribute("username")));
        req.setAttribute("whereBack", "my");
        req.getRequestDispatcher("jsps/list_of_cars.jsp").forward(req, resp);
    }
}
