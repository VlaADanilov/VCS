package org.servlets.list_servlets;

import org.DB.DB_helper;
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

@WebServlet("/user_cars")
public class User_cars_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = db_helper.getUserById(Integer.parseInt(req.getParameter("user_id"))).getName();
        List<Auto_model> list = null;
        String brand_id = req.getParameter("brand_id");
        String model = req.getParameter("model");
        String sort = req.getParameter("sort");
        String city = req.getParameter("city");
        if (brand_id == null){
            list = db_helper.getAllAuto(username);
        }
        else{
            list = db_helper.getFilterAuto(brand_id, model, sort, req.getParameter("user_id"),city);
        }
        req.setAttribute("list", list);
        req.setAttribute("whereBack", "user");
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "None","None"));
        brands.addAll(db_helper.getAllBrands());
        req.setAttribute("brands", brands);
        String[] uris = req.getRequestURI().split("/");
        String uri = uris[uris.length - 1];
        req.setAttribute("uri", "/" + uri);
        req.setAttribute("back", "all_users");
        req.getRequestDispatcher("/jsps/list_of_cars.jsp").forward(req, resp);
    }
}
