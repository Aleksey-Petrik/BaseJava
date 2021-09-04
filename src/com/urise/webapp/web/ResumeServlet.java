package com.urise.webapp.web;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageDataBase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = null;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = new Resume(UUID.randomUUID().toString(), "");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal!");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        if ((uuid != null && uuid.trim().length() != 0) &&
                (fullName != null && fullName.trim().length() != 0)) {

            Resume oldResume;
            Resume newResume;
            //Если NotExistStorageException значит сохраним новое резюме
            try {
                oldResume = storage.get(uuid);
            } catch (NotExistStorageException e) {
                oldResume = null;
            }
            newResume = new Resume(uuid, fullName);

            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                AbstractSection section = null;
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(value);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListTextSection();
                        String[] contents = value.split("\r\n");
                        for (String content : contents) {
                            if (content.trim().length() != 0) {
                                ((ListTextSection) section).addContent(content);
                            }
                        }
                        break;
                }
                if (section != null) {
                    newResume.addSection(type, section);
                }
            }

            //Контакты
            for (ContactsType type : ContactsType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    newResume.addContact(type, value);
                }
            }

            if (oldResume == null) {
                storage.save(newResume);
            } else {
                if (!newResume.equals(oldResume)) {
                    storage.update(newResume);
                }
            }
        }
        response.sendRedirect("resume");
    }

}
