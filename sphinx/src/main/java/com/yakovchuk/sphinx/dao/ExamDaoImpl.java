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

    private final DataSource dataSource;
    private String selectLanguageById;
    private String insertSubject;
    private String insertExam;
    private String insertQuestion;
    private String insertAnswer;
    private String selectSubjectById;
    private String subjectIdColumn;
    private String examIdColumn;
    private String questionIdColumn;
    private String answerIdColumn;
    private String examIdAlias;
    private String examNameAlias;
    private String subjectNameAlias;
    private String selectExamsWithoutQuestions;
    private String getExamById;
    private String languageIdAlias;
    private String subjectIdAlias;
    private String questionIdAlias;
    private String questionTextAlias;
    private String answerIdAlias;
    private String answerTextAlias;
    private String answerIsCorrectAlias;

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String examId) {
        Exam.Builder examBuilder = new Exam.Builder();

        try (Connection con = getConnection();
             CallableStatement callableStatement = con.prepareCall(getExamById)) {
            callableStatement.setNString(1, examId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {

                if (resultSet.next()) {
                    examBuilder = examBuilder.id(resultSet.getString(examIdAlias))
                            .name(resultSet.getString(examNameAlias))
                            .subject(resultSet.getString(subjectNameAlias));

                    LinkedHashMap<String, Question.Builder> questionBuilders = new LinkedHashMap<>();

                    do {
                        String questionId = resultSet.getString(questionIdAlias);
                        Question.Builder questionBuilder = questionBuilders.get(questionId);
                        if (questionBuilder == null) {
                            String questionText = resultSet.getString(questionTextAlias);
                            questionBuilder = new Question.Builder().id(questionId).text(questionText);
                            questionBuilders.put(questionId, questionBuilder);
                        }
                        questionBuilder.addAnswer(
                                new Answer.Builder()
                                        .id(resultSet.getString(answerIdAlias))
                                        .text(resultSet.getString(answerTextAlias))
                                        .isCorrect(Integer.parseInt(resultSet.getString(answerIsCorrectAlias)) == 1)
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
             PreparedStatement selectLanguage = con.prepareStatement(selectLanguageById);
             PreparedStatement selectSubject = con.prepareStatement(selectSubjectById);
             PreparedStatement insertSubject = con.prepareStatement(this.insertSubject, new String[]{subjectIdColumn});
             PreparedStatement insertExam = con.prepareStatement(this.insertExam, new String[]{examIdColumn});
             PreparedStatement insertQuestion = con.prepareStatement(this.insertQuestion, new String[]{questionIdColumn});
             PreparedStatement insertAnswer = con.prepareStatement(this.insertAnswer, new String[]{answerIdColumn})) {

            con.setAutoCommit(false);

            //TODO set real language selection
            String languageCode = "en";
            selectLanguage.setNString(1, languageCode);
            String languageId;
            try (ResultSet languageResultSet = selectLanguage.executeQuery()) {
                if (languageResultSet.next()) {
                    languageId = languageResultSet.getString(languageIdAlias);
                } else {
                    logger.error("Cannot find language with code {}", languageCode);
                    throw new SphinxSQLException("No language with code '" + languageCode + "'");
                }
            }

            selectSubject.setNString(1, toCreate.getSubject());
            String subjectId;
            try (ResultSet selectSubjectRS = selectSubject.executeQuery()) {
                if (selectSubjectRS.next()) {
                    subjectId = selectSubjectRS.getString(subjectIdAlias);
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
             PreparedStatement preparedStatement = con.prepareStatement(selectExamsWithoutQuestions);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Exam exam = new Exam.Builder().id(resultSet.getString(examIdAlias))
                        .name(resultSet.getString(examNameAlias))
                        .subject(resultSet.getString(subjectNameAlias)).build();
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


    public void setAliasAnswerIsCorrect(String answerIsCorrectAlias) {
        this.answerIsCorrectAlias = answerIsCorrectAlias;
    }

    public void setAliasAnswerText(String answerTextAlias) {
        this.answerTextAlias = answerTextAlias;
    }

    public void setAliasAnswerId(String answerIdAlias) {
        this.answerIdAlias = answerIdAlias;
    }

    public void setAliasQuestionText(String questionTextAlias) {
        this.questionTextAlias = questionTextAlias;
    }

    public void setAliasQuestionId(String questionIdAlias) {
        this.questionIdAlias = questionIdAlias;
    }

    public void setAliasSubjectId(String subjectIdAlias) {
        this.subjectIdAlias = subjectIdAlias;
    }

    public void setAliasLanguageId(String languageIdAlias) {
        this.languageIdAlias = languageIdAlias;
    }

    public void setQuerySelectExamById(String getExamById) {
        this.getExamById = getExamById;
    }

    public void setQuerySelectExamsWithoutQuestions(String selectExamsWithoutQuestions) {
        this.selectExamsWithoutQuestions = selectExamsWithoutQuestions;
    }

    public void setAliasSubjectName(String subjectNameAlias) {
        this.subjectNameAlias = subjectNameAlias;
    }

    public void setAliasExamName(String examNameAlias) {
        this.examNameAlias = examNameAlias;
    }

    public void setAliasExamId(String examIdAlias) {
        this.examIdAlias = examIdAlias;
    }

    public void setColumnAnswerId(String answerIdColumn) {
        this.answerIdColumn = answerIdColumn;
    }

    public void setColumnQuestionId(String questionIdColumn) {
        this.questionIdColumn = questionIdColumn;
    }

    public void setColumnExamId(String examIdColumn) {
        this.examIdColumn = examIdColumn;
    }

    public void setColumnSubjectId(String subjectIdColumn) {
        this.subjectIdColumn = subjectIdColumn;
    }

    public void setQuerySelectSubjectById(String selectSubjectById) {
        this.selectSubjectById = selectSubjectById;
    }

    public void setQueryInsertAnswer(String insertAnswer) {
        this.insertAnswer = insertAnswer;
    }

    public void setQueryInsertQuestion(String insertQuestion) {
        this.insertQuestion = insertQuestion;
    }

    public void setQueryInsertExam(String insertExam) {
        this.insertExam = insertExam;
    }

    public void setQueryInsertSubject(String insertSubject) {
        this.insertSubject = insertSubject;
    }

    public void setQuerySelectLanguageById(String selectLanguageById) {
        this.selectLanguageById = selectLanguageById;
    }
}
