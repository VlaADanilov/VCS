package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/doAdmin")
public class DoAdminThisUser_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        String status = "";
        if(MySQL_helper.getUserById(user_id).getStatus().equals("default")) {
            status = "admin";
        }else{
            status = "default";
        }
        MySQL_helper.changeStatusThisUser(user_id, status);
        resp.sendRedirect(req.getContextPath() + "/all_users");
    }
}
