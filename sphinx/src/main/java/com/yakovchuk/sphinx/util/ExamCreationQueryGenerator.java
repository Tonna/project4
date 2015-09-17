package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.dao.mock.ExamDataProvider;
import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

import java.util.Collection;

public class ExamCreationQueryGenerator {

    private static String subjectTableName = "SUBJECT";
    private static String examTableName = "EXAM";
    private static String questionTableName = "QUESTION";
    private static String answerTableName = "ANSWER";
    private static String languageTableName = "Language";

    public static void main(String[] args) {

        String english = "English";

        generateLanguage(english, "en");
        generateLanguage("Russian", "ru");

        String languageId = "SELECT ID FROM " + languageTableName + " WHERE language.name LIKE '" + english + "'";
        generateExam(ExamDataProvider.getExam3OriginalWithoutIds(), languageId);
        generateExam(ExamDataProvider.getExam1Original(), languageId);
        generateExam(ExamDataProvider.getExam2Original(), languageId);
        generateExam(ExamDataProvider.getExam4Original(), languageId);
        generateExam(ExamDataProvider.getExam5Original(), languageId);
    }

    private static void generateLanguage(String language, String code) {
        String createLanguage = "INSERT INTO " + languageTableName + " (NAME, CODE) VALUES('" + language + "','" + code + "')";
        System.out.println(createLanguage + ";");
    }

    private static void generateExam(Exam exam, String languageId) {
        String subjectName = exam.getSubject().getName();
        String examName = exam.getName();
        String createSubject = "INSERT INTO " + subjectTableName + " (NAME, LANGUAGE_ID) VALUES('" + subjectName + "', (" + languageId + "))";
        System.out.println(createSubject + ";");

        String subjectId = "SELECT ID FROM " + subjectTableName + " WHERE subject.NAME LIKE '" + subjectName + "'";


        String insertExam = "INSERT INTO " + examTableName + " (NAME, SUBJECT_ID) VALUES('" + examName + "', (" + subjectId + "))";
        System.out.println(insertExam + ";");
        String examId = "SELECT ID from " + examTableName + " WHERE exam.name LIKE '" + examName + "'";

        generateQuestions(examId, exam.getQuestions());
    }

    private static void generateQuestions(String examId, Collection<Question> questions) {
        for (Question question : questions) {

            String questionText = question.getText();
            String insertQuestion =
                    "INSERT INTO " + questionTableName + " (TEXT, EXAM_ID) VALUES('" + questionText + "', (" + examId + "))";
            System.out.println(insertQuestion + ";");
            String questionId = "SELECT id FROM " + questionTableName + " WHERE question.text LIKE '" + questionText + "'";
            generateAnswers(question, questionId);

        }
    }

    private static void generateAnswers(Question question, String questionId) {
        for (Answer answer : question.getAnswers()) {
            String answerText = answer.getText();
            Boolean answerIsCorrect = answer.getIsCorrect();
            String insertAnswer =
                    "INSERT INTO " + answerTableName +
                            " (text, is_correct, question_id) VALUES('"
                            + answerText + "', " + (answerIsCorrect ? "1" : "0") + ", (" + questionId + "))";
            System.out.println(insertAnswer + ";");
        }
    }
}
