package org.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/back_dispetcher")
public class Back_Dispetcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String where = req.getParameter("whereBack");
        switch (from) {
            case "list":
                switch (where) {
                    case "user":
                        resp.sendRedirect(req.getContextPath() + "/all_users");
                        break;
                    default:
                        resp.sendRedirect(req.getContextPath() + "/");
                }
                break;
            case "filter": case "fromInfo":
                switch (where) {
                    case "user":
                        resp.sendRedirect(req.getContextPath() + "/user_cars?user_id=" + req.getParameter("user_id"));
                        break;
                    case "all":
                        resp.sendRedirect(req.getContextPath() + "/list");
                        break;
                    case "my":
                        resp.sendRedirect(req.getContextPath() + "/my_cars");
                        break;
                    case "like":
                        resp.sendRedirect(req.getContextPath() + "/my_likes");
                        break;
                    case "report":
                        resp.sendRedirect(req.getContextPath() + "/list_of_reports");
                }
                break;
            case "toInfo":
                resp.sendRedirect(req.getContextPath() + "/info?number=" + req.getParameter("number") + "&whereBack=" + where);
                break;
        }
    }
}
