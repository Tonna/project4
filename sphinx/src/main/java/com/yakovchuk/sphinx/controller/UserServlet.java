package com.yakovchuk.sphinx.controller;

import com.yakovchuk.sphinx.service.UserService;
import com.yakovchuk.sphinx.service.UserServiceImpl;
import com.yakovchuk.sphinx.user.NullUser;
import com.yakovchuk.sphinx.user.User;
import com.yakovchuk.sphinx.user.UserImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    //TODO create initiation code
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        if ("logout".equals(request.getParameter("action"))) {
            logout(request, response);
        }

        User existingUser = (User) request.getSession().getAttribute("user");
        if (existingUser != null && existingUser instanceof UserImpl) {
            request.getRequestDispatcher("/exam?action=view").forward(request, response);
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User userFromService = userService.getUser(login, password);
        if(userFromService instanceof NullUser){
            //TODO add message to method
            goToLogin(request, response);
        }

        request.getSession().setAttribute("user", userFromService);
        request.getRequestDispatcher("/exam?action=view").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        goToLogin(request, response);
    }

    private void goToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
