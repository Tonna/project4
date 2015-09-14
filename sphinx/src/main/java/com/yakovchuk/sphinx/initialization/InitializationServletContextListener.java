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

    public static final String QUERY_SELECT_PROFILE_BY_LOGIN_AND_PASSWORD = "query.select.profile.byLoginAndPassword";
    public static final String QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN = "query.select.profileRoles.byProfileLogin";
    public static final String ALIAS_PROFILE_ROLE_OF_PROFILE = "alias.profile.roleOfProfile";

    public static final String ALIAS_LANGUAGE_ID = "alias.language.id";
    public static final String ALIAS_SUBJECT_ID = "alias.subject.id";
    public static final String QUERY_SELECT_LANGUAGE_BY_ID = "query.select.languageById";
    public static final String QUERY_INSERT_SUBJECT = "query.insert.subject";
    public static final String QUERY_INSERT_EXAM = "query.insert.exam";
    public static final String QUERY_INSERT_QUESTION = "query.insert.question";
    public static final String QUERY_INSERT_ANSWER = "query.insert.answer";
    public static final String QUERY_SELECT_SUBJECT_BY_ID = "query.select.subjectById";
    public static final String COLUMN_SUBJECT_ID = "column.subject.id";
    public static final String COLUMN_EXAM_ID = "column.exam.id";
    public static final String COLUMN_QUESTION_ID = "column.question.id";
    public static final String COLUMN_ANSWER_ID = "column.answer.id";
    public static final String ALIAS_EXAM_ID = "alias.exam.id";
    public static final String ALIAS_SUBJECT_NAME = "alias.subject.name";
    public static final String ALIAS_EXAM_NAME = "alias.exam.name";
    public static final String QUERY_SELECT_EXAMS_WITHOUT_QUESTIONS = "query.select.examsWithoutQuestions";
    public static final String ALIAS_QUESTION_ID = "alias.question.id";
    public static final String ALIAS_QUESTION_TEXT = "alias.question.text";
    public static final String ALIAS_ANSWER_ID = "alias.answer.id";
    public static final String ALIAS_ANSWER_TEXT = "alias.answer.text";
    public static final String ALIAS_ANSWER_IS_CORRECT = "alias.answer.isCorrect";
    public static final String QUERY_SELECT_EXAM_BY_ID = "query.select.examById";
    private final static Logger logger = LogManager.getLogger(InitializationServletContextListener.class);

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
        sqlStringsMap.put(QUERY_SELECT_PROFILE_BY_LOGIN_AND_PASSWORD, "SELECT LOGIN FROM PROFILE WHERE LOGIN LIKE ? AND PASSWORD LIKE ?");
        sqlStringsMap.put(QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN, "SELECT R.NAME AS ROLE_OF_PROFILE FROM ROLE R JOIN PROFILE_ROLE PR ON PR.ROLE_ID = R.ID JOIN PROFILE P ON P.ID = PR.PROFILE_ID WHERE P.LOGIN LIKE ?");
        sqlStringsMap.put(ALIAS_PROFILE_ROLE_OF_PROFILE, "ROLE_OF_PROFILE");


        sqlStringsMap.put(ALIAS_LANGUAGE_ID, "LANGUAGE_ID");
        sqlStringsMap.put(ALIAS_SUBJECT_ID, "SUBJECT_ID");
        sqlStringsMap.put(QUERY_SELECT_LANGUAGE_BY_ID, "SELECT ID AS LANGUAGE_ID FROM LANGUAGE WHERE CODE LIKE ?");
        sqlStringsMap.put(QUERY_INSERT_SUBJECT, "INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES(?,?)");
        sqlStringsMap.put(QUERY_INSERT_EXAM, "INSERT INTO EXAM (SUBJECT_ID, NAME) VALUES(?,?)");
        sqlStringsMap.put(QUERY_INSERT_QUESTION, "INSERT INTO QUESTION (EXAM_ID, TEXT) VALUES(?,?)");
        sqlStringsMap.put(QUERY_INSERT_ANSWER, "INSERT INTO ANSWER (QUESTION_ID, TEXT, IS_CORRECT) VALUES(?,?,?)");
        sqlStringsMap.put(QUERY_SELECT_SUBJECT_BY_ID, "SELECT ID AS SUBJECT_ID FROM SUBJECT WHERE NAME LIKE ?");
        sqlStringsMap.put(COLUMN_SUBJECT_ID, "ID");
        sqlStringsMap.put(COLUMN_EXAM_ID, "ID");
        sqlStringsMap.put(COLUMN_QUESTION_ID, "ID");
        sqlStringsMap.put(COLUMN_ANSWER_ID, "ID");
        sqlStringsMap.put(ALIAS_EXAM_ID, "EXAM_ID");
        sqlStringsMap.put(ALIAS_SUBJECT_NAME, "SUBJECT_NAME");
        sqlStringsMap.put(ALIAS_EXAM_NAME, "EXAM_NAME");
        sqlStringsMap.put(QUERY_SELECT_EXAMS_WITHOUT_QUESTIONS, "SELECT EXAM.ID AS EXAM_ID, EXAM.NAME AS EXAM_NAME, SUBJECT.NAME AS SUBJECT_NAME FROM EXAM JOIN SUBJECT ON SUBJECT.ID = EXAM.SUBJECT_ID");
        sqlStringsMap.put(ALIAS_QUESTION_ID, "QUESTION_ID");
        sqlStringsMap.put(ALIAS_QUESTION_TEXT, "QUESTION_TEXT");
        sqlStringsMap.put(ALIAS_ANSWER_ID, "ANSWER_ID");
        sqlStringsMap.put(ALIAS_ANSWER_TEXT, "ANSWER_TEXT");
        sqlStringsMap.put(ALIAS_ANSWER_IS_CORRECT, "ANSWER_IS_CORRECT");
        sqlStringsMap.put(QUERY_SELECT_EXAM_BY_ID, "SELECT EXAM.ID AS EXAM_ID, EXAM.NAME AS EXAM_NAME, SUBJECT.NAME AS SUBJECT_NAME, QUESTION.ID AS QUESTION_ID, QUESTION.TEXT AS QUESTION_TEXT, ANSWER.ID AS ANSWER_ID, ANSWER.TEXT AS ANSWER_TEXT, ANSWER.IS_CORRECT AS ANSWER_IS_CORRECT FROM EXAM JOIN SUBJECT ON EXAM.SUBJECT_ID = SUBJECT.ID JOIN QUESTION ON EXAM.ID = QUESTION.EXAM_ID JOIN ANSWER ON QUESTION.ID = ANSWER.QUESTION_ID WHERE EXAM.ID = ?");


