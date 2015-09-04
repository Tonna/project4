package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.dao.mock.MockUserDao;
import com.yakovchuk.sphinx.dao.UserDao;
import com.yakovchuk.sphinx.user.User;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUser(String login, String password) {
        return userDao.getUser(login, password);
    }
}
