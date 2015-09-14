package com.yakovchuk.sphinx.domain;

public class Subject {

    private final String id;
    private final String name;

    private Subject(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id = "";
        private String name = "";

        public Builder(Subject subject){
            this.id = subject.id;
            this.name = subject.name;
        }

        public Builder(){}

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Subject build() {
            return new Subject(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;

        Subject subject = (Subject) o;

        if (id != null ? !id.equals(subject.id) : subject.id != null) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
