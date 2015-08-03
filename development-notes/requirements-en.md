#Build knowledge checking web application.

##Functional requirements:

- Tutor creates exam.
- Exam consist of several questions.
- Question has more than one answer to choose from. To answer question one or more answers should be chosen. 
- Exam is related to subject. 
- Student can view exams. 
- Student can take an exam.

##Technical requirements:

###Requirements to source code:
1. It should take not more than 5 seconds for reader to understand what is this class or method for. 
2. Distribute classes by packages in simple and meaningful way.
3. Source code must follow Java Code Conventions.
4. Source code should contain comments.

###Requirements to architecture design:
1. Use Model-View-Controller pattern for application architecture.
2. Use GoF patterns for in business logic implementation. (Which? Strategy, Command, Builder, State, Observer, etc).
3. Implement required functionality using Servlets and JSP.

###Which technologies to use:
1. Domain information should be stored in Data Base. Use JDBC API to access data base. Use standard or custom connection pool.
3. Use JSTL tags in JSP.
4. Create and use custom JSTL tag.
5. Use sessions in business logic implementation.
6. Use filters in business logic implementation.
7. Log application exceptions and events using Log4j.

###Other requirements:
1. Application should support Russian and English languages. For storing information in DB also.
2. Implement login, logout, roles.