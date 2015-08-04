package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Exam;

import java.util.Map;

public interface ExamMapper {
    Exam mapExam(Map<String, String[]> parameterMap);
}
