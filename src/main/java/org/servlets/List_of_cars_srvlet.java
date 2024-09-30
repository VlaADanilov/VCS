package org.servlets;

import org.DB.MySQL_helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/list")
public class List_of_cars_srvlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", MySQL_helper.getAllAuto());
        req.setAttribute("whereBack", "all");
        req.getRequestDispatcher("jsps/list_of_cars.jsp").forward(req, resp);
    }

    private List<String> carsToStrings(List<?> list){
        return list.stream().map((a) -> a.toString()).collect(Collectors.toList());
    }
}
