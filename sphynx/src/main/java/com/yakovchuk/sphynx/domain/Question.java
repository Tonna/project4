package com.yakovchuk.sphynx.domain;

import java.util.Collection;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Question question = (Question) o;

        if (answers != null ? !answers.equals(question.answers) : question.answers != null)
            return false;
        if (id != null ? !id.equals(question.id) : question.id != null)
            return false;
        if (text != null ? !text.equals(question.text) : question.text != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }

    public static class Builder {
        public final String text;
        public final Collection<Answer> answers;
        public String id;

        public Builder(String text, Collection<Answer> answers) {
            this.text = text;
            this.answers = answers;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
