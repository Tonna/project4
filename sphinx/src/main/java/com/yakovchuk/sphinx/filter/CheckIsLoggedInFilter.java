package com.yakovchuk.sphinx.filter;

import com.yakovchuk.sphinx.profile.ProfileImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CheckIsLoggedInFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        Object profile = ((HttpServletRequest) servletRequest).getSession().getAttribute("profile");
        if (profile != null && profile instanceof ProfileImpl) {
            servletRequest.getRequestDispatcher("/exam?action=view").forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
