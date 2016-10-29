package com.yakovchuk.project4.controller;

import com.yakovchuk.project4.profile.NullProfile;
import com.yakovchuk.project4.profile.ProfileImpl;
import com.yakovchuk.project4.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ProfileServletTest {

    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ProfileService profileService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProfileImpl profileImpl;

    private ProfileServlet profileServlet;

    @Before
    public void setUp() throws ServletException {
        profileServlet = new ProfileServlet();

        MockitoAnnotations.initMocks(this);
        when(servletContext.getAttribute("profileService")).thenReturn(profileService);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        profileServlet.init(servletConfig);
    }


    @Test
    public void testServletContextInjected() {
        assertNotNull(profileServlet.getServletContext());
    }

    @Test
    public void testProfileServiceWasRetrievedFromContext() {
        verify(servletContext).getAttribute("profileService");
    }

    @Test
    public void testLogoutSuccess() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("logout");

        profileServlet.doPost(request, response);

        verify(session).removeAttribute("profile");
        verify(request).getRequestDispatcher("login.jsp");
    }

    /*
    In this method we don`t care about anything except
    retrieving profile from session
    */
    @Test
    public void testProfileSelectedFromSessionIfNotLogOutAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("NOT logout");

        profileServlet.doPost(request, response);

        verify(session).getAttribute("profile");
    }

    @Test
    public void testChangeLanguageSuccess() throws ServletException, IOException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(request.getParameter("action")).thenReturn("changeLanguage");
        when(request.getParameter("language")).thenReturn("en");
        when(profileService.getProfile(anyString(), anyString())).thenReturn(profileImpl);
        when(session.getAttribute("profile")).thenReturn(profileImpl);
        when(profileService.changeLanguage(profileImpl, "en")).thenReturn(profileImpl);

        profileServlet.doPost(request, response);

        verify(request, atLeastOnce()).getParameter(captor.capture());
        assertTrue(captor.getAllValues().contains("action"));
        assertTrue(captor.getAllValues().contains("language"));

        verify(profileService).changeLanguage(profileImpl, "en");
        verify(session).setAttribute("profile", profileImpl);
        verify(request).getRequestDispatcher("/exam?action=view");
    }

    @Test
    public void testCredentialsAreRetrievedFromRequest() throws ServletException, IOException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        profileServlet.doPost(request, response);

        verify(profileService).getProfile(anyString(), anyString());
        verify(request, atLeastOnce()).getParameter(captor.capture());
        assertTrue(captor.getAllValues().contains("login"));
        assertTrue(captor.getAllValues().contains("password"));
    }

    @Test
    public void testLoginSuccess() throws ServletException, IOException {
        when(profileService.getProfile(anyString(), anyString())).thenReturn(profileImpl);

        profileServlet.doPost(request, response);

        verify(session).setAttribute("profile", profileImpl);
        verify(request).getRequestDispatcher("/exam?action=view");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testLoginFailure() throws ServletException, IOException {
        when(profileService.getProfile(anyString(), anyString())).thenReturn(NullProfile.getInstance());

        profileServlet.doPost(request, response);

        verify(request).setAttribute("authenticationFailed", "true");
        verify(request).getRequestDispatcher("login.jsp");
    }

    @Test
    public void testGoToExamPageIfValidProfileExistsInSession() throws ServletException, IOException {
        when(session.getAttribute("profile")).thenReturn(profileImpl);

        profileServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/exam?action=view");
    }

}