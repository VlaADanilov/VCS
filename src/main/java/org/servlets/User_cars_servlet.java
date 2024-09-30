package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user_cars")
public class User_cars_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = MySQL_helper.getUserById(Integer.parseInt(req.getParameter("user_id"))).getName();
        req.setAttribute("list", MySQL_helper.getAllAuto(username));
        req.setAttribute("whereBack", "user");
        req.getRequestDispatcher("jsps/list_of_cars.jsp").forward(req, resp);
    }
}
