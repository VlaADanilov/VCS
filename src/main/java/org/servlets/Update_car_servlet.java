package org.servlets;

import org.DB.MySQL_helper;
import org.models.Brand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/update")
public class Update_car_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Brand brand = new Brand("Не изменять", null);
        brand.setId(-1);
        LinkedList<Brand> list = new LinkedList<Brand>();
        list.add(brand);
        list.addAll(MySQL_helper.getAllBrands());
        req.setAttribute("list", list);
        req.setAttribute("car", MySQL_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id"))));
        req.getRequestDispatcher("jsps/update_car.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Integer.parseInt(req.getParameter("brand")) != -1) {
            MySQL_helper.updateAutoById_brand(Integer.parseInt(req.getParameter("auto_id")),Integer.parseInt(req.getParameter("brand")));
        }
        if (!req.getParameter("car_model").isEmpty()) {
            MySQL_helper.updateAutoById_model(Integer.parseInt(req.getParameter("auto_id")),req.getParameter("car_model"));
        }
        if (!req.getParameter("year").isEmpty()) {
            MySQL_helper.updateAutoById_year(Integer.parseInt(req.getParameter("auto_id")), Integer.parseInt(req.getParameter("year")));
        }
        if (!req.getParameter("price").isEmpty()) {
            MySQL_helper.updateAutoById_price(Integer.parseInt(req.getParameter("auto_id")), Integer.parseInt(req.getParameter("price")));
        }
        if (!req.getParameter("mileage").isEmpty()) {
            MySQL_helper.updateAutoById_mileage(Integer.parseInt(req.getParameter("auto_id")), Integer.parseInt(req.getParameter("mileage")));
        }
        resp.sendRedirect(req.getContextPath()+"/info?number="+req.getParameter("auto_id")+"&whereBack="+req.getParameter("whereBack"));
    }
}
