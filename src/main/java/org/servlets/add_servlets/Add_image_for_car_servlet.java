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
import java.util.Arrays;

@WebServlet(urlPatterns = {"/list/info/image","/my_cars/info/image","/my_likes/info/image","/user_cars/info/image"})
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
        req.setAttribute("uri", collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
        req.getRequestDispatcher("/jsps/add_image.jsp").forward(req, resp);
    }

    private String collectTheString(String uri){
        String[] arr = uri.split("/");
        System.out.println(Arrays.toString(arr));
        String rez = "/";
        for (int i = 1; i < arr.length - 1; i++) {
            rez += arr[i];
            if (i != arr.length - 2) {
                rez += "/";
            }
        }
        return rez;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        Part part = req.getPart("image");
        if (part != null) {
            InputStream is = part.getInputStream();
            boolean flag = db_helper.addImageToThisAuto(is, auto_id);
            req.setAttribute("flag", flag);
        } else{
            req.setAttribute("flag", "false");
        }
        doGet(req,resp);
    }
}
