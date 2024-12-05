package org.servlets.delete_servlets;

import org.DB.DBHelper;
import org.models.AutoModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet(urlPatterns = {"/list/info/delete_image","/my_cars/info/delete_image","/my_likes/info/delete_image","/user_cars/info/delete_image","/list_of_reports/info/delete_image"})
public class DeleteImageServlet extends HttpServlet {
    private DBHelper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DBHelper) config.getServletContext().getAttribute("database");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AutoModel auto = db_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id")));
        req.setAttribute("brand", db_helper.getBrandById(auto.getBrand_id()));
        req.setAttribute("list", createList(auto));
//        if (req.getSession().getAttribute("username") != null){
//            User user = db_helper.getUser((String) req.getSession().getAttribute("username"));
//            boolean flag = db_helper.checkPermission(user.getName(), auto.getId()) || !user.getStatus().equals("default");
//            req.setAttribute("pravo", flag);
//        }
        req.setAttribute("uri", collectTheString(req.getRequestURI()) + "?number=" + req.getParameter("auto_id"));
        req.getRequestDispatcher("/WEB-INF/jsps/list_for_delete_images.jsp").forward(req, resp);
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

    private List<Integer> createList(AutoModel auto) {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= db_helper.getCountOfImageFromThisAuto(auto.getId()); i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int number = Integer.parseInt(req.getParameter("numbIm"));
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        db_helper.deleteImageById(db_helper.getImageIdFromThisAutoWithNumber(auto_id, number));
        doGet(req, resp);
    }
}
