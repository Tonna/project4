package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Exam;

import java.util.Map;

public interface ExamMapper {
    //TODO receive Map<String, String> as input
    Exam mapExam(Map<String, String[]> parameterMap);
}
