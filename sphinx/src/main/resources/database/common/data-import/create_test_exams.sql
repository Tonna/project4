INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES('development', (SELECT ID FROM Language WHERE language.name LIKE 'English'));
INSERT INTO EXAM (NAME, SUBJECT_ID) VALUES('dummy exam', (SELECT ID FROM SUBJECT WHERE subject.NAME LIKE 'development'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('first question of dummy exam', (SELECT ID from EXAM WHERE exam.name LIKE 'dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('first answer - wrong', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'first question of dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('second answer - correct', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'first question of dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('third answer - correct', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'first question of dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('fourth answer - wrong', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'first question of dummy exam'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('second question of dummy exam', (SELECT ID from EXAM WHERE exam.name LIKE 'dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('first answer - correct', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'second question of dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('second answer - wrong', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'second question of dummy exam'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('third question of dummy exam', (SELECT ID from EXAM WHERE exam.name LIKE 'dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('first answer - wrong', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'third question of dummy exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('second answer - correct', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'third question of dummy exam'));

INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES('Math', (SELECT ID FROM Language WHERE language.name LIKE 'English'));
INSERT INTO EXAM (NAME, SUBJECT_ID) VALUES('Basic math', (SELECT ID FROM SUBJECT WHERE subject.NAME LIKE 'Math'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('2 ^ 10 = ?', (SELECT ID from EXAM WHERE exam.name LIKE 'Basic math'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('1024', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '2 ^ 10 = ?'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('20', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '2 ^ 10 = ?'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('1 + 1 = ?', (SELECT ID from EXAM WHERE exam.name LIKE 'Basic math'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('2', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '1 + 1 = ?'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('3', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '1 + 1 = ?'));
INSERT INTO EXAM (NAME, SUBJECT_ID) VALUES('Division', (SELECT ID FROM SUBJECT WHERE subject.NAME LIKE 'Math'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('5 / 5', (SELECT ID from EXAM WHERE exam.name LIKE 'Division'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('2', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '5 / 5'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('5', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '5 / 5'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('1', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '5 / 5'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('10 / 2', (SELECT ID from EXAM WHERE exam.name LIKE 'Division'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('1', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '10 / 2'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('10', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '10 / 2'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('5', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '10 / 2'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('12 / 6', (SELECT ID from EXAM WHERE exam.name LIKE 'Division'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('2', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '12 / 6'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('3', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '12 / 6'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('4', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '12 / 6'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('22 / 2', (SELECT ID from EXAM WHERE exam.name LIKE 'Division'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('10', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '22 / 2'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('2', 0, (SELECT id FROM QUESTION WHERE question.text LIKE '22 / 2'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('11', 1, (SELECT id FROM QUESTION WHERE question.text LIKE '22 / 2'));

INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES('Software development', (SELECT ID FROM Language WHERE language.name LIKE 'English'));
INSERT INTO EXAM (NAME, SUBJECT_ID) VALUES('Despair exam', (SELECT ID FROM SUBJECT WHERE subject.NAME LIKE 'Software development'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('Maybe I should type less? Is there a way to develop software fast?', (SELECT ID from EXAM WHERE exam.name LIKE 'Despair exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('No', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'Maybe I should type less? Is there a way to develop software fast?'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('Yes', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'Maybe I should type less? Is there a way to develop software fast?'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('Is it enough boilerplate code?', (SELECT ID from EXAM WHERE exam.name LIKE 'Despair exam'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('Yes', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'Is it enough boilerplate code?'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('No', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'Is it enough boilerplate code?'));
INSERT INTO SUBJECT (NAME, LANGUAGE_ID) VALUES('English', (SELECT ID FROM Language WHERE language.name LIKE 'English'));
INSERT INTO EXAM (NAME, SUBJECT_ID) VALUES('Adjectives. Select correct adjective', (SELECT ID FROM SUBJECT WHERE subject.NAME LIKE 'English'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('The girls were mad because they could not play', (SELECT ID from EXAM WHERE exam.name LIKE 'Adjectives. Select correct adjective'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('happy', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The girls were mad because they could not play'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('sad', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The girls were mad because they could not play'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('angry', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'The girls were mad because they could not play'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('The chair was not hard', (SELECT ID from EXAM WHERE exam.name LIKE 'Adjectives. Select correct adjective'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('brown', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The chair was not hard'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('new', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The chair was not hard'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('soft', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'The chair was not hard'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('Another word for not awake is', (SELECT ID from EXAM WHERE exam.name LIKE 'Adjectives. Select correct adjective'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('asleep', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'Another word for not awake is'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('loud', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'Another word for not awake is'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('scared', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'Another word for not awake is'));
INSERT INTO QUESTION (TEXT, EXAM_ID) VALUES('The door was not closed', (SELECT ID from EXAM WHERE exam.name LIKE 'Adjectives. Select correct adjective'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('shut', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The door was not closed'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('dirty', 0, (SELECT id FROM QUESTION WHERE question.text LIKE 'The door was not closed'));
INSERT INTO ANSWER (text, is_correct, question_id) VALUES('open', 1, (SELECT id FROM QUESTION WHERE question.text LIKE 'The door was not closed'));

--INSERT INTO SUBJECT VALUES(6,'\u0420\u0443\u0441\u0441\u043a\u0438\u0439 \u044f\u0437\u044b\u043a',2)
--INSERT INTO EXAM VALUES(7,'\u041f\u0440\u0430\u0432\u043e\u043f\u0438\u0441\u0430\u043d\u0438\u0435',6)
--INSERT INTO QUESTION VALUES(17,'\u0416\u0438/\u0436\u044b, \u0448\u0438/\u0448\u044b',7)
--INSERT INTO ANSWER VALUES(43,'\u0416\u0438, \u0448\u0438',17,1)
--INSERT INTO ANSWER VALUES(44,'\u0416\u0438, \u0448\u044b',17,0)
--INSERT INTO ANSWER VALUES(45,'\u0416\u044b, \u0448\u0438',17,0)
--INSERT INTO ANSWER VALUES(46,'\u0416\u044b, \u0448\u044b',17,0)

--INSERT INTO SUBJECT VALUES(7,'Hybris',2)
--INSERT INTO EXAM VALUES(8,'Hybris Core',7)
--INSERT INTO QUESTION VALUES(18,'How to create extension in hybris?',8)
--INSERT INTO ANSWER VALUES(47,'ant extgen',18,1)
--INSERT INTO ANSWER VALUES(48,'ant all',18,0)
--INSERT INTO ANSWER VALUES(49,'ant unittest',18,0)
--INSERT INTO QUESTION VALUES(19,'What is default user in hybris?',8)
--INSERT INTO ANSWER VALUES(50,'Admin',19,0)
--INSERT INTO ANSWER VALUES(51,'Anonimous',19,1)
