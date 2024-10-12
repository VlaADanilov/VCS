package org.servlets.service_servlet;

import org.DB.DB_helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/list/info/setting_like","/my_cars/info/setting_like","/my_likes/info/setting_like","/user_cars/info/setting_like"})
public class Setting_like_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        int user_id = db_helper.getUser((String) req.getSession().getAttribute("username")).getId();
        if (req.getParameter("do").equals("delete")) {
            db_helper.deleteLike(user_id, auto_id);
        }
        else{
            db_helper.addLikeToDatabase(user_id, auto_id);
        }
        resp.sendRedirect(req.getContextPath() + collectTheString(req.getRequestURI()) + "?number=" + auto_id);
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
}
