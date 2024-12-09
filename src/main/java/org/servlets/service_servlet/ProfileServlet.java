package org.servlets.service_servlet;

import org.DB.DBHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("name") != null && req.getParameter("password") != null) {
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            if (db_helper.checkUsersPassword(name,password)){
                req.getSession().setAttribute("username",name);
                req.getSession().setAttribute("status",db_helper.getUser(name).getStatus());
            } else{
                req.getSession().setAttribute("error","Неверные имя пользователя или пароль");
            }
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/");
    }
}
