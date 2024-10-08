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

@WebServlet("/doAdmin")
public class DoAdminThisUser_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        String status = "";
        if(db_helper.getUserById(user_id).getStatus().equals("default")) {
            status = "admin";
        }else{
            status = "default";
        }
        db_helper.changeStatusThisUser(user_id, status);
        resp.sendRedirect(req.getContextPath() + "/all_users");
    }
}
