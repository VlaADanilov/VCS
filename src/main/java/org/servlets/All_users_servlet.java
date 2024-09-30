package org.servlets;

import org.DB.MySQL_helper;
import org.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all_users")
public class All_users_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> list = MySQL_helper.getAllUsers();
        list.removeIf(user -> user.getName().equals(req.getSession().getAttribute("username")));
        req.setAttribute("list", list);
        req.getRequestDispatcher("jsps/all_users.jsp").forward(req, resp);
    }
}
