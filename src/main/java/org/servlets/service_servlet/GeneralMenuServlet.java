package org.servlets.service_servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("")
public class GeneralMenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("error") != null) {
            req.setAttribute("error", req.getSession().getAttribute("error"));
            req.getSession().removeAttribute("error");
        }
        req.getRequestDispatcher("/WEB-INF/jsps/general_menu.jsp").forward(req, resp);
    }


}
