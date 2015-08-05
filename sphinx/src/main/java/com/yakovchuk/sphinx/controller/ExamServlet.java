package com.yakovchuk.sphinx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.service.ExamService;
import com.yakovchuk.sphinx.util.ExamChecker;
import com.yakovchuk.sphinx.util.ExamSubmissionMapper;

public class ExamServlet extends HttpServlet {

    private ExamService examService;
    private ExamSubmissionMapper examSubmissionMapper;
    private ExamChecker examChecker;

    @Override
    public void init() throws ServletException {
        examService = (ExamService) getServletContext().getAttribute("examService");
        examSubmissionMapper = (ExamSubmissionMapper) getServletContext().getAttribute("examSubmissionMapper");
        examChecker = (ExamChecker) getServletContext().getAttribute("examChecker");
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
            Exam submittedExam = examSubmissionMapper.mapExam(request.getParameterMap());
            Exam originalExam = examService.getExam(submittedExam.getId());
            request.setAttribute("takenExamName", originalExam.getName());
            request.setAttribute("questionsInExam", originalExam.getQuestions().size());
            request.setAttribute("correctlyAnsweredQuestions", examChecker.checkExam(originalExam, submittedExam));
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
