package org.servlets;

import org.DB.DB_helper;
import org.DB.MySQL_helper;
import org.models.Employee;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@WebServlet("/add_employee")
@MultipartConfig(maxFileSize = 16177216)
public class Add_employee_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsps/add_employee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
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
            String status = "admin";
            db_helper.changeStatusThisUser(db_helper.getUser(username).getId(), status);
            req.setAttribute("flag", "true");
            req.setAttribute("emp_id", db_helper.getEmpIdByName(employee.getName()));
        }
        doGet(req, resp);
    }

    private String generateStr(){
        Random random = new Random();
        int length = random.nextInt(11) + 5; // Случайная длина от 5 до 15 символов
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int charType = random.nextInt(3); // 0 - буква, 1 - цифра, 2 - специальный символ
            switch (charType) {
                case 0:
                    sb.append((char) (random.nextInt(26) + 'a')); // Случайная буква в нижнем регистре
                    break;
                case 1:
                    sb.append(random.nextInt(10)); // Случайная цифра
                    break;
                case 2:
                    sb.append((char) (random.nextInt(15) + '!')); // Случайный специальный символ
                    break;
            }
        }

        return sb.toString();
    }
}
