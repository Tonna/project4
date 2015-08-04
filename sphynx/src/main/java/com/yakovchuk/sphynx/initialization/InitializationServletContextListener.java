package com.yakovchuk.sphynx.initialization;

import com.yakovchuk.sphynx.dao.ExamDao;
import com.yakovchuk.sphynx.dao.MockExamDao;
import com.yakovchuk.sphynx.service.ExamServiceImpl;
import com.yakovchuk.sphynx.util.ExamCheckerImp;
import com.yakovchuk.sphynx.util.ExamSubmissionMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.yakovchuk.sphynx.dao.ExamDataProvider.*;

public class InitializationServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        ExamDao examDao = new MockExamDao(getExam1Original(), getExam2Original(), getExam3Original());
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.setExamDao(examDao);
        context.setAttribute("examService", examService);

        context.setAttribute("examSubmissionMapper", new ExamSubmissionMapper());

        context.setAttribute("examChecker", new ExamCheckerImp());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
