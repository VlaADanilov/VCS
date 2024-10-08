package org.servlets;

import org.DB.DB_helper;
import org.models.Auto_model;
import org.models.User;

import javax.servlet.ServletConfig;
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
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auto_model auto = db_helper.getAutoById(Integer.parseInt(req.getParameter("number")));
        req.setAttribute("car", auto);
        req.setAttribute("brand", db_helper.getBrandByName(auto.getBrand()));
        req.setAttribute("list", createList(auto));
        if (req.getSession().getAttribute("username") != null){
            User user = db_helper.getUser((String) req.getSession().getAttribute("username"));
            boolean flag = db_helper.checkPermission(user.getName(), auto.getId()) || !user.getStatus().equals("default");
            req.setAttribute("pravo", flag);
        }
        req.setAttribute("phone", db_helper.getUserById(auto.getUser_id()).getPhone());

        // узнать, у пользователя есть ли в избранных машина
        if (req.getSession().getAttribute("username") != null){
            int user_id = db_helper.getUser((String) req.getSession().getAttribute("username")).getId();
            req.setAttribute("exist", db_helper.checkLike(user_id, auto.getId()));
        }

        req.getRequestDispatcher("jsps/info_car.jsp").forward(req, resp);
    }

    private List<Integer> createList(Auto_model auto) {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= db_helper.getCountOfImageFromThisAuto(auto.getId()); i++) {
            list.add(i);
        }
        return list;
    }
}
