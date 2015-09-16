package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.profile.NullProfile;
import com.yakovchuk.sphinx.profile.Profile;
import com.yakovchuk.sphinx.profile.ProfileImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ProfileDaoImpl implements ProfileDao {

    private final static Logger logger = LogManager.getLogger(ProfileDaoImpl.class);
    private final DataSource dataSource;
    private final Map<String, String> rolesMapping;
    private String querySelectProfileByLoginAndPassword;
    private String querySelectProfileRolesByProfileLogin;
    private String aliasProfileRoleOfProfile;


    public ProfileDaoImpl(DataSource dataSource, Map<String, String> rolesMapping) {
        this.dataSource = dataSource;
        this.rolesMapping = rolesMapping;
    }

    @Override
    public Profile getProfile(String login, String password) {
        Profile profile = null;
        String languageCode = "";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectProfileStatement = connection.prepareStatement(querySelectProfileByLoginAndPassword)) {

            selectProfileStatement.setNString(1, login);
            selectProfileStatement.setNString(2, password);

            boolean isProfileFound;
            try (ResultSet resultSet = selectProfileStatement.executeQuery()) {
                isProfileFound = resultSet.next();
                languageCode = resultSet.getNString(2);
            }

            if (isProfileFound) {
                ProfileImpl profileImp = new ProfileImpl();

                ArrayList<String> roles = new ArrayList<>();
                try (PreparedStatement preparedStatement = connection.prepareStatement(querySelectProfileRolesByProfileLogin)) {
                    preparedStatement.setNString(1, login);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String role = resultSet.getString(aliasProfileRoleOfProfile);
                            if (rolesMapping.get(role) != null) {
                                roles.add(rolesMapping.get(role));
                            }
                        }
                    }
                }

                profileImp.setRoles(roles);
                profileImp.setLogin(login);
                profileImp.setLanguageCode(languageCode);
                profile = profileImp;

            } else {
                profile = NullProfile.getInstance();
            }

        } catch (SQLException e) {
            logger.error("Unable to retrieve profile with login {}", login);
            throw new SphinxSQLException(e);
        }
        return profile;
    }

    public void setQuerySelectProfileByLoginAndPassword(String querySelectProfileByLoginAndPassword) {
        this.querySelectProfileByLoginAndPassword = querySelectProfileByLoginAndPassword;
    }

    public void setQuerySelectProfileRolesByProfileLogin(String querySelectProfileRolesByProfileLogin) {
        this.querySelectProfileRolesByProfileLogin = querySelectProfileRolesByProfileLogin;
    }

    public void setAliasProfileRoleOfProfile(String aliasProfileRoleOfProfile) {
        this.aliasProfileRoleOfProfile = aliasProfileRoleOfProfile;
    }
}
