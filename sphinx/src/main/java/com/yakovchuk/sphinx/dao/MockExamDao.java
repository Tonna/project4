package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MockExamDao implements ExamDao {

    private final Map<String, Exam> data;

    public MockExamDao(Exam... exams) {
        data = new HashMap<>();
        for (Exam exam : exams) {
            data.put(exam.getId(), exam);
        }
    }

    @Override
    public Exam get(String id) {
        return data.get(id);
    }

    @Override
    public Collection<Exam> getAll() {
        return data.values();
    }

    @Override
    public Exam create(Exam toCreate) {
        Exam.Builder builder = new Exam.Builder().
                id(getRandomId())
                .subject(toCreate.getSubject())
                .name(toCreate.getName());
        Collection<Question> questions = toCreate.getQuestions();
        for (Question question : questions) {
            Question.Builder questionBuilder = new Question.Builder().id(getRandomId()).text(question.getText());
            Collection<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {
                questionBuilder.addAnswer(
                        new Answer.Builder()
                                .id(getRandomId())
                                .text(answer.getText())
                                .isCorrect(answer.getIsCorrect())
                                .build());
            }
            builder.addQuestion(questionBuilder.build());

        }
        Exam toPersist = builder.build();

        data.put(toPersist.getId(), toPersist);
        return toPersist;
    }

    private String getRandomId() {
        return Integer.toString(new Random().nextInt());
    }

    @Override
    public Exam update(Exam toUpdate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Exam delete(Exam toDelete) {
        throw new UnsupportedOperationException();
    }
}
