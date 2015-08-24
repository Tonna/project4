package com.yakovchuk.sphinx.filter;

import com.yakovchuk.sphinx.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamFilter implements Filter {

    List<String> restrictedActions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //TODO actions should be set via config?
        restrictedActions = new ArrayList<>();
        restrictedActions.add("creationForm");
        restrictedActions.add("create");
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
            if (restrictedActions.contains(action)) {
                User user = (User) session.getAttribute("user");
                if(!user.getRoles().contains("tutor")){
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
