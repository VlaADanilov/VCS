package org.servlets;

import org.DB.MySQL_helper;

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
            MySQL_helper.addImageToThisAuto(is, auto_id);
        } else{
            System.out.println("Ошибка");
        }
        resp.sendRedirect("/info?number="+auto_id+"&whereBack="+whereBack);
    }
}
