package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Exam;

public interface ExamChecker {
    int checkExam(Exam originalExam, Exam submittedExam);
}
