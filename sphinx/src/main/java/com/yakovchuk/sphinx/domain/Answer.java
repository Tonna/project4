package com.yakovchuk.sphinx.domain;

public class Answer {

    private final String id;
    private final String text;
    private final Boolean isCorrect;

    private Answer(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.isCorrect = builder.isCorrect;
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private String id;
        private String text;
        private Boolean isCorrect = false;

        public Builder(){}

        public Builder(Answer answer) {
            this.id = answer.id;
            this.text = answer.text;
            this.isCorrect = answer.isCorrect;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder isCorrect(Boolean isCorrect) {
            this.isCorrect = isCorrect;
            return this;
        }

        public Builder isCorrect() {
            this.isCorrect = true;
            return this;
        }

        public Answer build() {
            return new Answer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (isCorrect != null ? !isCorrect.equals(answer.isCorrect) : answer.isCorrect != null) return false;
        if (text != null ? !text.equals(answer.text) : answer.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (isCorrect != null ? isCorrect.hashCode() : 0);
        return result;
    }
}
