package org.servlets;

import org.DB.DB_helper;
import org.models.Report;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/report")
public class report_servlet extends HttpServlet {
    private DB_helper db_helper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db_helper = (DB_helper) config.getServletContext().getAttribute("database");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("jsps/report_car.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String text = req.getParameter("desc");
        int auto_id = Integer.parseInt(req.getParameter("auto_id"));
        int user_id =  db_helper.getUser((String) req.getSession().getAttribute("username")).getId();
        if (text == null || text.isEmpty()) {
            req.setAttribute("flag", "false");
            doGet(req, resp);
        }
        else{
            db_helper.addReport(new Report(auto_id,text,user_id));
            String whereBack = req.getParameter("whereBack");
            resp.sendRedirect(req.getContextPath() + "/back_dispetcher?whereBack=" + whereBack + "&from=toInfo&number=" + auto_id);
        }
    }
}
