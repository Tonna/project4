package com.yakovchuk.sphinx.controller;

import com.yakovchuk.sphinx.profile.NullProfile;
import com.yakovchuk.sphinx.profile.Profile;
import com.yakovchuk.sphinx.profile.ProfileImpl;
import com.yakovchuk.sphinx.service.ProfileService;

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
        if (existingProfile != null && existingProfile instanceof ProfileImpl) {
            request.getRequestDispatcher("/exam?action=view").forward(request, response);
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Profile profileFromService = profileService.getProfile(login, password);
        if(profileFromService instanceof NullProfile){
            //TODO add message to method
            request.setAttribute("authenticationFailed", "true");
            goToLogin(request, response);
            return;
        }

        assert(profileFromService != null);

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
