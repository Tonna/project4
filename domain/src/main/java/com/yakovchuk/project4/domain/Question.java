package com.yakovchuk.project4.domain;

import java.util.Collection;
import java.util.LinkedList;

public class Question {

    private final String id;
    private final String text;
    private final Collection<Answer> answers;

    private Question(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.answers = builder.answers;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" + "id='" + id + '\'' + ", text='" + text + '\'' + ", answers=" + answers + '}';
    }

    public static class Builder {
        private String text = "";
        private Collection<Answer> answers = new LinkedList<>();
        private String id = "";

        public Builder() {
        }

        public Builder(Question question) {
            this.text = question.text;
            this.answers = question.answers;
            this.id = question.id;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder answers(Collection<Answer> answers) {
            this.answers = answers;
            return this;
        }

        public Builder addAnswer(Answer answer) {
            answers.add(answer);
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (answers != null ? !answers.equals(question.answers) : question.answers != null) return false;
        if (text != null ? !text.equals(question.text) : question.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }
}
