package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Exam;

import java.util.Map;

public interface ExamMapper {
    Exam mapExam(Map<String, String[]> parameterMap);
}
