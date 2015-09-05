package com.yakovchuk.sphinx.initialization;

import com.yakovchuk.sphinx.dao.ExamDao;
import com.yakovchuk.sphinx.dao.ExamDaoImpl;
import com.yakovchuk.sphinx.dao.UserDao;
import com.yakovchuk.sphinx.dao.UserDaoImpl;
import com.yakovchuk.sphinx.dao.mock.ExamDataProvider;
import com.yakovchuk.sphinx.dao.mock.MockExamDao;
import com.yakovchuk.sphinx.service.ExamServiceImpl;
import com.yakovchuk.sphinx.service.UserService;
import com.yakovchuk.sphinx.service.UserServiceImpl;
import com.yakovchuk.sphinx.util.ExamCheckerImp;
import com.yakovchuk.sphinx.util.ExamCreationMapper;
import com.yakovchuk.sphinx.util.ExamSubmissionMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.getExam2Original;
import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.getExam3Original;

public class InitializationServletContextListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger(InitializationServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();


        context.setAttribute("examSubmissionMapper", new ExamSubmissionMapper());
        context.setAttribute("examCreationMapper", new ExamCreationMapper());

        context.setAttribute("examChecker", new ExamCheckerImp());

        context.setAttribute("examCreationRoles", Arrays.asList("tutor"));
        context.setAttribute("examTakingRoles", Arrays.asList("student"));

        DataSource dataSource=  null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/ExamDB");

        } catch (NamingException e) {
            logger.error("Couldn't resolve datasource");
        }

        UserDao userDao = new UserDaoImpl(dataSource);
        context.setAttribute("userDao", userDao);

        UserService userService = new UserServiceImpl(userDao);
        context.setAttribute("userService", userService);

        ExamDao examDao = new ExamDaoImpl(dataSource);
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.setExamDao(examDao);
        context.setAttribute("examService", examService);

        HashMap actionRoleMapping = new HashMap<>();
        actionRoleMapping.put("creationForm", "tutor");
        actionRoleMapping.put("create", "tutor");
        actionRoleMapping.put("take", "student");
        actionRoleMapping.put("submit", "student");

        context.setAttribute("actionRoleMapping", actionRoleMapping);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
