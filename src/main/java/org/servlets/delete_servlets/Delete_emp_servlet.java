package org.servlets.delete_servlets;

import org.DB.DB_helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete_emp")
public class Delete_emp_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int emp_id = Integer.parseInt(req.getParameter("emp_id"));
        if(!db_helper.getUserById(db_helper.getEmpById(emp_id).getUser_id()).getStatus().equals("owner")){
            db_helper.changeStatusThisUser(db_helper.getEmpById(emp_id).getUser_id(), "default");
        }
        db_helper.deleteEmpById(emp_id);
        resp.sendRedirect(req.getContextPath() + "/list_of_emp");
    }
}
