package org.servlets;

import org.DB.MySQL_helper;
import org.models.Auto_model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/add")
public class Add_car_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", MySQL_helper.getAllBrands());
        req.getRequestDispatcher("jsps/add_car.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("car_model").isEmpty() || req.getParameter("year").isEmpty()
        || req.getParameter("price").isEmpty() || req.getParameter("mileage").isEmpty()) {
            req.setAttribute("flag", "false");
            doGet(req, resp);
        }
        int brand = Integer.parseInt(req.getParameter("brand"));
        String model = req.getParameter("car_model");
        int year = Integer.parseInt(req.getParameter("year"));
        int price = Integer.parseInt(req.getParameter("price"));
        String username = (String) req.getSession().getAttribute("username");
        int mileage = Integer.parseInt(req.getParameter("mileage"));

        MySQL_helper.addAutoToDatabase(new Auto_model(brand,MySQL_helper.getUser(username).getId(),model,year,price, mileage));
        req.setAttribute("flag", "true");
        resp.sendRedirect(req.getContextPath() + "/my_cars");
    }
}
