package com.yakovchuk.sphinx.controller;

import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.service.ExamService;
import com.yakovchuk.sphinx.util.ExamChecker;
import com.yakovchuk.sphinx.util.ExamCreationMapper;
import com.yakovchuk.sphinx.util.ExamMapper;
import com.yakovchuk.sphinx.util.ExamSubmissionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ExamServlet extends HttpServlet {

    private ExamService examService;
    private ExamMapper examSubmissionMapper;
    private ExamMapper examCreationMapper;
    private ExamChecker examChecker;

    private static final Logger logger = LogManager.getLogger(ExamServlet.class);

    @Override
    public void init() throws ServletException {
        logger.info("ExamServlet initiation started");

        examService = (ExamService) getServletContext().getAttribute("examService");
        examSubmissionMapper = (ExamSubmissionMapper) getServletContext().getAttribute("examSubmissionMapper");
        examCreationMapper = (ExamCreationMapper) getServletContext().getAttribute("examCreationMapper");
        examChecker = (ExamChecker) getServletContext().getAttribute("examChecker");

        logger.info("ExamServlet initiation finished");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String creationForm = "creationForm";
        String create = "create";
        String take = "take";
        String submit = "submit";
        String action = request.getParameter("action");
        if (creationForm.equals(action)) {
            goToPage(request, response, "WEB-INF/view/createExam.jsp");
        } else if (take.equals(action)) {
            request.setAttribute("exam", examService.getExam(request.getParameter("id")));
            goToPage(request, response, "WEB-INF/view/takeExam.jsp");
        } else if (create.equals(action)) {
            Exam createdExam = examCreationMapper.mapExam(request.getParameterMap());
            examService.createExam(createdExam);
            goToExamsPage(request, response);
        } else if (submit.equals(action)) {
            Exam submittedExam = examSubmissionMapper.mapExam(request.getParameterMap());
            Exam originalExam = examService.getExam(submittedExam.getId());
            request.setAttribute("takenExamName", originalExam.getName());
            request.setAttribute("questionsInExam", originalExam.getQuestions().size());
            request.setAttribute("correctlyAnsweredQuestions", examChecker.checkExam(originalExam, submittedExam));
            goToExamsPage(request, response);
        } else { // TODO: Implement wrong action handling. Going to exams view page
            goToExamsPage(request, response);
        }
    }

    private void goToPage(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    private void goToExamsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("examsBySubject", groupBySubjects(examService.getExamHeaders()));
        goToPage(request, response, "WEB-INF/view/exams.jsp");
    }

    private TreeMap<String, Collection<Exam>> groupBySubjects(Collection<Exam> examHeaders) {
        TreeMap<String, Collection<Exam>> subjectExamMap = new TreeMap<>();
        for (Exam exam : examHeaders) {
            if (subjectExamMap.containsKey(exam.getSubject())) {
                subjectExamMap.get(exam.getSubject()).add(exam);
            } else {
                Set<Exam> subjEx = new HashSet<>();
                subjEx.add(exam);
                subjectExamMap.put(exam.getSubject(), subjEx);
            }
        }
        return subjectExamMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
