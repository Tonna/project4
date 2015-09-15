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
import java.util.LinkedList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao {
    private static final Logger logger = LogManager.getLogger(SubjectDaoImpl.class);
    private final DataSource dataSource;
    public String querySelectSubjectsByLanguageCode;
    private String querySelectLanguageById;
    private String queryInsertSubject;
    private String querySelectSubjectById;
    private String columnSubjectId;
    private String aliasLanguageId;
    private String aliasSubjectId;
    private String aliasSubjectName;

    public SubjectDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject create(String subjectName, String languageCode) {
        Subject.Builder builder = new Subject.Builder().name(subjectName);

        try (Connection con = dataSource.getConnection();
             PreparedStatement selectLanguage = con.prepareStatement(querySelectLanguageById);
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

            insertSubject.setNString(1, subjectName);
            insertSubject.setNString(2, languageId);
            insertSubject.execute();
            try (ResultSet generatedKeys = insertSubject.getGeneratedKeys()) {
                generatedKeys.next();
                builder.id(generatedKeys.getString(1));
            }

        } catch (SQLException e) {
            logger.error("Unable to create subject with name {} and languageCode {}", subjectName, languageCode);
            throw new SphinxSQLException(e);
        }
        return builder.build();
    }

    /*
    * TODO think should I retrieve subject by name and not by id?
     * In real life its bad. In learning project?
     * I should choose, mess with Internet Explorer (it cannot display datalist)
     * or selecting subject Ids from exam creation page.
     * In this case subject name plays role of id - which looks wrong.
    */
    @Override
    public Subject get(String subjectName) {
        String subjectId = "";
        Subject.Builder builder = new Subject.Builder().name(subjectName);
        try (Connection con = dataSource.getConnection();
             PreparedStatement selectSubject = con.prepareStatement(querySelectSubjectById)) {

            selectSubject.setNString(1, subjectName);
            try (ResultSet selectSubjectRS = selectSubject.executeQuery()) {
                if (selectSubjectRS.next()) {
                    subjectId = selectSubjectRS.getString(aliasSubjectId);
                    builder.id(subjectId);
                }
            }
        } catch (SQLException e) {
            logger.error("Unable to retrieve subject with name {}", subjectName);
            throw new SphinxSQLException(e);
        }
        return builder.build();
    }

    @Override
    public Collection<Subject> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Subject> getAll(String languageCode) {
        List<Subject> subjectList = new LinkedList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement selectSubjectsByLanguageCode = con.prepareStatement(querySelectSubjectsByLanguageCode)) {

            selectSubjectsByLanguageCode.setNString(1, languageCode);
            try (ResultSet subjectsResultSet = selectSubjectsByLanguageCode.executeQuery()) {
                while (subjectsResultSet.next()) {
                    subjectList.add(new Subject.Builder()
                            .id(subjectsResultSet.getNString(aliasSubjectId))
                            .name(subjectsResultSet.getNString(aliasSubjectName))
                            .build());
                }
            }

        } catch (SQLException e) {
            logger.error("Unable to retrieve all subjects for language {}");
            throw new SphinxSQLException(e);
        }
        return subjectList;
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

    public void setQuerySelectSubjectsByLanguageCode(String querySelectSubjectsByLanguageCode) {
        this.querySelectSubjectsByLanguageCode = querySelectSubjectsByLanguageCode;
    }

    public void setAliasSubjectName(String aliasSubjectName) {
        this.aliasSubjectName = aliasSubjectName;
    }
}
