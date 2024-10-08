package org.servlets;

import org.DB.DB_helper;
import org.DB.MySQL_helper;
import org.models.Auto_model;
import org.models.Brand;

import javax.servlet.ServletConfig;
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
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auto_model> list = null;
        String brand_id = req.getParameter("brand_id");
        String model = req.getParameter("model");
        String sort = req.getParameter("sort");
        String city = req.getParameter("city");
        if (brand_id == null){
            list = db_helper.getAllAuto((String) req.getSession().getAttribute("username"));
        }
        else{
            list = db_helper.getFilterAuto(brand_id, model, sort, db_helper.getUser((String) req.getSession().getAttribute("username")).getId() + "",city);
        }
        req.setAttribute("list", list);
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "None","None"));
        brands.addAll(db_helper.getAllBrands());
        req.setAttribute("brands", brands);
        req.setAttribute("whereBack", "my");
        req.getRequestDispatcher("jsps/list_of_cars.jsp").forward(req, resp);
    }
}
