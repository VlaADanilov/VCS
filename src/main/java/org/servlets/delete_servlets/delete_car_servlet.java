package org.servlets.delete_servlets;

import org.DB.DB_helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/list/info/delete","/my_cars/info/delete","/my_likes/info/delete","/user_cars/info/delete","/list_of_reports/info/delete"})
public class delete_car_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("uri", collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
        req.getRequestDispatcher("/WEB-INF/jsps/delete.jsp").forward(req, resp);
    }

    private String collectTheString(String uri){
        String[] arr = uri.split("/");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = db_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id"))).getUser_id();
        db_helper.deleteAutoById(Integer.parseInt(req.getParameter("auto_id")));

        String end = collectTheString2(req.getRequestURI());
        if(end.split("/")[end.split("/").length-1].equals("user_cars")){
            end += "?user_id=" + user_id;
        }

        resp.sendRedirect(req.getContextPath() + end);
    }

    private String collectTheString2(String uri){
        String[] arr = uri.split("/");
        String rez = "/";
        for (int i = 1; i < arr.length - 2; i++) {
            rez += arr[i];
            if (i != arr.length - 3) {
                rez += "/";
            }
        }
        return rez;
    }
}
