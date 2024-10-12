package org.servlets.add_servlets;

import org.DB.DB_helper;
import org.models.Auto_model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/add")
public class Add_car_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", db_helper.getAllBrands());
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
        String city = req.getParameter("city");
        if (year <= 1900 || price < 0 || mileage < 0 || city.trim().isEmpty()){
            req.setAttribute("flag", "false");
            doGet(req, resp);
            return;
        }

        db_helper.addAutoToDatabase(new Auto_model(db_helper.getBrandById(brand).getName(),db_helper.getUser(username).getId(),model,year,price, mileage, city));
        req.setAttribute("flag", "true");
        int auto_id = db_helper.getLastAutoFromThisUser((String)req.getSession().getAttribute("username"));
        resp.sendRedirect(req.getContextPath() + "/image?auto_id=" + auto_id + "&whereBack=my");
    }
}
