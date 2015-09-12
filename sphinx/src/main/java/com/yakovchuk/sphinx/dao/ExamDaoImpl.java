package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

public class ExamDaoImpl implements ExamDao {

    private final static Logger logger = LogManager.getLogger(ExamDaoImpl.class);

    private static final String LANGUAGE_ID_ALIAS = "LANGUAGE_ID";
    private static final String SUBJECT_ID_ALIAS = "SUBJECT_ID";
    private final DataSource dataSource;
    public static final String SELECT_LANGUAGE_BY_ID = "SELECT ID AS LANGUAGE_ID FROM LANGUAGE WHERE CODE LIKE ?";
    public static final String INSERT_SUBJECT = "INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES(?,?)";
    public static final String INSERT_EXAM = "INSERT INTO EXAM (SUBJECT_ID, NAME) VALUES(?,?)";
    public static final String INSERT_QUESTION = "INSERT INTO QUESTION (EXAM_ID, TEXT) VALUES(?,?)";
    public static final String INSERT_ANSWER = "INSERT INTO ANSWER (QUESTION_ID, TEXT, IS_CORRECT) VALUES(?,?,?)";
    public static final String SELECT_SUBJECT_BY_ID = "SELECT ID AS SUBJECT_ID FROM SUBJECT WHERE NAME LIKE ?";
    public static final String SUBJECT_ID_COLUMN = "ID";
    public static final String EXAM_ID_COLUMN = "ID";
    public static final String QUESTION_ID_COLUMN = "ID";
    public static final String ANSWER_ID_COLUMN = "ID";
    public static final String EXAM_ID_ALIAS = "EXAM_ID";
    public static final String EXAM_NAME_ALIAS = "EXAM_NAME";
    public static final String SUBJECT_NAME_ALIAS = "SUBJECT_NAME";
    public static final String SELECT_EXAMS_WITHOUT_QUESTIONS = "SELECT EXAM.ID AS EXAM_ID, EXAM.NAME AS EXAM_NAME, SUBJECT.NAME AS SUBJECT_NAME FROM EXAM JOIN SUBJECT ON SUBJECT.ID = EXAM.SUBJECT_ID";
    private static final String QUESTION_ID_ALIAS = "QUESTION_ID";
    private static final String QUESTION_TEXT_ALIAS = "QUESTION_TEXT";
    private static final String ANSWER_ID_ALIAS = "ANSWER_ID";
    private static final String ANSWER_TEXT_ALIAS = "ANSWER_TEXT";
    private static final String ANSWER_IS_CORRECT_ALIAS = "ANSWER_IS_CORRECT";
    public static final String GET_EXAM_BY_ID = "SELECT EXAM.ID AS EXAM_ID, EXAM.NAME AS EXAM_NAME, SUBJECT.NAME AS SUBJECT_NAME, QUESTION.ID AS QUESTION_ID, QUESTION.TEXT AS QUESTION_TEXT, ANSWER.ID AS ANSWER_ID, ANSWER.TEXT AS ANSWER_TEXT, ANSWER.IS_CORRECT AS ANSWER_IS_CORRECT FROM EXAM JOIN SUBJECT ON EXAM.SUBJECT_ID = SUBJECT.ID JOIN QUESTION ON EXAM.ID = QUESTION.EXAM_ID JOIN ANSWER ON QUESTION.ID = ANSWER.QUESTION_ID WHERE EXAM.ID = ?";

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String examId) {
        Exam.Builder examBuilder = new Exam.Builder();

        try (Connection con = getConnection();
             CallableStatement callableStatement = con.prepareCall(GET_EXAM_BY_ID)) {
            callableStatement.setNString(1, examId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {

                if (resultSet.next()) {
                    examBuilder = examBuilder.id(resultSet.getString(EXAM_ID_ALIAS))
                            .name(resultSet.getString(EXAM_NAME_ALIAS))
                            .subject(resultSet.getString(SUBJECT_NAME_ALIAS));

                    LinkedHashMap<String, Question.Builder> questionBuilders = new LinkedHashMap<>();

                    do {
                        String questionId = resultSet.getString(QUESTION_ID_ALIAS);
                        Question.Builder questionBuilder = questionBuilders.get(questionId);
                        if (questionBuilder == null) {
                            String questionText = resultSet.getString(QUESTION_TEXT_ALIAS);
                            questionBuilder = new Question.Builder().id(questionId).text(questionText);
                            questionBuilders.put(questionId, questionBuilder);
                        }
                        questionBuilder.addAnswer(
                                new Answer.Builder()
                                        .id(resultSet.getString(ANSWER_ID_ALIAS))
                                        .text(resultSet.getString(ANSWER_TEXT_ALIAS))
                                        .isCorrect(Integer.parseInt(resultSet.getString(ANSWER_IS_CORRECT_ALIAS)) == 1)
                                        .build());
                    } while (resultSet.next());

                    for (Question.Builder builder : questionBuilders.values()) {
                        examBuilder.addQuestion(builder.build());
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve exam with examId {}", examId);
            throw new SphinxSQLException(e);
        }
        return examBuilder.build();
    }

    /**
     * This method currently not supported.
     * TODO Delete?
     */
    @Override
    public Collection<Exam> getAll() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Exam create(Exam toCreate) {

        try (Connection con = getConnection();
             PreparedStatement selectLanguage = con.prepareStatement(SELECT_LANGUAGE_BY_ID);
             PreparedStatement selectSubject = con.prepareStatement(SELECT_SUBJECT_BY_ID);
             PreparedStatement insertSubject = con.prepareStatement(INSERT_SUBJECT, new String[]{SUBJECT_ID_COLUMN});
             PreparedStatement insertExam = con.prepareStatement(INSERT_EXAM, new String[]{EXAM_ID_COLUMN});
             PreparedStatement insertQuestion = con.prepareStatement(INSERT_QUESTION, new String[]{QUESTION_ID_COLUMN});
             PreparedStatement insertAnswer = con.prepareStatement(INSERT_ANSWER, new String[]{ANSWER_ID_COLUMN})) {

            con.setAutoCommit(false);

            //TODO set real language selection
            String languageCode = "en";
            selectLanguage.setNString(1, languageCode);
            String languageId;
            try (ResultSet languageResultSet = selectLanguage.executeQuery()) {
                if (languageResultSet.next()) {
                    languageId = languageResultSet.getString(LANGUAGE_ID_ALIAS);
                } else {
                    logger.error("Cannot find language with code {}", languageCode);
                    throw new SphinxSQLException("No language with code '" + languageCode + "'");
                }
            }

            selectSubject.setNString(1, toCreate.getSubject());
            String subjectId;
            try (ResultSet selectSubjectRS = selectSubject.executeQuery()) {
                if (selectSubjectRS.next()) {
                    subjectId = selectSubjectRS.getString(SUBJECT_ID_ALIAS);
                } else {
                    insertSubject.setNString(1, toCreate.getSubject());
                    insertSubject.setNString(2, languageId);
                    insertSubject.execute();
                    try (ResultSet generatedKeys = insertSubject.getGeneratedKeys()) {
                        generatedKeys.next();
                        subjectId = generatedKeys.getString(1);
                    }
                }
            }

            insertExam.setNString(1, subjectId);
            insertExam.setNString(2, toCreate.getName());
            insertExam.execute();

            String examId;
            try (ResultSet generatedKeysExam = insertExam.getGeneratedKeys();) {
                generatedKeysExam.next();
                examId = generatedKeysExam.getString(1);
            }

            for (Question question : toCreate.getQuestions()) {
                insertQuestion.setNString(1, examId);
                insertQuestion.setNString(2, question.getText());
                insertQuestion.execute();
                String questionId;
                try (ResultSet questionRS = insertQuestion.getGeneratedKeys()) {
                    questionRS.next();
                    questionId = questionRS.getString(1);
                }

                for (Answer answer : question.getAnswers()) {
                    insertAnswer.setNString(1, questionId);
                    insertAnswer.setNString(2, answer.getText());
                    insertAnswer.setInt(3, answer.getIsCorrect() ? 1 : 0);
                    insertAnswer.execute();
                }
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Failed to create exam {}", toCreate);
            throw new SphinxSQLException(e);
        }
        return toCreate;
    }

    @Override
    public Exam update(Exam toUpdate) {
        return new Exam.Builder().build();
    }

    @Override
    public Exam delete(Exam toDelete) {
        return new Exam.Builder().build();
    }

    @Override
    public Collection<Exam> getAllExamsWithoutQuestions() {
        ArrayList<Exam> exams = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SELECT_EXAMS_WITHOUT_QUESTIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Exam exam = new Exam.Builder().id(resultSet.getString(EXAM_ID_ALIAS))
                        .name(resultSet.getString(EXAM_NAME_ALIAS))
                        .subject(resultSet.getString(SUBJECT_NAME_ALIAS)).build();
                exams.add(exam);
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve exams without questions");
            throw new SphinxSQLException(e);
        }
        return exams;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //TODO create separate methods\class(?) for handling opening, closing connections, statements and stuff
}
