package org.servlets;

import org.DB.MySQL_helper;
import org.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registr")
public class Registr_servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if(req.getParameter("nameReq") != null && req.getParameter("passwordReq") != null){
            String name = req.getParameter("nameReq");
            String password = req.getParameter("passwordReq");
            String phone = "+7" + req.getParameter("phoneReq");
            if (MySQL_helper.getUser(name) != null){
                resp.sendRedirect(getServletContext().getContextPath()+"/");
                return;
            }
            MySQL_helper.addUserToDatabase(new User(name,password,"default",phone));
            req.getSession().setAttribute("username",name);
            req.getSession().setAttribute("status",MySQL_helper.getUser(name).getStatus());
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/");
    }
}
