package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.user.NullUser;
import com.yakovchuk.sphinx.user.User;
import com.yakovchuk.sphinx.user.UserImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MockUserDao implements UserDao {

    Map<String, String> userPasswordMap;
    Map<String, User> userMap;

    public MockUserDao() {
        userPasswordMap = new HashMap<>();

        userPasswordMap.put("tutor", "tutor");
        userPasswordMap.put("student", "student");

        userMap = new HashMap<>();

        UserImpl tutor = new UserImpl();
        tutor.setLogin("tutor");
        tutor.setRoles(Arrays.asList("tutor", "student"));

        UserImpl student = new UserImpl();
        student.setLogin("student");
        student.setRoles(Arrays.asList("student"));

        userMap.put("tutor", tutor);
        userMap.put("student", student);
    }

    @Override
    public User getUser(String login, String password) {
        if (userPasswordMap.get(login) != null && userPasswordMap.get(login).equals(password)) {
            if (userMap.get(login) != null) {
                return userMap.get(login);
            }
        }

        return NullUser.getInstance();
    }
}
