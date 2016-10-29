package com.yakovchuk.project4.dao;

import com.yakovchuk.project4.profile.NullProfile;
import com.yakovchuk.project4.profile.Profile;
import com.yakovchuk.project4.profile.ProfileImpl;
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
    private String queryUpdateProfileLanguage;


    public ProfileDaoImpl(DataSource dataSource, Map<String, String> rolesMapping) {
        this.dataSource = dataSource;
        this.rolesMapping = rolesMapping;
    }

    @Override
    public Profile getProfile(String login, String password) {
        assert (login != null && !login.isEmpty());
        assert (password != null && !password.isEmpty());

        Profile profile;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectProfileStatement = connection.prepareStatement(querySelectProfileByLoginAndPassword)) {

            selectProfileStatement.setNString(1, login);
            selectProfileStatement.setNString(2, password);

            try (ResultSet profileResultSet = selectProfileStatement.executeQuery()) {
                if (profileResultSet.next()) {
                    ProfileImpl profileImp = new ProfileImpl();
                    ArrayList<String> roles = getProfileRoles(login, connection);

                    profileImp.setRoles(roles);
                    profileImp.setLogin(login);
                    profileImp.setLanguageCode(profileResultSet.getNString(2));
                    profile = profileImp;

                } else {
                    profile = NullProfile.getInstance();
                }
            }

        } catch (SQLException e) {
            logger.error("Unable to retrieve profile with login {}", login);
            throw new SphinxSQLException(e);
        }
        return profile;
    }

    private ArrayList<String> getProfileRoles(String login, Connection connection) throws SQLException {
        ArrayList<String> roles = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(querySelectProfileRolesByProfileLogin)) {
            preparedStatement.setNString(1, login);

            try (ResultSet profileRolesResultSet = preparedStatement.executeQuery()) {

                while (profileRolesResultSet.next()) {
                    String role = profileRolesResultSet.getString(aliasProfileRoleOfProfile);
                    if (rolesMapping.get(role) != null) {
                        roles.add(rolesMapping.get(role));
                    }
                }
            }
        }
        return roles;
    }

    @Override
    public Profile changeLanguage(Profile profile, String newLanguage) {
        Profile profileToReturn = profile;
        ProfileImpl updateProfile;
        try (Connection con = dataSource.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(queryUpdateProfileLanguage)){

            con.setAutoCommit(false);

            preparedStatement.setNString(1, newLanguage);
            String login = profile.getLogin();
            preparedStatement.setNString(2, login);

            //TODO Add check that update was executed?
            preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);

            updateProfile = new ProfileImpl();
            updateProfile.setLogin(login);
            updateProfile.setLanguageCode(newLanguage);
            updateProfile.setRoles(getProfileRoles(login, con));
            profileToReturn = updateProfile;

        } catch (SQLException e) {
            logger.error("Unable to set profiles '{}' default language to '{}'", profile, newLanguage);
            throw new SphinxSQLException(e);
        }


        return profileToReturn;
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

    public void setQueryUpdateProfileLanguage(String queryUpdateProfileLanguage) {
        this.queryUpdateProfileLanguage = queryUpdateProfileLanguage;
    }
}
