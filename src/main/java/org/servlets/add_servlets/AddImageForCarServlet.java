package org.servlets.add_servlets;

import org.DB.DBHelper;

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

@WebServlet(urlPatterns = {"/list/info/image","/my_cars/info/image","/my_likes/info/image","/user_cars/info/image","/list_of_reports/info/image","/image"})
@MultipartConfig(maxFileSize = 16177216)
public class AddImageForCarServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result;
        if(collectTheString(req.getRequestURI()).equals("/")){
            result = "/my_cars/info?number=" + req.getParameter("auto_id");
        }else{
            result = collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id");
        }
        req.setAttribute("uri", result);
        req.getRequestDispatcher("/WEB-INF/jsps/add_image.jsp").forward(req, resp);
    }

    private String collectTheString(String uri){
        String[] arr = uri.split("/");
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
            if(is.available() == 0){
                req.setAttribute("flag", "false");
            }
            else{
                boolean flag = db_helper.addImageToThisAuto(is, generateStr() ,auto_id);
                req.setAttribute("flag", flag);
            }
        } else{
            req.setAttribute("flag", "false");
        }
        doGet(req,resp);
    }

    private String generateStr(){
        Random random = new Random();
        int length = random.nextInt(11) + 5; // Случайная длина от 5 до 15 символов
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int charType = random.nextInt(2); // 0 - буква, 1 - цифра, 2 - специальный символ
            switch (charType) {
                case 0:
                    sb.append((char) (random.nextInt(26) + 'a')); // Случайная буква в нижнем регистре
                    break;
                case 1:
                    sb.append(random.nextInt(10)); // Случайная цифра
                    break;
            }
        }

        return sb.toString();
    }
}
