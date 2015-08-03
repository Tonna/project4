package com.yakovchuk.sphynx.initialization;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yakovchuk.sphynx.dao.ExamDao;
import com.yakovchuk.sphynx.dao.ExamDataProvider;
import com.yakovchuk.sphynx.dao.MockExamDao;
import com.yakovchuk.sphynx.service.ExamServiceImpl;
import com.yakovchuk.sphynx.util.ExamHelper;

public class InitializationServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        ExamDao examDao = new MockExamDao(ExamDataProvider.getExam1(), ExamDataProvider.getExam2());
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.setExamDao(examDao);
        context.setAttribute("examService", examService);

        context.setAttribute("examHelper", new ExamHelper());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
