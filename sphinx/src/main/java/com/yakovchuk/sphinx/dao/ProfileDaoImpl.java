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

    public static final String SELECT_PROFILE = "SELECT LOGIN FROM PROFILE WHERE LOGIN LIKE ? AND PASSWORD LIKE ?";
    public static final String SELECT_ROLES_OF_PROFILE = "SELECT R.NAME AS ROLE_OF_PROFILE FROM ROLE R JOIN PROFILE_ROLE PR ON PR.ROLE_ID = R.ID JOIN PROFILE P ON P.ID = PR.PROFILE_ID WHERE P.LOGIN LIKE ?";
    private final String ROLE_ALIAS = "ROLE_OF_PROFILE";
    private final DataSource dataSource;
    private final Map<String, String> rolesMapping;

    public ProfileDaoImpl(DataSource dataSource, Map<String, String> rolesMapping) {
        this.dataSource = dataSource;
        this.rolesMapping = rolesMapping;
    }

    @Override
    public Profile getProfile(String login, String password) {
        Profile profile = null;
        ArrayList<String> roles = new ArrayList<>();
        boolean isProfileFound = false;
        try (Connection con = dataSource.getConnection();
             PreparedStatement selectProfileStatement = con.prepareStatement(SELECT_PROFILE)) {

            selectProfileStatement.setNString(1, login);
            selectProfileStatement.setNString(2, password);
            try (ResultSet resultSet = selectProfileStatement.executeQuery()) {
                isProfileFound = resultSet.next();
            }
            if (isProfileFound) {
                ProfileImpl profileImp = new ProfileImpl();

                try (PreparedStatement preparedStatement = con.prepareStatement(SELECT_ROLES_OF_PROFILE)) {
                    preparedStatement.setNString(1, login);
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    ;
                    while (resultSet1.next()) {
                        if (rolesMapping.get(resultSet1.getString(1)) != null) {
                            roles.add(rolesMapping.get(resultSet1.getString(1)));
                        }
                    }
                    profileImp.setRoles(roles);
                    profileImp.setLogin(login);
                    profile = profileImp;
                }

            } else {
                profile = NullProfile.getInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
}
