package org.servlets.service_servlet;

import org.DB.DB_helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_my_account")
public class DelteMyAccountServlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getSession().getAttribute("username").toString();
        db_helper.deleteUserByName(username);
        req.getSession().removeAttribute("username");
        req.getSession().removeAttribute("status");
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
