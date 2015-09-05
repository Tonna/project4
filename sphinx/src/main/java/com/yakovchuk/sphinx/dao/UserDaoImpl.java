package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.user.NullUser;
import com.yakovchuk.sphinx.user.User;
import com.yakovchuk.sphinx.user.UserImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;
    private final Map<String, String> rolesMapping;
    private Connection connection;

    public UserDaoImpl(DataSource dataSource, Map<String, String> rolesMapping) {
        this.dataSource = dataSource;
	this.rolesMapping = rolesMapping;
    }

    @Override
    public User getUser(String login, String password) {
        User user = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
            String selectUser = "SELECT LOGIN, PASSWORD FROM PROFILE WHERE LOGIN LIKE ? AND PASSWORD LIKE ?";

            CallableStatement callableStatement = connection.prepareCall(selectUser);

            callableStatement.setNString(1, login);
            callableStatement.setNString(2, password);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            boolean foundUser = resultSet.next();
            UserImpl userReal;
            if(foundUser){
                userReal = new UserImpl();
                String name = resultSet.getString(1);
                String pass = resultSet.getString(2);
                user = userReal;

                String selectRoles = "SELECT R.NAME FROM ROLE R" +
                        " JOIN PROFILE_ROLE PR ON PR.ROLE_ID = R.ID" +
                        " JOIN PROFILE P ON P.ID = PR.PROFILE_ID" +
                        " WHERE P.LOGIN LIKE ?";

                callableStatement.close();
                callableStatement = connection.prepareCall(selectRoles);
                callableStatement.setNString(1, login);
                callableStatement.execute();
                ResultSet resultSet1 = callableStatement.getResultSet();
                ArrayList<String> roles = new ArrayList<String>();
                while(resultSet1.next()) {
                    if(rolesMapping.get(resultSet1.getString(1)) != null){
                        roles.add(rolesMapping.get(resultSet1.getString(1)));
		    }
                }
                userReal.setRoles(roles);

            } else {
                user = NullUser.getInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
