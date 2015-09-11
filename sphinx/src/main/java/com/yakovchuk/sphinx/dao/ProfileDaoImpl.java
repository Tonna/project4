package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.profile.NullProfile;
import com.yakovchuk.sphinx.profile.Profile;
import com.yakovchuk.sphinx.profile.ProfileImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ProfileDaoImpl implements ProfileDao {

    private String selectProfileQuery;
    private String selectRolesOfProfileQuery;
    private String roleAlias;
    private final DataSource dataSource;
    private final Map<String, String> rolesMapping;



    public ProfileDaoImpl(DataSource dataSource, Map<String, String> rolesMapping) {
        this.dataSource = dataSource;
        this.rolesMapping = rolesMapping;
    }

    @Override
    public Profile getProfile(String login, String password) {
        Profile profile = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectProfileStatement = connection.prepareStatement(selectProfileQuery)) {

            selectProfileStatement.setNString(1, login);
            selectProfileStatement.setNString(2, password);

            boolean isProfileFound;
            try (ResultSet resultSet = selectProfileStatement.executeQuery()) {
                isProfileFound = resultSet.next();
            }

            if (isProfileFound) {
                ProfileImpl profileImp = new ProfileImpl();

                ArrayList<String> roles = new ArrayList<>();
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectRolesOfProfileQuery)) {
                    preparedStatement.setNString(1, login);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String role = resultSet.getString(roleAlias);
                            if (rolesMapping.get(role) != null) {
                                roles.add(rolesMapping.get(role));
                            }
                        }
                    }
                }

                profileImp.setRoles(roles);
                profileImp.setLogin(login);
                profile = profileImp;

            } else {
                profile = NullProfile.getInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;
    }

    public void setSelectProfileQuery(String selectProfileQuery) {
        this.selectProfileQuery = selectProfileQuery;
    }

    public void setSelectRolesOfProfileQuery(String selectRolesOfProfileQuery) {
        this.selectRolesOfProfileQuery = selectRolesOfProfileQuery;
    }

    public void setRoleAlias(String roleAlias) {
        this.roleAlias = roleAlias;
    }
}
