package com.yakovchuk.sphynx.dao;

import com.yakovchuk.sphynx.domain.Exam;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MockExamDao implements ExamDao {

    private Set<Exam> data = new HashSet<Exam>();

    public Set<Exam> getData() {
        return data;
    }

    public void setData(Set<Exam> data) {
        this.data = data;
    }

    @Override public Exam get(String id) {
        for(Exam exam : data) {
            if(id.equals(exam.getId())) {
                return exam;
            }
        }
        return null;
    }

    @Override public Collection<Exam> getAll() {
        return null;
    }

    @Override public Exam create(Exam toCreate) {
        return null;
    }

    @Override public Exam update(Exam toUpdate) {
        return null;
    }

    @Override public Exam delete(Exam toDelete) {
        return null;
    }
}
