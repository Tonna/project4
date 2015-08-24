package com.yakovchuk.sphinx.initialization;

import com.yakovchuk.sphinx.dao.ExamDao;
import com.yakovchuk.sphinx.dao.mock.MockExamDao;
import com.yakovchuk.sphinx.service.ExamServiceImpl;
import com.yakovchuk.sphinx.util.ExamCheckerImp;
import com.yakovchuk.sphinx.util.ExamCreationMapper;
import com.yakovchuk.sphinx.util.ExamSubmissionMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.Arrays;

import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.*;

public class InitializationServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        ExamDao examDao = new MockExamDao(getExam1Original(), getExam2Original(), getExam3Original());
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.setExamDao(examDao);
        context.setAttribute("examService", examService);

        context.setAttribute("examSubmissionMapper", new ExamSubmissionMapper());
        context.setAttribute("examCreationMapper", new ExamCreationMapper());

        context.setAttribute("examChecker", new ExamCheckerImp());

        context.setAttribute("examCreationRoles", Arrays.asList("tutor"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
