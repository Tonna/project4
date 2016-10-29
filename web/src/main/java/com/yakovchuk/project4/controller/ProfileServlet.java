package com.yakovchuk.project4.controller;

import com.yakovchuk.project4.profile.NullProfile;
import com.yakovchuk.project4.profile.Profile;
import com.yakovchuk.project4.profile.ProfileImpl;
import com.yakovchuk.project4.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    private ProfileService profileService;

    @Override
    public void init() {
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        if ("logout".equals(request.getParameter("action"))) {
            logout(request, response);
            return;
        }
        Profile existingProfile = (Profile) request.getSession().getAttribute("profile");

        if("changeLanguage".equals(request.getParameter("action"))) {
            Profile updatedProfile = profileService.changeLanguage(existingProfile, request.getParameter("language"));
            request.getSession().setAttribute("profile", updatedProfile);
            request.getRequestDispatcher("/exam?action=view").forward(request, response);
            return;
        }

        if (existingProfile != null && existingProfile instanceof ProfileImpl) {
            request.getRequestDispatcher("/exam?action=view").forward(request, response);
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Profile profileFromService = profileService.getProfile(login, password);
        if(profileFromService instanceof NullProfile){
            request.setAttribute("authenticationFailed", "true");
            goToLogin(request, response);
            return;
        }

        request.getSession().setAttribute("profile", profileFromService);
        request.getRequestDispatcher("/exam?action=view").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("profile");
        goToLogin(request, response);
    }

    private void goToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

