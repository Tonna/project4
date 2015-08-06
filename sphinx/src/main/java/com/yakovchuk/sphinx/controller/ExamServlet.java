package com.yakovchuk.sphinx.controller;

import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.service.ExamService;
import com.yakovchuk.sphinx.util.ExamChecker;
import com.yakovchuk.sphinx.util.ExamMapper;
import com.yakovchuk.sphinx.util.ExamSubmissionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExamServlet extends HttpServlet {

    private ExamService examService;
    private ExamMapper examSubmissionMapper;
    private ExamChecker examChecker;

    private static final Logger logger = LogManager.getLogger(ExamServlet.class);

    @Override
    public void init() throws ServletException {
        logger.info("ExamServlet initiation started");
        examService = (ExamService) getServletContext().getAttribute("examService");
        examSubmissionMapper = (ExamSubmissionMapper) getServletContext().getAttribute("examSubmissionMapper");
        examChecker = (ExamChecker) getServletContext().getAttribute("examChecker");
        logger.info("ExamServlet initiation finished");
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
        } else if (submit.equals(action)) {
            Exam submittedExam = examSubmissionMapper.mapExam(request.getParameterMap());
            Exam originalExam = examService.getExam(submittedExam.getId());
            request.setAttribute("takenExamName", originalExam.getName());
            request.setAttribute("questionsInExam", originalExam.getQuestions().size());
            request.setAttribute("correctlyAnsweredQuestions", examChecker.checkExam(originalExam, submittedExam));
            goToExamsPage(request, response);
        } else { // TODO: Implement wrong action handling.
            goToExamsPage(request, response);
        }
    }

    private void goToExamsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("examsBySubject", examService.getExamsBySubject());
        request.getRequestDispatcher("WEB-INF/view/exams.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
