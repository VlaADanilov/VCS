package org.servlets;

import org.DB.DB_helper;
import org.DB.MySQL_helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class delete_car_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsps/delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = db_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id"))).getUser_id();
        db_helper.deleteAutoById(Integer.parseInt(req.getParameter("auto_id")));
        String whereBack = req.getParameter("whereBack");
        String end = "";
        switch (whereBack) {
            case "all":
                end = "/list";
                break;
            case "my":
                end = "/my_cars";
                break;
            default:
                end = "user_cars?user_id=" + user_id;
        }
        resp.sendRedirect(req.getContextPath() + end);
    }
}
