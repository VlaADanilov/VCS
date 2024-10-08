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

@WebServlet("/profile")
public class Profile_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
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
            }
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/");
    }
}
