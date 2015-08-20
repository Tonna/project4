package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.user.NullUser;
import com.yakovchuk.sphinx.user.User;

public class MockUserDao implements UserDao {
    @Override
    public User getUser(String login, String password) {
        return NullUser.getInstance();
    }
}
