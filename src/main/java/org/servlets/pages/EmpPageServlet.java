package org.servlets.pages;

import org.DB.DBHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/getImageEmp")
public class EmpPageServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] list = db_helper.getImageFromThisEmp(Integer.parseInt(request.getParameter("emp_id")));
        response.setContentType("image/*");
        OutputStream os = response.getOutputStream();
        os.write(list);
        os.flush();
        os.close();
    }
}
