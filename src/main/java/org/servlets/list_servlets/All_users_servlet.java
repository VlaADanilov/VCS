package org.servlets.list_servlets;

import org.DB.DB_helper;
import org.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all_users")
public class All_users_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<User> list = db_helper.getAllUsers();
        list.removeIf(user -> user.getName().equals(req.getSession().getAttribute("username")));
        req.setAttribute("list", list);
        req.getRequestDispatcher("/jsps/all_users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<User> list = db_helper.getAllUsers(req.getParameter("filter"));
        list.removeIf(user -> user.getName().equals(req.getSession().getAttribute("username")));
        req.getSession().setAttribute("list", list);
        req.getRequestDispatcher("/jsps/all_users.jsp").forward(req, resp);
    }
}
