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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/info")
public class Info_car_servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auto_model auto = MySQL_helper.getAutoById(Integer.parseInt(req.getParameter("number")));
        req.setAttribute("car", auto);
        req.setAttribute("brand", MySQL_helper.getBrandById(auto.getBrand()));
        req.setAttribute("list", createList(auto));
        if (req.getSession().getAttribute("username") != null){
            User user = MySQL_helper.getUser((String) req.getSession().getAttribute("username"));
            boolean flag = user.getId() == auto.getUser_id() || user.getStatus().equals("admin");
            req.setAttribute("pravo", flag);
        }
        req.setAttribute("phone", MySQL_helper.getUserById(auto.getUser_id()).getPhone());
        req.getRequestDispatcher("jsps/info_car.jsp").forward(req, resp);
    }

    private List<Integer> createList(Auto_model auto) {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= MySQL_helper.getCountOfImageFromThisAuto(auto.getId()); i++) {
            list.add(i);
        }
        return list;
    }
}
