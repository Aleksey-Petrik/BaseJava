package com.urise.webapp.web;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ResumeServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(ResumeServlet.class.getName());
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
        Resume resume;
        switch (action) {
            case "delete":
                log.info("Delete - " + uuid);
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                log.info("command View - " + uuid);
                resume = storage.get(uuid);
                break;
            case "edit":
                log.info("command Edit - " + uuid);
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListTextSection.EMPTY;
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            //Добавление пустой организации и пустого периода в каждую организацию
                            ListOrganizationSection organizations = (ListOrganizationSection) section;
                            List<Organization> organizationsPlusEmpty = new ArrayList<>();
                            organizationsPlusEmpty.add(Organization.EMPTY);
                            if (organizations != null) {
                                for (Organization organization : organizations.getList()) {
                                    List<Organization.Period> emptyFirstPeriods = new ArrayList<>();
                                    emptyFirstPeriods.add(Organization.Period.EMPTY);
                                    emptyFirstPeriods.addAll(organization.getPeriods());
                                    organizationsPlusEmpty.add(new Organization(organization.getOrganization(), emptyFirstPeriods));
                                }
                            }
                            section = new ListOrganizationSection(organizationsPlusEmpty);
                            break;
                    }
                    resume.addSection(type, section);
                }
                break;
            case "add":
                resume = Resume.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal!");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            //Контакты
            for (ContactsType type : ContactsType.values()) {
                String value = request.getParameter(type.name());
                if (value != null && value.trim().length() != 0) {
                    newResume.addContact(type, value);
                }
            }
            //Доп.секции
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                String[] values = request.getParameterValues(type.name());
                if (!value.trim().isEmpty() || values.length > 1) {
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
                        case EDUCATION:
                        case EXPERIENCE:
                            List<Organization> organizations = new ArrayList<>();
                            String[] urls = request.getParameterValues(type.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String name = values[i];
                                if (!name.trim().isEmpty()) {
                                    List<Organization.Period> periods = new ArrayList<>();
                                    String pfx = type.name() + i;
                                    String[] startDates = request.getParameterValues(pfx + "startDate");
                                    String[] endDates = request.getParameterValues(pfx + "endDate");
                                    contents = request.getParameterValues(pfx + "contents");
                                    for (int j = 0; j < contents.length; j++) {
                                        if (!contents[j].trim().isEmpty()) {
                                            periods.add(new Organization.Period(DateUtil.parse(startDates[j], DateUtil.MASK_FOR_READ_PERIOD_HTML),
                                                    DateUtil.parse(endDates[j], DateUtil.MASK_FOR_READ_PERIOD_HTML), contents[j]));
                                        }
                                    }
                                    organizations.add(new Organization(new Link(name, urls[i]), periods));
                                }
                            }
                            section = new ListOrganizationSection(organizations);
                            break;
                    }
                    if (section != null) {
                        newResume.addSection(type, section);
                    }
                }
            }

            if (oldResume == null) {
                storage.save(newResume);
            } else if (!newResume.equals(oldResume)) {
                storage.update(newResume);
            }
        }
        response.sendRedirect("resume");
    }

}
