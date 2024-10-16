package org.servlets.one_car_servlets;

import org.DB.DB_helper;
import org.models.Brand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet(urlPatterns = {"/list/info/update","/my_cars/info/update","/my_likes/info/update","/user_cars/info/update"})
public class Update_car_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Brand brand = new Brand("Не изменять", null);
        brand.setId(-1);
        LinkedList<Brand> list = new LinkedList<>();
        list.add(brand);
        list.addAll(db_helper.getAllBrands());
        req.setAttribute("list", list);
        req.setAttribute("uri", collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
        req.setAttribute("car", db_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id"))));
        req.getRequestDispatcher("/jsps/update_car.jsp").forward(req, resp);
    }

    private String collectTheString(String uri){
        String[] arr = uri.split("/");
        System.out.println(Arrays.toString(arr));
        String rez = "/";
        for (int i = 1; i < arr.length - 1; i++) {
            rez += arr[i];
            if (i != arr.length - 2) {
                rez += "/";
            }
        }
        return rez;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if(Integer.parseInt(req.getParameter("brand")) != -1) {
            db_helper.updateAutoById_brand(Integer.parseInt(req.getParameter("auto_id")),Integer.parseInt(req.getParameter("brand")));
        }
        if (!req.getParameter("car_model").isEmpty()) {
            System.out.println(req.getParameter("car_model"));
            db_helper.updateAutoById_model(Integer.parseInt(req.getParameter("auto_id")),req.getParameter("car_model"));
        }
        if (!req.getParameter("year").isEmpty()) {
            int year = Integer.parseInt(req.getParameter("year"));
            if(year >= 1900 && year <= 2100) {
                db_helper.updateAutoById_year(Integer.parseInt(req.getParameter("auto_id")), year);
            }
        }
        if (!req.getParameter("price").isEmpty()) {
            int price = Integer.parseInt(req.getParameter("price"));
            if (price >= 0) {
                db_helper.updateAutoById_price(Integer.parseInt(req.getParameter("auto_id")), price);
            }
        }
        if (!req.getParameter("mileage").isEmpty()) {
            int mileage = Integer.parseInt(req.getParameter("mileage"));
            if (mileage >= 0) {
                db_helper.updateAutoById_mileage(Integer.parseInt(req.getParameter("auto_id")), mileage);
            }
        }
        if (!req.getParameter("city").isEmpty()) {
            System.out.println(req.getCharacterEncoding());
            System.out.println(req.getParameter("city"));
            db_helper.updateAutoById_city(Integer.parseInt(req.getParameter("auto_id")),req.getParameter("city"));
        }
        resp.sendRedirect(req.getContextPath()+collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
    }
}
