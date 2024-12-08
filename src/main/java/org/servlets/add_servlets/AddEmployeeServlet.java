package org.servlets.add_servlets;

import org.DB.DBHelper;
import org.models.Employee;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add_employee")
@MultipartConfig(maxFileSize = 16177216)
public class AddEmployeeServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsps/add_employee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("his_name");
        if (req.getParameter("his_name").isEmpty() || db_helper.getUser(username) == null
        || req.getParameter("name").isEmpty() || req.getParameter("profession").isEmpty()
        || req.getParameter("desc").isEmpty()) {
            req.setAttribute("flag", "false");
        }
        else{
            Employee employee = new Employee();
            employee.setName(req.getParameter("name"));
            employee.setProfession(req.getParameter("profession"));
            employee.setDescription(req.getParameter("desc"));
            employee.setUser_id(db_helper.getUser(username).getId());
            db_helper.addEmployee(employee);
            if (!db_helper.getUser(username).getStatus().equals("owner")) {
                db_helper.changeStatusThisUser(db_helper.getUser(username).getId(), "admin");
            }
            req.setAttribute("flag", "true");
            req.setAttribute("emp_id", db_helper.getEmpIdByName(employee.getName()));
        }
        doGet(req, resp);
    }
}
