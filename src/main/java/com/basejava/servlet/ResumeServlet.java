package com.basejava.servlet;

import com.basejava.Config;
import com.basejava.model.Resume;
import com.basejava.storage.Storage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private Storage storage = Config.getInstance().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        List<Resume> resumes = storage.getAllSorted();
        
        PrintWriter out = response.getWriter();
        
        out.println(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<title>Курс JavaSE + Web.</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border='1' style='border-collapse: collapse; border-color: black;'>");
        
        for (Resume resume : resumes) {
            out.println("<tr>");
            out.println("<td>" + resume.getUuid() + "</td>");
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }
                
        out.println(
                "</table>\n" +
                "</body>\n" +
                "</html>");
        
        out.close();
    }
}