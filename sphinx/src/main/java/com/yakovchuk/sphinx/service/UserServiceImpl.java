package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.dao.mock.MockUserDao;
import com.yakovchuk.sphinx.dao.UserDao;
import com.yakovchuk.sphinx.user.User;

public class UserServiceImpl implements UserService {

    //TODO create initiation code
    private UserDao userDao = new MockUserDao();

    @Override
    public User getUser(String login, String password) {
        return userDao.getUser(login, password);
    }
}
