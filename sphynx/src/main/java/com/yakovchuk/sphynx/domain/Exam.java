package com.yakovchuk.sphynx.domain;

import java.util.Collection;

public class Exam {

    private String id;
    private String name;
    private Collection<Question> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    @Override public String toString() {
        return "Exam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", questions=" + questions +
                '}';
    }

    @Override public boolean equals(Object o) {
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

        return true;
    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}
