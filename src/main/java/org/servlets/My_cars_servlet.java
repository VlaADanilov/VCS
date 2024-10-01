package org.servlets;

import org.DB.MySQL_helper;
import org.models.Auto_model;
import org.models.Brand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/my_cars")
public class My_cars_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auto_model> list = null;
        String brand_id = req.getParameter("brand_id");
        String model = req.getParameter("model");
        String sort = req.getParameter("sort");
        if (brand_id == null){
            list = MySQL_helper.getAllAuto((String) req.getSession().getAttribute("username"));
        }
        else{
            list = MySQL_helper.getFilterAuto(brand_id, model, sort, MySQL_helper.getUser((String) req.getSession().getAttribute("username")).getId() + "");
        }
        req.setAttribute("list", list);
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "None","None"));
        brands.addAll(MySQL_helper.getAllBrands());
        req.setAttribute("brands", brands);
        req.setAttribute("whereBack", "my");
        req.getRequestDispatcher("jsps/list_of_cars.jsp").forward(req, resp);
    }
}
