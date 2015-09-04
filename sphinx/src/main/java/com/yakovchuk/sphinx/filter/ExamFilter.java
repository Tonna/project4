package com.yakovchuk.sphinx.filter;

import com.yakovchuk.sphinx.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (session == null || session.getAttribute("user") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            //TODO put return here?
        } else {
            String action = request.getParameterMap().get("action")[0];
            if (actionRoleMapping.get(action) != null) {
                User user = (User) session.getAttribute("user");
                if(!user.getRoles().contains(actionRoleMapping.get(action))){
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
