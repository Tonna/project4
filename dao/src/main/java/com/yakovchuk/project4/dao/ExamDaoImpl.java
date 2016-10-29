package com.yakovchuk.project4.dao;

import com.yakovchuk.project4.domain.Answer;
import com.yakovchuk.project4.domain.Exam;
import com.yakovchuk.project4.domain.Question;
import com.yakovchuk.project4.domain.Subject;
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

    private final DataSource dataSource;

    private String queryInsertAnswer;
    private String queryInsertExam;
    private String queryInsertQuestion;
    private String querySelectExamById;
    private String querySelectExamsWithoutQuestionsByLanguageCode;
    private String columnExamId;
    private String columnQuestionId;
    private String columnAnswerId;
    private String aliasExamId;
    private String aliasExamName;
    private String aliasSubjectName;
    private String aliasQuestionId;
    private String aliasQuestionText;
    private String aliasAnswerId;
    private String aliasAnswerText;
    private String aliasAnswerIsCorrect;

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String examId) {
        Exam.Builder examBuilder = new Exam.Builder();

        try (Connection con = getConnection();
             CallableStatement callableStatement = con.prepareCall(querySelectExamById)) {
            callableStatement.setNString(1, examId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {

                if (resultSet.next()) {
                    examBuilder = examBuilder.id(resultSet.getString(aliasExamId))
                            .name(resultSet.getString(aliasExamName))
                            .subject(new Subject.Builder().name(resultSet.getString(aliasSubjectName)).build());

                    LinkedHashMap<String, Question.Builder> questionBuilders = new LinkedHashMap<>();

                    do {
                        String questionId = resultSet.getString(aliasQuestionId);
                        Question.Builder questionBuilder = questionBuilders.get(questionId);
                        if (questionBuilder == null) {
                            String questionText = resultSet.getString(aliasQuestionText);
                            questionBuilder = new Question.Builder().id(questionId).text(questionText);
                            questionBuilders.put(questionId, questionBuilder);
                        }
                        questionBuilder.addAnswer(
                                new Answer.Builder()
                                        .id(resultSet.getString(aliasAnswerId))
                                        .text(resultSet.getString(aliasAnswerText))
                                        .isCorrect(Integer.parseInt(resultSet.getString(aliasAnswerIsCorrect)) == 1)
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
             PreparedStatement insertExam = con.prepareStatement(queryInsertExam, new String[]{columnExamId});
             PreparedStatement insertQuestion = con.prepareStatement(queryInsertQuestion, new String[]{columnQuestionId});
             PreparedStatement insertAnswer = con.prepareStatement(queryInsertAnswer, new String[]{columnAnswerId})) {
            con.setAutoCommit(false);

            insertExam.setNString(1, toCreate.getSubject().getId());
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
        } catch (SQLIntegrityConstraintViolationException integrityConstraintException) {
            /*
            Exam name is only unique value for exam except id. So in case of
            such exception exam name is appended by time in millis and creation is retried.
            Looks like hack, but no need for more complex solution now.
             */
            logger.error("Exam with name '{}' already exists.", toCreate.getName());
            logger.error("Retry with datetime appended to exam name.");
            String datetime = com.yakovchuk.project4.util.DateUtil.getCurrentDateTime();

            return create(new Exam.Builder(toCreate)
                    .name(toCreate.getName() + " " + datetime)
                    .build());
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
    public Collection<Exam> getAllExamsWithoutQuestions(String languageCode) {
        assert (languageCode != null && !languageCode.isEmpty());
        ArrayList<Exam> exams = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(querySelectExamsWithoutQuestionsByLanguageCode)) {
            preparedStatement.setNString(1, languageCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Exam exam = new Exam.Builder().id(resultSet.getString(aliasExamId))
                            .name(resultSet.getString(aliasExamName))
                            .subject(new Subject.Builder().name(resultSet.getString(aliasSubjectName)).build()).build();
                    exams.add(exam);
                }
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

    public void setAliasAnswerIsCorrect(String aliasAnswerIsCorrect) {
        this.aliasAnswerIsCorrect = aliasAnswerIsCorrect;
    }

    public void setAliasAnswerText(String aliasAnswerText) {
        this.aliasAnswerText = aliasAnswerText;
    }

    public void setAliasAnswerId(String aliasAnswerId) {
        this.aliasAnswerId = aliasAnswerId;
    }

    public void setAliasQuestionText(String aliasQuestionText) {
        this.aliasQuestionText = aliasQuestionText;
    }

    public void setAliasQuestionId(String aliasQuestionId) {
        this.aliasQuestionId = aliasQuestionId;
    }

    public void setQuerySelectExamById(String querySelectExamById) {
        this.querySelectExamById = querySelectExamById;
    }

    public void setQuerySelectExamsWithoutQuestionsByLanguageCode(String querySelectExamsWithoutQuestionsByLanguageCode) {
        this.querySelectExamsWithoutQuestionsByLanguageCode = querySelectExamsWithoutQuestionsByLanguageCode;
    }

    public void setAliasSubjectName(String aliasSubjectName) {
        this.aliasSubjectName = aliasSubjectName;
    }

    public void setAliasExamName(String aliasExamName) {
        this.aliasExamName = aliasExamName;
    }

    public void setAliasExamId(String aliasExamId) {
        this.aliasExamId = aliasExamId;
    }

    public void setColumnAnswerId(String columnAnswerId) {
        this.columnAnswerId = columnAnswerId;
    }

    public void setColumnQuestionId(String columnQuestionId) {
        this.columnQuestionId = columnQuestionId;
    }

    public void setColumnExamId(String columnExamId) {
        this.columnExamId = columnExamId;
    }

    public void setQueryInsertAnswer(String queryInsertAnswer) {
        this.queryInsertAnswer = queryInsertAnswer;
    }

    public void setQueryInsertQuestion(String queryInsertQuestion) {
        this.queryInsertQuestion = queryInsertQuestion;
    }

    public void setQueryInsertExam(String queryInsertExam) {
        this.queryInsertExam = queryInsertExam;
    }

}
