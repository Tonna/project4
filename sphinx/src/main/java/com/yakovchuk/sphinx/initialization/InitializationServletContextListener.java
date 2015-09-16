package com.yakovchuk.sphinx.initialization;

import com.yakovchuk.sphinx.dao.*;
import com.yakovchuk.sphinx.service.ExamServiceImpl;
import com.yakovchuk.sphinx.service.ProfileService;
import com.yakovchuk.sphinx.service.ProfileServiceImpl;
import com.yakovchuk.sphinx.service.SubjectServiceImpl;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InitializationServletContextListener implements ServletContextListener {

    private static final String QUERY_SELECT_PROFILE_BY_LOGIN_AND_PASSWORD = "query.select.profile.byLoginAndPassword";
    private static final String QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN = "query.select.profileRoles.byProfileLogin";
    private static final String ALIAS_PROFILE_ROLE_OF_PROFILE = "alias.profile.roleOfProfile";
    private static final String ALIAS_LANGUAGE_ID = "alias.language.id";
    private static final String ALIAS_SUBJECT_ID = "alias.subject.id";
    private static final String QUERY_SELECT_LANGUAGE_BY_ID = "query.select.languageById";
    private static final String QUERY_INSERT_SUBJECT = "query.insert.subject";
    private static final String QUERY_INSERT_EXAM = "query.insert.exam";
    private static final String QUERY_INSERT_QUESTION = "query.insert.question";
    private static final String QUERY_INSERT_ANSWER = "query.insert.answer";
    private static final String QUERY_SELECT_SUBJECT_BY_ID = "query.select.subjectById";
    private static final String COLUMN_SUBJECT_ID = "column.subject.id";
    private static final String COLUMN_EXAM_ID = "column.exam.id";
    private static final String COLUMN_QUESTION_ID = "column.question.id";
    private static final String COLUMN_ANSWER_ID = "column.answer.id";
    private static final String ALIAS_EXAM_ID = "alias.exam.id";
    private static final String ALIAS_SUBJECT_NAME = "alias.subject.name";
    private static final String ALIAS_EXAM_NAME = "alias.exam.name";
    private static final String QUERY_SELECT_EXAMS_WITHOUT_QUESTIONS_BY_LANGUAGE_CODE = "query.select.exams.withoutQuestions.byLanguageCode";
    private static final String ALIAS_QUESTION_ID = "alias.question.id";
    private static final String ALIAS_QUESTION_TEXT = "alias.question.text";
    private static final String ALIAS_ANSWER_ID = "alias.answer.id";
    private static final String ALIAS_ANSWER_TEXT = "alias.answer.text";
    private static final String ALIAS_ANSWER_IS_CORRECT = "alias.answer.isCorrect";
    private static final String QUERY_SELECT_EXAM_BY_ID = "query.select.examById";
    private static final String QUERY_SELECT_SUBJECTS_BY_LANGUAGE_CODE = "query.select.subject.byLanguageCode";

    private static final Logger logger = LogManager.getLogger(InitializationServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Starting application initialization");

        ServletContext context = servletContextEvent.getServletContext();

        Properties sqlStrings = new Properties();
        try {
            sqlStrings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(context.getInitParameter("sql_strings")));
        } catch (IOException e) {
            logger.fatal("Unable to load SQL strings from file {}", context.getInitParameter("sql_strings"));
            throw new SphinxInitializationException(e);
        }

        logger.info("SQL strings loaded");

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
        

/*        HashMap sqlStringHolder = new HashMap();
        OR ever object?*/


        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl(dataSource, rolesMapping);
        profileDaoImpl.setQuerySelectProfileByLoginAndPassword(sqlStrings.getProperty(QUERY_SELECT_PROFILE_BY_LOGIN_AND_PASSWORD));
        profileDaoImpl.setQuerySelectProfileRolesByProfileLogin(sqlStrings.getProperty(QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN));
        profileDaoImpl.setAliasProfileRoleOfProfile(sqlStrings.getProperty(ALIAS_PROFILE_ROLE_OF_PROFILE));

        context.setAttribute("profileDao", profileDaoImpl);


        ProfileService profileService = new ProfileServiceImpl(profileDaoImpl);
        context.setAttribute("profileService", profileService);

        SubjectDao subjectDao;
        SubjectDaoImpl subjectDaoImpl = new SubjectDaoImpl(dataSource);
        subjectDaoImpl.setAliasLanguageId(sqlStrings.getProperty(ALIAS_LANGUAGE_ID));
        subjectDaoImpl.setAliasSubjectId(sqlStrings.getProperty(ALIAS_SUBJECT_ID));
        subjectDaoImpl.setAliasSubjectName(sqlStrings.getProperty(ALIAS_SUBJECT_NAME));
        subjectDaoImpl.setQuerySelectLanguageById(sqlStrings.getProperty(QUERY_SELECT_LANGUAGE_BY_ID));
        subjectDaoImpl.setQueryInsertSubject(sqlStrings.getProperty(QUERY_INSERT_SUBJECT));
        subjectDaoImpl.setQuerySelectSubjectById(sqlStrings.getProperty(QUERY_SELECT_SUBJECT_BY_ID));
        subjectDaoImpl.setColumnSubjectId(sqlStrings.getProperty(COLUMN_SUBJECT_ID));
        subjectDaoImpl.setQuerySelectSubjectsByLanguageCode(sqlStrings.getProperty(QUERY_SELECT_SUBJECTS_BY_LANGUAGE_CODE));

        subjectDao = subjectDaoImpl;

        SubjectServiceImpl subjectService = new SubjectServiceImpl();
        subjectService.setSubjectDao(subjectDao);

        context.setAttribute("subjectService", subjectService);

        ExamDao examDao;
        ExamDaoImpl examDaoImpl = new ExamDaoImpl(dataSource);

        examDaoImpl.setQueryInsertExam(sqlStrings.getProperty(QUERY_INSERT_EXAM));
        examDaoImpl.setQueryInsertQuestion(sqlStrings.getProperty(QUERY_INSERT_QUESTION));
        examDaoImpl.setQueryInsertAnswer(sqlStrings.getProperty(QUERY_INSERT_ANSWER));
        examDaoImpl.setColumnExamId(sqlStrings.getProperty(COLUMN_EXAM_ID));
        examDaoImpl.setColumnQuestionId(sqlStrings.getProperty(COLUMN_QUESTION_ID));
        examDaoImpl.setColumnAnswerId(sqlStrings.getProperty(COLUMN_ANSWER_ID));
        examDaoImpl.setAliasExamId(sqlStrings.getProperty(ALIAS_EXAM_ID));
        examDaoImpl.setAliasSubjectName(sqlStrings.getProperty(ALIAS_SUBJECT_NAME));
        examDaoImpl.setAliasExamName(sqlStrings.getProperty(ALIAS_EXAM_NAME));
        examDaoImpl.setQuerySelectExamsWithoutQuestionsByLanguageCode(sqlStrings.getProperty(QUERY_SELECT_EXAMS_WITHOUT_QUESTIONS_BY_LANGUAGE_CODE));
        examDaoImpl.setAliasQuestionId(sqlStrings.getProperty(ALIAS_QUESTION_ID));
        examDaoImpl.setAliasQuestionText(sqlStrings.getProperty(ALIAS_QUESTION_TEXT));
        examDaoImpl.setAliasAnswerId(sqlStrings.getProperty(ALIAS_ANSWER_ID));
        examDaoImpl.setAliasAnswerText(sqlStrings.getProperty(ALIAS_ANSWER_TEXT));
        examDaoImpl.setAliasAnswerIsCorrect(sqlStrings.getProperty(ALIAS_ANSWER_IS_CORRECT));
        examDaoImpl.setQuerySelectExamById(sqlStrings.getProperty(QUERY_SELECT_EXAM_BY_ID));

        examDao = examDaoImpl;
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
