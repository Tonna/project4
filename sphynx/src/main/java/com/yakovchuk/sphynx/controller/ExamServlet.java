package com.yakovchuk.sphynx.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String create = "create";
        String take = "take";
        String action = request.getParameter("action");
        if(create.equals(action)){
            request.getRequestDispatcher("WEB-INF/view/createExam.jsp").forward(request, response);
        } else if (take.equals(action)){
            request.getRequestDispatcher("WEB-INF/view/takeExam.jsp").forward(request, response);
        } else { //TODO: Implement wrong action handling.
            request.getRequestDispatcher("WEB-INF/view/exams.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
