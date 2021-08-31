package com.urise.webapp.web;


import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageDataBase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/HTML; charset=UTF-8");
        String uuid = request.getParameter("uuid");

        response.getWriter().write("<!DOCTYPE html>"
                + "<html lang=\"ru\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Document</title>"
                + "</head>"
                + "<body>"
                + "<table border=1>"
                + "<caption>Резюме:</caption>"
                + "<tr>"
                + "<td>UUID</td>"
                + "<td>Full Name</td>"
                + "</tr>"
        );

        if (uuid != null) {
            Resume resume = storage.get(uuid);
            response.getWriter().write("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName()+"</td></tr>");
        } else {
            for(Resume resume : storage.getAllSorted()) {
                response.getWriter().write("<tr><td>" + resume.getUuid() + "</td><td>" + resume.getFullName()+"</td></tr>");
            }
        }

        response.getWriter().write("</table>"
                + "</body>"
                + "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
