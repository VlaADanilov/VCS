package org.servlets.add_servlets;

import org.DB.DB_helper;

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

@WebServlet("/emp_image")
@MultipartConfig(maxFileSize = 16177216)
public class Add_image_for_emp_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int emp_id = Integer.parseInt(req.getParameter("emp_id"));
        Part part = req.getPart("image");
        if (part != null) {
            InputStream is = part.getInputStream();
            db_helper.addImageToThisEmp(is, emp_id);
        } else{
            System.out.println("Ошибка");
        }
        resp.sendRedirect(req.getContextPath() + "/list_of_emp");
    }
}
