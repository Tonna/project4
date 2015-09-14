package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;
import com.yakovchuk.sphinx.domain.Subject;
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
    private String querySelectLanguageById;
    private String queryInsertAnswer;
    private String queryInsertExam;
    private String queryInsertQuestion;
    private String queryInsertSubject;
    private String querySelectExamById;
    private String querySelectExamsWithoutQuestions;
    private String querySelectSubjectById;
    private String columnSubjectId;
    private String columnExamId;
    private String columnQuestionId;
    private String columnAnswerId;
    private String aliasExamId;
    private String aliasExamName;
    private String aliasSubjectName;
    private String aliasLanguageId;
    private String aliasSubjectId;
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
             PreparedStatement selectLanguage = con.prepareStatement(querySelectLanguageById);
             PreparedStatement selectSubject = con.prepareStatement(querySelectSubjectById);
             PreparedStatement insertSubject = con.prepareStatement(queryInsertSubject, new String[]{columnSubjectId});
             PreparedStatement insertExam = con.prepareStatement(queryInsertExam, new String[]{columnExamId});
             PreparedStatement insertQuestion = con.prepareStatement(queryInsertQuestion, new String[]{columnQuestionId});
             PreparedStatement insertAnswer = con.prepareStatement(queryInsertAnswer, new String[]{columnAnswerId})) {

            con.setAutoCommit(false);

            //TODO set real language selection
            String languageCode = "en";
            selectLanguage.setNString(1, languageCode);
            String languageId;
            try (ResultSet languageResultSet = selectLanguage.executeQuery()) {
                if (languageResultSet.next()) {
                    languageId = languageResultSet.getString(aliasLanguageId);
                } else {
                    logger.error("Cannot find language with code {}", languageCode);
                    throw new SphinxSQLException("No language with code '" + languageCode + "'");
                }
            }

            selectSubject.setNString(1, toCreate.getSubject().getName());
            String subjectId;
            try (ResultSet selectSubjectRS = selectSubject.executeQuery()) {
                if (selectSubjectRS.next()) {
                    subjectId = selectSubjectRS.getString(aliasSubjectId);
                } else {
                    insertSubject.setNString(1, toCreate.getSubject().getName());
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
             PreparedStatement preparedStatement = con.prepareStatement(querySelectExamsWithoutQuestions);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Exam exam = new Exam.Builder().id(resultSet.getString(aliasExamId))
                        .name(resultSet.getString(aliasExamName))
                        .subject(new Subject.Builder().name(resultSet.getString(aliasSubjectName)).build()).build();
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

    public void setAliasSubjectId(String aliasSubjectId) {
        this.aliasSubjectId = aliasSubjectId;
    }

    public void setAliasLanguageId(String aliasLanguageId) {
        this.aliasLanguageId = aliasLanguageId;
    }

    public void setQuerySelectExamById(String querySelectExamById) {
        this.querySelectExamById = querySelectExamById;
    }

    public void setQuerySelectExamsWithoutQuestions(String querySelectExamsWithoutQuestions) {
        this.querySelectExamsWithoutQuestions = querySelectExamsWithoutQuestions;
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

    public void setColumnSubjectId(String columnSubjectId) {
        this.columnSubjectId = columnSubjectId;
    }

    public void setQuerySelectSubjectById(String querySelectSubjectById) {
        this.querySelectSubjectById = querySelectSubjectById;
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

    public void setQueryInsertSubject(String queryInsertSubject) {
        this.queryInsertSubject = queryInsertSubject;
    }

    public void setQuerySelectLanguageById(String querySelectLanguageById) {
        this.querySelectLanguageById = querySelectLanguageById;
    }
}
