package org.servlets;

import org.DB.MySQL_helper;
import org.models.Auto_model;
import org.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/delete_image")
public class Delete_Image_servlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auto_model auto = MySQL_helper.getAutoById(Integer.parseInt(req.getParameter("auto_id")));
        req.setAttribute("brand", MySQL_helper.getBrandById(auto.getBrand()));
        req.setAttribute("list", createList(auto));
        if (req.getSession().getAttribute("username") != null){
            User user = MySQL_helper.getUser((String) req.getSession().getAttribute("username"));
            boolean flag = user.getId() == auto.getUser_id() || user.getStatus().equals("admin") ||
                    user.getStatus().equals("owner");
            req.setAttribute("pravo", flag);
        }

        req.getRequestDispatcher("jsps/list_for_delete_images.jsp").forward(req, resp);
    }

    private List<Integer> createList(Auto_model auto) {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= MySQL_helper.getCountOfImageFromThisAuto(auto.getId()); i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int number = Integer.parseInt(req.getParameter("numbIm"));
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        MySQL_helper.deleteImageById(MySQL_helper.getImageIdFromThisAutoWithNumber(auto_id, number));
        doGet(req, resp);
    }
}
