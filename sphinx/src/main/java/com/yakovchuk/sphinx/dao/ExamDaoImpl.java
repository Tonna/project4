package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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
    public static final String SELECT_EXAMS_WITHOUT_QUESTIONS = "SELECT " + EXAM_ID + COMMA + EXAM_NAME + COMMA + SUBJECT_NAME + " FROM EXAM JOIN SUBJECT ON SUBJECT.ID = EXAM.SUBJECT_ID";
    public static final String GET_ALL_EXAMS = "SELECT" + SPACE +
            EXAM_ID + COMMA + EXAM_NAME + COMMA + SUBJECT_NAME + COMMA +
            QUESTION_ID + COMMA + QUESTION_TEXT + COMMA + ANSWER_ID + COMMA +
            ANSWER_TEXT + COMMA + ANSWER_IS_CORRECT + SPACE +
            "FROM EXAM JOIN SUBJECT ON EXAM.SUBJECT_ID = SUBJECT.ID JOIN QUESTION ON EXAM.ID = QUESTION.EXAM_ID " +
            "JOIN ANSWER ON QUESTION.ID = ANSWER.QUESTION_ID";
    public static final String GET_EXAM_BY_ID = GET_ALL_EXAMS + " WHERE QUESTION.ID = ?";

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String id) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();

                CallableStatement callableStatement = connection.prepareCall(GET_EXAM_BY_ID);

                callableStatement.setNString(1, id);

                callableStatement.execute();

                ResultSet resultSet = callableStatement.getResultSet();

                boolean resultSetIsNotFinished = resultSet.next();

                Exam.Builder exam = new Exam.Builder().id(resultSet.getString(EXAM_ID))
                        .name(resultSet.getString(EXAM_NAME))
                        .subject(resultSet.getString(SUBJECT_NAME));

                HashMap<String, Question.Builder> questionBuilders = new HashMap<>();
                while (resultSetIsNotFinished) {
                    String questionId = resultSet.getString(QUESTION_ID);
                    Question.Builder qBuilder = questionBuilders.get(questionId);
                    if (qBuilder == null) {
                        String questionText = resultSet.getString(QUESTION_TEXT);
                        qBuilder = new Question.Builder().id(questionId).text(questionText);
                        questionBuilders.put(questionId, qBuilder);
                    }
                    String answerId = resultSet.getString(ANSWER_ID);
                    String answerText = resultSet.getString(ANSWER_TEXT);
                    Boolean answerISCorrect = Integer.parseInt(resultSet.getString(ANSWER_IS_CORRECT)) == 1;
                    qBuilder.addAnswer(new Answer.Builder().id(answerId).text(answerText).isCorrect(answerISCorrect).build());
                    resultSetIsNotFinished = resultSet.next();
                }

                for (Question.Builder builder : questionBuilders.values()) {
                    exam.addQuestion(builder.build());
                }

                return exam.build();


                //Take builders one by one, build and put to exam builder.
                //Build exam.


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Exam.Builder().build();
    }

    /**
     This method currently not supported. TODO Delete?
     */
    @Override
    public Collection<Exam> getAll() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Exam create(Exam toCreate) {
        //create subject if not exist, create exam.
        //have no idea how to implement this
        return null;
    }

    @Override
    public Exam update(Exam toUpdate) {
        return null;
    }

    @Override
    public Exam delete(Exam toDelete) {
        return null;
    }

    @Override
    public Collection<Exam> getAllExamsWithoutQuestions() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
            CallableStatement callableStatement = connection.prepareCall(SELECT_EXAMS_WITHOUT_QUESTIONS);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            ArrayList<Exam> exams = new ArrayList<>();

            boolean hasNext = resultSet.next();
            while (hasNext) {
                Exam exam = new Exam.Builder().id(resultSet.getString(EXAM_ID))
                        .name(resultSet.getString(EXAM_NAME))
                        .subject(resultSet.getString(SUBJECT_NAME)).build();
                exams.add(exam);
                hasNext = resultSet.next();
            }
            return exams;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    //TODO create separate methods\class(?) for handling opening, closing connections, statements and stuff
}
