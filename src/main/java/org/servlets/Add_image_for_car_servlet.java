package org.servlets;

import org.DB.DB_helper;
import org.DB.MySQL_helper;

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

@WebServlet("/image")
@MultipartConfig(maxFileSize = 16177216)
public class Add_image_for_car_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jsps/add_image.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        String whereBack = req.getParameter("whereBack");
        Part part = req.getPart("image");
        if (part != null) {
            InputStream is = part.getInputStream();
            boolean flag = db_helper.addImageToThisAuto(is, auto_id);
            req.setAttribute("flag", flag);
        } else{
            req.setAttribute("flag", "false");
        }
        //resp.sendRedirect("/info?number="+auto_id+"&whereBack="+whereBack);
        doGet(req,resp);
    }
}