/*        HashMap sqlStringHolder = new HashMap();
        OR ever object?*/


        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl(dataSource, rolesMapping);
        profileDaoImpl.setQuerySelectProfileByLoginAndPassword(sqlStringsMap.get(QUERY_SELECT_PROFILE_BY_LOGIN_AND_PASSWORD));
        profileDaoImpl.setQuerySelectProfileRolesByProfileLogin(sqlStringsMap.get(QUERY_SELECT_PROFILE_ROLES_BY_PROFILE_LOGIN));
        profileDaoImpl.setAliasProfileRoleOfProfile(sqlStringsMap.get(ALIAS_PROFILE_ROLE_OF_PROFILE));

        ProfileDao profileDao = profileDaoImpl;
        context.setAttribute("profileDao", profileDao);


        ProfileService profileService = new ProfileServiceImpl(profileDao);
        context.setAttribute("profileService", profileService);

        ExamDao examDao;
        ExamDaoImpl examDaoImpl = new ExamDaoImpl(dataSource);

        examDaoImpl.setAliasLanguageId(sqlStringsMap.get(ALIAS_LANGUAGE_ID));
        examDaoImpl.setAliasSubjectId(sqlStringsMap.get(ALIAS_SUBJECT_ID));
        examDaoImpl.setQuerySelectLanguageById(sqlStringsMap.get(QUERY_SELECT_LANGUAGE_BY_ID));
        examDaoImpl.setQueryInsertSubject(sqlStringsMap.get(QUERY_INSERT_SUBJECT));
        examDaoImpl.setQueryInsertExam(sqlStringsMap.get(QUERY_INSERT_EXAM));
        examDaoImpl.setQueryInsertQuestion(sqlStringsMap.get(QUERY_INSERT_QUESTION));
        examDaoImpl.setQueryInsertAnswer(sqlStringsMap.get(QUERY_INSERT_ANSWER));
        examDaoImpl.setQuerySelectSubjectById(sqlStringsMap.get(QUERY_SELECT_SUBJECT_BY_ID));
        examDaoImpl.setColumnSubjectId(sqlStringsMap.get(COLUMN_SUBJECT_ID));
        examDaoImpl.setColumnExamId(sqlStringsMap.get(COLUMN_EXAM_ID));
        examDaoImpl.setColumnQuestionId(sqlStringsMap.get(COLUMN_QUESTION_ID));
        examDaoImpl.setColumnAnswerId(sqlStringsMap.get(COLUMN_ANSWER_ID));
        examDaoImpl.setAliasExamId(sqlStringsMap.get(ALIAS_EXAM_ID));
        examDaoImpl.setAliasSubjectName(sqlStringsMap.get(ALIAS_SUBJECT_NAME));
        examDaoImpl.setAliasExamName(sqlStringsMap.get(ALIAS_EXAM_NAME));
        examDaoImpl.setQuerySelectExamsWithoutQuestions(sqlStringsMap.get(QUERY_SELECT_EXAMS_WITHOUT_QUESTIONS));
        examDaoImpl.setAliasQuestionId(sqlStringsMap.get(ALIAS_QUESTION_ID));
        examDaoImpl.setAliasQuestionText(sqlStringsMap.get(ALIAS_QUESTION_TEXT));
        examDaoImpl.setAliasAnswerId(sqlStringsMap.get(ALIAS_ANSWER_ID));
        examDaoImpl.setAliasAnswerText(sqlStringsMap.get(ALIAS_ANSWER_TEXT));
        examDaoImpl.setAliasAnswerIsCorrect(sqlStringsMap.get(ALIAS_ANSWER_IS_CORRECT));
        examDaoImpl.setQuerySelectExamById(sqlStringsMap.get(QUERY_SELECT_EXAM_BY_ID));

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
