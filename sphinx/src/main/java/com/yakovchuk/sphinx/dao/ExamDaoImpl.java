package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ExamDaoImpl implements ExamDao {

    private final DataSource dataSource;
    private Connection connection;
    public static final String EXAM_ID = "EXAM.ID";
    public static final String EXAM_NAME = "EXAM.NAME";
    public static final String SUBJECT_NAME = "SUBJECT.NAME";
    public static final String QUESTION_ID = "QUESTION.ID";
    public static final String QUESTION_TEXT = "QUESTION.TEXT";
    public static final String ANSWER_ID = "ANSWER.ID";
    public static final String ANSWER_TEXT = "ANSWER.TEXT";
    public static final String ANSWER_IS_CORRECT = "ANSWER.IS_CORRECT";
    public static final String COMMA = ", ";
    public static final String SPACE = " ";
    public static final String EXAM_ID_ALIAS = "EXAM_ID";
    public static final String EXAM_NAME_ALIAS = "EXAM_NAME";
    public static final String SUBJECT_NAME_ALIAS = "SUBJECT_NAME";
    public static final String SELECT_EXAMS_WITHOUT_QUESTIONS = "SELECT " + "EXAM.ID" + " AS " + EXAM_ID_ALIAS + COMMA +
            "EXAM.NAME" + " AS " + EXAM_NAME_ALIAS + COMMA
            + "SUBJECT.NAME" + " AS " + SUBJECT_NAME_ALIAS + " FROM EXAM JOIN SUBJECT ON SUBJECT.ID = EXAM.SUBJECT_ID";
    private static final String QUESTION_ID_ALIAS = "QUESTION_ID";
    private static final String QUESTION_TEXT_ALIAS = "QUESTION_TEXT";
    private static final String ANSWER_ID_ALIAS = "ANSWER_ID";
    private static final String ANSWER_TEXT_ALIAS = "ANSWER_TEXT";
    private static final String ANSWER_IS_CORRECT_ALIAS = "ANSWER_IS_CORRECT";
    public static final String GET_ALL_EXAMS = "SELECT" + SPACE +
            EXAM_ID + " AS " + EXAM_ID_ALIAS + COMMA + EXAM_NAME + " AS " + EXAM_NAME_ALIAS + COMMA + SUBJECT_NAME + " AS " + SUBJECT_NAME_ALIAS + COMMA +
            QUESTION_ID + " AS " + QUESTION_ID_ALIAS + COMMA + QUESTION_TEXT + " AS " + QUESTION_TEXT_ALIAS + COMMA + ANSWER_ID + " AS " + ANSWER_ID_ALIAS + COMMA +
            ANSWER_TEXT + " AS " + ANSWER_TEXT_ALIAS + COMMA + ANSWER_IS_CORRECT + " AS " + ANSWER_IS_CORRECT_ALIAS + SPACE +
            "FROM EXAM JOIN SUBJECT ON EXAM.SUBJECT_ID = SUBJECT.ID JOIN QUESTION ON EXAM.ID = QUESTION.EXAM_ID " +
            "JOIN ANSWER ON QUESTION.ID = ANSWER.QUESTION_ID";
    public static final String GET_EXAM_BY_ID = GET_ALL_EXAMS + " WHERE EXAM.ID = ?";

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String id) {
        Exam.Builder examBuilder = new Exam.Builder();

        try (Connection con = getConnection();
             CallableStatement callableStatement = con.prepareCall(GET_EXAM_BY_ID)) {

            callableStatement.setNString(1, id);

            try (ResultSet resultSet = callableStatement.executeQuery()) {

                if (resultSet.next()) {

                    examBuilder = examBuilder.id(resultSet.getString(EXAM_ID_ALIAS))
                            .name(resultSet.getString(EXAM_NAME_ALIAS))
                            .subject(resultSet.getString(SUBJECT_NAME_ALIAS));

                    HashMap<String, Question.Builder> questionBuilders = new HashMap<>();

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
            e.printStackTrace();
        }

        return examBuilder.build();
    }

    /**
     * This method currently not supported. TODO Delete?
     */
    @Override
    public Collection<Exam> getAll() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Exam create(Exam toCreate) {
        //create subject if not exist, create exam.
        //have no idea how to implement this

        try {

            PreparedStatement selectLanguage = getConnection().prepareStatement("SELECT ID FROM LANGUAGE WHERE CODE LIKE ?");
            selectLanguage.setNString(1, "en");
            selectLanguage.execute();
            ResultSet languageResultSet = selectLanguage.getResultSet();
            languageResultSet.next();
            String languageId = languageResultSet.getString(1);

            PreparedStatement selectSubject = connection.prepareStatement("SELECT ID FROM SUBJECT WHERE NAME LIKE ?");
            selectSubject.setNString(1, toCreate.getSubject());
            selectSubject.execute();
            ResultSet selectSubjectRS = selectSubject.getResultSet();
            selectSubjectRS.next();
            String subjectId = selectSubjectRS.getString(1);
            if (subjectId == null || subjectId.isEmpty()) {
                PreparedStatement insertSubject = connection.prepareStatement("INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                insertSubject.setNString(1, toCreate.getSubject());
                insertSubject.setNString(2, languageId);
                insertSubject.execute();
                ResultSet generatedKeys = insertSubject.getGeneratedKeys();
                generatedKeys.next();
                subjectId = generatedKeys.getString(1);
            }


            PreparedStatement insertExam = connection.prepareStatement("INSERT INTO EXAM (SUBJECT_ID, NAME) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);

            insertExam.setNString(1, subjectId);
            insertExam.setNString(2, toCreate.getName());

            insertExam.execute();
            ResultSet generatedKeysExam = insertExam.getGeneratedKeys();
            generatedKeysExam.next();
            String examId = generatedKeysExam.getString(1);

            PreparedStatement insertQuestion = connection.prepareStatement("INSERT INTO QUESTION (EXAM_ID, TEXT) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement insertAnswer = connection.prepareStatement("INSERT INTO ANSWER (QUESTION_ID, TEXT, IS_CORRECT) VALUES(?,?,?)");

            for (Question question : toCreate.getQuestions()) {
                insertQuestion.setNString(1, examId);
                insertQuestion.setNString(2, question.getText());
                insertExam.execute();
                ResultSet questionRS = insertExam.getGeneratedKeys();
                questionRS.next();
                String questionId = questionRS.getString(1);

                for (Answer answer : question.getAnswers()) {
                    insertAnswer.setNString(1, questionId);
                    insertAnswer.setNString(2, answer.getText());
                    insertAnswer.setInt(3, answer.getIsCorrect() ? 1 : 0);
                    insertAnswer.execute();
                }


            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
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

        try (Connection thisConnection = getConnection();
             PreparedStatement preparedStatement = thisConnection.prepareStatement(SELECT_EXAMS_WITHOUT_QUESTIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Exam exam = new Exam.Builder().id(resultSet.getString(EXAM_ID_ALIAS))
                        .name(resultSet.getString(EXAM_NAME_ALIAS))
                        .subject(resultSet.getString(SUBJECT_NAME_ALIAS)).build();
                exams.add(exam);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exams;
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    //TODO create separate methods\class(?) for handling opening, closing connections, statements and stuff
}
