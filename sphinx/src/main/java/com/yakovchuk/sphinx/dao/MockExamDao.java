package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Exam;

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
        Exam toPersist = new Exam.Builder(toCreate).id(Integer.toString(new Random().nextInt())).build();
        data.put(toPersist.getId(), toPersist);
        return toPersist;
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
