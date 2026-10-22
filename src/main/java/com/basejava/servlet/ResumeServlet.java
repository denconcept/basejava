package com.basejava.servlet;

import com.basejava.Config;
import com.basejava.model.*;
import com.basejava.storage.Storage;
import com.basejava.util.HtmlUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.basejava.util.DateUtil.parse;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {

    private enum THEME {
        dark, light, purple
    }

    private Storage storage;
    private final Set<String> themes = new HashSet<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
        for (THEME t : THEME.values()) {
            themes.add(t.name());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.isEmpty());
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(type);
            } else {
                resume.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (values == null || (HtmlUtil.isEmpty(value) && values.length < 2)) {
                resume.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> {
                        resume.setSection(type, new TextSection(value));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> descriptions = new ArrayList<>();
                        for (String item : values) {
                            if (!HtmlUtil.isEmpty(item)) {
                                descriptions.add(item);
                            }
                        }
                        resume.setSection(type, new ListSection(descriptions));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Company.Period> periods = new ArrayList<>();
                                String pfx = type.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        periods.add(new Company.Period(
                                                parse(startDates[j]),
                                                parse(endDates[j]),
                                                titles[j],
                                                descriptions[j]
                                        ));
                                    }
                                }
                                companies.add(new Company(new Link(name, urls[i]), periods));
                            }
                        }
                        resume.setSection(type, new CompanySection(companies));
                    }
                }
            }
        }
        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume?theme=" + getTheme(request));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("theme", getTheme(request));
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume?theme=" + getTheme(request));
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = Resume.EMPTY;
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            ListSection listSection = (ListSection) section;
                            List<String> emptyFirstDescriptions = new ArrayList<>();
                            emptyFirstDescriptions.add("");
                            if (listSection != null) {
                                emptyFirstDescriptions.addAll(listSection.getDescription());
                            }
                            section = new ListSection(emptyFirstDescriptions);
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            CompanySection companySection = (CompanySection) section;
                            List<Company> emptyFirstCompanies = new ArrayList<>();
                            emptyFirstCompanies.add(Company.EMPTY);
                            if (companySection != null) {
                                for (Company company : companySection.getCompanies()) {
                                    List<Company.Period> emptyFirstPeriods = new ArrayList<>();
                                    emptyFirstPeriods.add(Company.Period.EMPTY);
                                    emptyFirstPeriods.addAll(company.getPeriods());
                                    emptyFirstCompanies.add(new Company(company.getHomePage(), emptyFirstPeriods));
                                }
                            }
                            section = new CompanySection(emptyFirstCompanies);
                            break;
                    }
                    resume.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.setAttribute("theme", getTheme(request));
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private String getTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return themes.contains(theme) ? theme : THEME.light.name();
    }
}