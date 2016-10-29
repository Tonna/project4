package com.yakovchuk.project4.util;

import com.yakovchuk.project4.domain.Exam;

public interface ExamChecker {
    int checkExam(Exam originalExam, Exam submittedExam);
}
