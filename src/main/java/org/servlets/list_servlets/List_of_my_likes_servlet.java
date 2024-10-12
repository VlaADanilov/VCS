package org.servlets.list_servlets;

import org.DB.DB_helper;
import org.models.Auto_model;
import org.models.Brand;
import org.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/my_likes")
public class List_of_my_likes_servlet extends HttpServlet {
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
        User user = db_helper.getUser((String) req.getSession().getAttribute("username"));
        if (brand_id == null){
            list = db_helper.getAutoByThisIds(db_helper.getAllLikes(user.getId()));
        }
        else{
            list = db_helper.getFilterAutoLike(brand_id, model, sort, null, user.getId(),city);
        }
        req.setAttribute("list", list);
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "None","None"));
        brands.addAll(db_helper.getAllBrands());
        req.setAttribute("brands", brands);
        req.setAttribute("whereBack", "like");
        String[] uris = req.getRequestURI().split("/");
        String uri = uris[uris.length - 1];
        req.setAttribute("uri", "/" + uri);
        req.setAttribute("back", uris[uris.length - 2]);
        req.getRequestDispatcher("/jsps/list_of_cars.jsp").forward(req, resp);
    }
}
