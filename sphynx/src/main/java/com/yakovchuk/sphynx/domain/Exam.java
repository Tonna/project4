package com.yakovchuk.sphynx.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Exam {

    private final String id;
    private final String name;
    private final String subject;
    private final Collection<Question> questions;

    private Exam(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.subject = builder.subject;
        this.questions = builder.questions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Exam{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", subject='" + subject + '\''
                + ", questions=" + questions + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Exam exam = (Exam) o;

        if (id != null ? !id.equals(exam.id) : exam.id != null)
            return false;
        if (name != null ? !name.equals(exam.name) : exam.name != null)
            return false;
        if (questions != null ? !questions.equals(exam.questions) : exam.questions != null)
            return false;
        if (subject != null ? !subject.equals(exam.subject) : exam.subject != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }

    public static class Builder {

        private String name;
        private Collection<Question> questions;
        private String id;
        private String subject;

        public Builder(Exam exam) {
            this.name = exam.name;
            this.questions = exam.questions;
            this.id = exam.id;
            this.subject = exam.subject;
        }

        public Builder(){}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder questions(Collection<Question> questions) {
            this.questions = questions;
            return this;
        }

        public Builder addQuestion(Question question) {
            if (questions == null) {
                questions = new ArrayList<>();
            }
            questions.add(question);
            return this;
        }

        public Exam build() {
            return new Exam(this);
        }

    }
}