package com.yakovchuk.sphinx.initialization;

import com.yakovchuk.sphinx.dao.ExamDao;
import com.yakovchuk.sphinx.dao.ExamDaoImpl;
import com.yakovchuk.sphinx.dao.ProfileDao;
import com.yakovchuk.sphinx.dao.ProfileDaoImpl;
import com.yakovchuk.sphinx.service.ExamServiceImpl;
import com.yakovchuk.sphinx.service.ProfileService;
import com.yakovchuk.sphinx.service.ProfileServiceImpl;
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
import java.util.Map;

public class InitializationServletContextListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger(InitializationServletContextListener.class);
    public static final String PROFILE_QUERY_CHECK_CREDENTIALS = "profile.query.checkCredentials";
    public static final String PROFILE_QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN = "profile.query.selectProfileRoles.byProfileLogin";
    public static final String PROFILE_ALIAS_ROLE_OF_PROFILE = "profile.alias.roleOfProfile";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();


        context.setAttribute("examSubmissionMapper", new ExamSubmissionMapper());
        context.setAttribute("examCreationMapper", new ExamCreationMapper());

        context.setAttribute("examChecker", new ExamCheckerImp());

        context.setAttribute("examCreationRoles", Arrays.asList("tutor"));
        context.setAttribute("examTakingRoles", Arrays.asList("student"));

        DataSource dataSource = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/ExamDB");

        } catch (NamingException e) {
            logger.error("Couldn't resolve datasource");
        }

        Map<String, String> rolesMapping = new HashMap<String, String>();
        rolesMapping.put("tutor", "tutor");
        rolesMapping.put("student", "student");

        //TODO retrieving sql strings from file
        HashMap<String, String> sqlStringsMap = new HashMap<>();
        sqlStringsMap.put(PROFILE_QUERY_CHECK_CREDENTIALS,"SELECT LOGIN FROM PROFILE WHERE LOGIN LIKE ? AND PASSWORD LIKE ?");
        sqlStringsMap.put(PROFILE_QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN,"SELECT R.NAME AS ROLE_OF_PROFILE FROM ROLE R JOIN PROFILE_ROLE PR ON PR.ROLE_ID = R.ID JOIN PROFILE P ON P.ID = PR.PROFILE_ID WHERE P.LOGIN LIKE ?");
        sqlStringsMap.put(PROFILE_ALIAS_ROLE_OF_PROFILE,"ROLE_OF_PROFILE");

/*        HashMap sqlStringHolder = new HashMap();
        OR ever object?*/


        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl(dataSource, rolesMapping);
        profileDaoImpl.setSelectProfileQuery(sqlStringsMap.get(PROFILE_QUERY_CHECK_CREDENTIALS));
        profileDaoImpl.setSelectRolesOfProfileQuery(sqlStringsMap.get(PROFILE_QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN));
        profileDaoImpl.setRoleAlias(sqlStringsMap.get(PROFILE_ALIAS_ROLE_OF_PROFILE));
        ProfileDao profileDao = profileDaoImpl;
        context.setAttribute("profileDao", profileDao);



        ProfileService profileService = new ProfileServiceImpl(profileDao);
        context.setAttribute("profileService", profileService);

        ExamDao examDao = new ExamDaoImpl(dataSource);
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.setExamDao(examDao);
        context.setAttribute("examService", examService);

        HashMap<String, String> actionRoleMapping = new HashMap<>();
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
