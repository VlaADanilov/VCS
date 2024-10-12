package org.servlets.service_servlet;

import org.DB.DB_helper;
import org.models.Report;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/list/info/report","/my_cars/info/report","/my_likes/info/report","/user_cars/info/report"})
public class Report_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("uri", collectTheString(req.getRequestURI()));
        req.getRequestDispatcher("/jsps/report_car.jsp").forward(req, resp);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String text = req.getParameter("desc");
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        int user_id =  db_helper.getUser((String) req.getSession().getAttribute("username")).getId();
        if (text == null || text.isEmpty()) {
            req.setAttribute("flag", "false");
            doGet(req, resp);
        }
        else{
            db_helper.addReport(new Report(auto_id,text,user_id));
            System.out.println(req.getParameter("uri"));
            resp.sendRedirect(req.getContextPath() + req.getParameter("uri") + "?number=" + auto_id);
        }
    }
}
