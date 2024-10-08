package org.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dispetcher")
public class List_Dispetcher_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String whereBack = req.getParameter("whereBack");
        String brand_id = req.getParameter("brand"); //brand_id или 0
        String model = req.getParameter("car_model");
        String city = req.getParameter("city");
        String sort = req.getParameter("sort"); // priceUp, priceDown, yearDown, yearUp, mileageDown, mileageUp, none
        String url = getServletContext().getContextPath();
        switch (whereBack){
            case "all":
                url += "/list";
                break;
            case "my":
                url += "/my_cars";
                break;
            case "user":
                url += "/user_cars";
                break;
            case "like":
                url += "/my_likes";
                break;
        }
        url += "?brand_id=" + brand_id + "&model=" + model + "&sort=" + sort + "&city=" + city;
        if (req.getParameter("user_id") != null && !req.getParameter("user_id").isEmpty()) {
            url += "&user_id=" + req.getParameter("user_id");
        }
        req.getRequestDispatcher(url).forward(req, resp);
    }
}
