package com.yakovchuk.project4.util;

import com.yakovchuk.project4.domain.Exam;

import java.util.Map;

public interface ExamMapper {
    //TODO receive Map<String, String> as input
    Exam mapExam(Map<String, String[]> parameterMap);
}
