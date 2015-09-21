package com.yakovchuk.sphinx.filter;

import com.yakovchuk.sphinx.profile.Profile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class ExamFilter implements Filter {

    private HashMap<String, String> actionRoleMapping;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        actionRoleMapping = (HashMap<String, String>) filterConfig.getServletContext().getAttribute("actionRoleMapping");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();

        //TODO can session be null at all? Better to check session is new or something
        if (session.getAttribute("profile") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            String[] actions = request.getParameterMap().get("action");
            if (actions != null && actions[0] != null && actionRoleMapping.get(actions[0]) != null) {
                Profile profile = (Profile) session.getAttribute("profile");
                if (!profile.getRoles().contains(actionRoleMapping.get(actions[0]))) {
                    request.getRequestDispatcher("/exam?action=view").forward(request, response);
                    return;
                }
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
