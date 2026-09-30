package com.basejava.servlet;

import com.basejava.Config;
import com.basejava.model.*;
import com.basejava.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        if (uuid == null || uuid.trim().isEmpty()) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE, PERSONAL -> {
                    String description = request.getParameter(type.name());
                    if (description != null && !description.trim().isEmpty()) {
                        resume.addSection(type, new TextSection(description));
                    } else {
                        resume.getSections().remove(type);
                    }
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    String[] values = request.getParameterValues(type.name());
                    List<String> descriptions = new ArrayList<>();
                    if (values != null) {
                        for (String value : values) {
                            if (value != null && !value.trim().isEmpty()) {
                                descriptions.add(value);
                            }
                        }
                    }
                    if (!descriptions.isEmpty()) {
                        resume.addSection(type, new ListSection(descriptions));
                    } else {
                        resume.getSections().remove(type);
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    String[] companyTitles = request.getParameterValues(type.name() + "_companyTitle");
                    String[] companyUrls = request.getParameterValues(type.name() + "_companyUrl");
                    String[] periodCounts = request.getParameterValues(type.name() + "_periodCount");
                    String[] periodDates = request.getParameterValues(type.name() + "_periodDates");
                    String[] periodTitles = request.getParameterValues(type.name() + "_periodTitle");
                    String[] periodDescriptions = request.getParameterValues(type.name() + "_periodDescription");
                    int periodIndex = 0;
                    List<Company> companies = new ArrayList<>();
                    if (companyTitles != null) {
                        for (int i = 0; i < companyTitles.length; i++) {
                            Company company = new Company(companyTitles[i], companyUrls[i]);
                            int count = Integer.parseInt(periodCounts[i]);
                            for (int j = 0; j < count; j++) {
                                if (periodDates[periodIndex].isEmpty()) {
                                    periodIndex++;
                                    continue;
                                }
                                List<LocalDate> dates = parseDate(periodDates[periodIndex]);
                                company.setPeriod(new Company.Period(dates.getFirst(), dates.getLast(),
                                        periodTitles[periodIndex], periodDescriptions[periodIndex]));
                                periodIndex++;
                            }
                            companies.add(company);
                        }
                        if (!companies.isEmpty()) {
                            resume.addSection(type, new CompanySection(companies));
                        } else {
                            resume.getSections().remove(type);
                        }
                    }
                }
            }
        }
        if (uuid == null) {
            storage.save(resume);
            response.sendRedirect("resume");
        } else {
            storage.update(resume);
            response.sendRedirect("resume?uuid=" + uuid + "&action=view");
        }
    }

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
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "create":
                resume = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private List<LocalDate> parseDate(String periodStr) {
        String[] parts = periodStr.split(" – ");
        String startStr = parts[0].trim();
        String endStr = parts[1].trim();
        LocalDate startDate = LocalDate.parse("01/" + startStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = endStr.equals("Сейчас") ?
                LocalDate.now() :
                LocalDate.parse("01/" + endStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return Arrays.asList(startDate, endDate);
    }
}