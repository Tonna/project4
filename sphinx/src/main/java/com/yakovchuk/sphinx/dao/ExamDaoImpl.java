package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Exam;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Collection;

public class ExamDaoImpl implements ExamDao {

    private final DataSource dataSource;
    private Connection connection;

    public ExamDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exam get(String id) {
        return null;
    }

    @Override
    public Collection<Exam> getAll() {
        return null;
    }

    @Override
    public Exam create(Exam toCreate) {
        return null;
    }

    @Override
    public Exam update(Exam toUpdate) {
        return null;
    }

    @Override
    public Exam delete(Exam toDelete) {
        return null;
    }

    @Override
    public Collection<Exam> getAllHeaders() {
        return null;
    }
}
