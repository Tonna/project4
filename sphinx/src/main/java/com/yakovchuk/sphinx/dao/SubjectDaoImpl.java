package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class SubjectDaoImpl implements SubjectDao {
    private static final Logger logger = LogManager.getLogger(SubjectDaoImpl.class);
    private final DataSource dataSource;
    private String querySelectLanguageById;
    private String queryInsertSubject;
    private String querySelectSubjectById;
    private String columnSubjectId;
    private String aliasLanguageId;
    private String aliasSubjectId;

    public SubjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject create(String name, String languageCode) {
        String subjectId = "";

        try (Connection con = dataSource.getConnection();
             PreparedStatement selectLanguage = con.prepareStatement(querySelectLanguageById);
             PreparedStatement selectSubject = con.prepareStatement(querySelectSubjectById);
             PreparedStatement insertSubject = con.prepareStatement(queryInsertSubject, new String[]{columnSubjectId})) {

            selectLanguage.setNString(1, languageCode);
            String languageId;
            try (ResultSet languageResultSet = selectLanguage.executeQuery()) {
                if (languageResultSet.next()) {
                    languageId = languageResultSet.getString(aliasLanguageId);
                } else {
                    logger.error("Cannot find language with code {}", languageCode);
                    throw new SphinxSQLException("No language with code '" + languageCode + "'");
                }
            }

            selectSubject.setNString(1, name);
            try (ResultSet selectSubjectRS = selectSubject.executeQuery()) {
                if (selectSubjectRS.next()) {
                    subjectId = selectSubjectRS.getString(aliasSubjectId);
                } else {
                    insertSubject.setNString(1, name);
                    insertSubject.setNString(2, languageId);
                    insertSubject.execute();
                    try (ResultSet generatedKeys = insertSubject.getGeneratedKeys()) {
                        generatedKeys.next();
                        subjectId = generatedKeys.getString(1);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Unable to create subject with name {} and languageCode {}", name, languageCode);
            throw new SphinxSQLException(e);
        }
        return new Subject.Builder().id(subjectId).name(name).build();
    }

    @Override
    public Subject get(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Subject> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject create(Subject toCreate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject update(Subject toUpdate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject delete(Subject toDelete) {
        throw new UnsupportedOperationException();
    }


    public void setQuerySelectLanguageById(String querySelectLanguageById) {
        this.querySelectLanguageById = querySelectLanguageById;
    }

    public void setQueryInsertSubject(String queryInsertSubject) {
        this.queryInsertSubject = queryInsertSubject;
    }

    public void setQuerySelectSubjectById(String querySelectSubjectById) {
        this.querySelectSubjectById = querySelectSubjectById;
    }

    public void setColumnSubjectId(String columnSubjectId) {
        this.columnSubjectId = columnSubjectId;
    }

    public void setAliasLanguageId(String aliasLanguageId) {
        this.aliasLanguageId = aliasLanguageId;
    }

    public void setAliasSubjectId(String aliasSubjectId) {
        this.aliasSubjectId = aliasSubjectId;
    }
}
