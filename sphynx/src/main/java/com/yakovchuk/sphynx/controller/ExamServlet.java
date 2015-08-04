package com.yakovchuk.sphynx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.service.ExamService;
import com.yakovchuk.sphynx.util.ExamHelper;

public class ExamServlet extends HttpServlet {

    private ExamService examService;
    private ExamHelper examHelper;

    @Override
    public void init() throws ServletException {
        examService = (ExamService) getServletContext().getAttribute("examService");
        examHelper = (ExamHelper) getServletContext().getAttribute("examHelper");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String create = "create";
        String take = "take";
        String submit = "submit";
        String action = request.getParameter("action");
        if (create.equals(action)) {
            request.getRequestDispatcher("WEB-INF/view/createExam.jsp").forward(request, response);
        } else if (take.equals(action)) {
            request.setAttribute("exam", examService.getExam(request.getParameter("id")));
            request.getRequestDispatcher("WEB-INF/view/takeExam.jsp").forward(request, response);
        } else if (submit.equals(action)){
            Exam submittedExam = examHelper.createExamFromRequest(request.getParameterMap());
            Exam originalExam = examService.getExam(submittedExam.getId());
            request.setAttribute("takenExamName", originalExam.getName());
            request.setAttribute("questionsInExam", originalExam.getQuestions().size());
            request.setAttribute("correctlyAnsweredQuestions", examHelper.checkExam(originalExam, submittedExam));
            request.setAttribute("examsBySubject", examService.getExamsBySubject());
            request.getRequestDispatcher("WEB-INF/view/exams.jsp").forward(request, response);
        } else { // TODO: Implement wrong action handling.
            request.setAttribute("examsBySubject", examService.getExamsBySubject());
            request.getRequestDispatcher("WEB-INF/view/exams.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
