package com.yakovchuk.sphynx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yakovchuk.sphynx.service.ExamService;

public class ExamServlet extends HttpServlet {

    private ExamService examService;

    @Override
    public void init() throws ServletException {
        examService = (ExamService) getServletContext().getAttribute("examService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String create = "create";
        String take = "take";
        String action = request.getParameter("action");
        if (create.equals(action)) {
            request.getRequestDispatcher("WEB-INF/view/createExam.jsp").forward(request, response);
        } else if (take.equals(action)) {
            request.getRequestDispatcher("WEB-INF/view/takeExam.jsp").forward(request, response);
        } else { // TODO: Implement wrong action handling.
            request.setAttribute("exams", examService.getExams());
            request.getRequestDispatcher("WEB-INF/view/exams.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
