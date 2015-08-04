package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Exam;

public interface ExamChecker {
    int checkExam(Exam originalExam, Exam submittedExam);
}
