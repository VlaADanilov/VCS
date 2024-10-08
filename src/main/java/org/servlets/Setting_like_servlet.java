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

@WebServlet("/setting_like")
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
        resp.sendRedirect(req.getContextPath() + "/info?number=" + auto_id + "&whereBack="  + req.getParameter("whereBack"));
    }
}
