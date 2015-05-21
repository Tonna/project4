package com.yakovchuk.sphynx.domain;

public class Answer {

    private final String id;
    private final String text;
    private final Boolean isCorrect;

    private Answer(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.isCorrect = builder.isCorrect;
    }

    public static Builder builder(String text, Boolean isCorrect) {
        return new Builder(text, isCorrect);
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "Answer{" + "id='" + id + '\'' + ", text='" + text + '\'' + ", isCorrect=" + isCorrect + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Answer answer = (Answer) o;

        if (id != null ? !id.equals(answer.id) : answer.id != null)
            return false;
        if (isCorrect != null ? !isCorrect.equals(answer.isCorrect) : answer.isCorrect != null)
            return false;
        if (text != null ? !text.equals(answer.text) : answer.text != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isCorrect != null ? isCorrect.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String id;
        private final String text;
        private final Boolean isCorrect;

        public Builder(String text, Boolean isCorrect) {
            this.text = text;
            this.isCorrect = isCorrect;
        }

        public Builder(Answer answer) {
            this(answer.text, answer.isCorrect);
            this.id = answer.id;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Answer build() {
            return new Answer(this);
        }
    }
}
