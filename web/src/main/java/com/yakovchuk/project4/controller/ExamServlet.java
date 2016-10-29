package com.yakovchuk.project4.controller;

import com.yakovchuk.project4.domain.Exam;
import com.yakovchuk.project4.domain.Subject;
import com.yakovchuk.project4.profile.Profile;
import com.yakovchuk.project4.service.ExamService;
import com.yakovchuk.project4.service.SubjectService;
import com.yakovchuk.project4.util.ExamChecker;
import com.yakovchuk.project4.util.ExamCreationMapper;
import com.yakovchuk.project4.util.ExamMapper;
import com.yakovchuk.project4.util.ExamSubmissionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class ExamServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ExamServlet.class);
    private ExamService examService;
    private SubjectService subjectService;
    private ExamMapper examSubmissionMapper;
    private ExamMapper examCreationMapper;
    private ExamChecker examChecker;

    @Override
    public void init() throws ServletException {
        logger.info("ExamServlet initiation started");

        ServletContext servletContext = getServletContext();
        examService = (ExamService) servletContext.getAttribute("examService");
        subjectService = (SubjectService) servletContext.getAttribute("subjectService");
        examSubmissionMapper = (ExamSubmissionMapper) servletContext.getAttribute("examSubmissionMapper");
        examCreationMapper = (ExamCreationMapper) servletContext.getAttribute("examCreationMapper");
        examChecker = (ExamChecker) servletContext.getAttribute("examChecker");

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
        String profileLanguage = ((Profile) request.getSession().getAttribute("profile")).getLanguageCode();
        if (creationForm.equals(action)) {
            request.setAttribute("subjects", subjectService.getAll(profileLanguage));
            goToPage(request, response, "WEB-INF/view/createExam.jsp");
        } else if (take.equals(action)) {
            request.setAttribute("exam", examService.getExam(request.getParameter("id")));
            goToPage(request, response, "WEB-INF/view/takeExam.jsp");
        } else if (create.equals(action)) {
            Exam createdExam = examCreationMapper.mapExam(request.getParameterMap());
            //TODO move logic from controller
            String subjectName = createdExam.getSubject().getName();
            Subject subject = subjectService.getSubject(subjectName);
            if (subject.getId().isEmpty()) {
                subject = subjectService.createSubject(subjectName, profileLanguage);
            }
            createdExam = new Exam.Builder(createdExam).subject(subject).build();
            examService.createExam(createdExam);
            request.setAttribute("createdExamName", createdExam.getName());
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
        String profileLanguage = ((Profile) request.getSession().getAttribute("profile")).getLanguageCode();
        request.setAttribute("examsBySubject", groupBySubjects(examService.getExamHeaders(profileLanguage)));
        goToPage(request, response, "WEB-INF/view/exams.jsp");
    }

    /*TODO Move this displaying logic to separate jstl? Or just move it to jsp*/
    private TreeMap<String, Collection<Exam>> groupBySubjects(Collection<Exam> examHeaders) {
        TreeMap<String, Collection<Exam>> subjectsNameExamMap = new TreeMap<>();
        for (Exam exam : examHeaders) {
            if (subjectsNameExamMap.containsKey(exam.getSubject().getName())) {
                subjectsNameExamMap.get(exam.getSubject().getName()).add(exam);
            } else {
                Set<Exam> subjEx = new HashSet<>();
                subjEx.add(exam);
                subjectsNameExamMap.put(exam.getSubject().getName(), subjEx);
            }
        }
        return subjectsNameExamMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
