INSERT INTO requirements (code, name, required_credits, active) VALUES
                                                                        ('CS_CORE', 'Computer Science Core', 30, true),
                                                                        ('MATH_CORE', 'Mathematics Core', 15, true);

INSERT INTO courses (code, title, credits) VALUES
                                               ('CS101', 'Introduction to Computer Science', 3),
                                               ('CS102', 'Data Structures and Algorithms', 3),
                                               ('MATH101', 'Calculus I and Applications', 4);

INSERT INTO course_prerequisites (course_code, prerequisites) VALUES
    ('CS102', 'CS101');

INSERT INTO course_requirements (course_code, requirement_id) VALUES
                                                                  ('CS101', 1),
                                                                  ('CS102', 1),
                                                                  ('MATH101', 2);

INSERT INTO professors (name, rating, department, active) VALUES
                                                                       ('Dr. Smith', 0.0, 'Computer Science', true),
                                                                       ('Dr. Johnson', 0.0, 'Mathematics', true);


INSERT INTO students (emplid, gpa, credits, name, school_email, password) VALUES
                                                                              (1001, 0.0, 0, 'Ali Student', 'ali@stu-mail.brooklyn.cuny.edu', 'password'),
                                                                              (1002, 0.0, 0, 'Test Student', 'test@stu-mail.brooklyn.cuny.edu', 'password');


INSERT INTO enrollments (enrollment_id, emplid, course_code, credits, grade, semester, professor, completed) VALUES
                                                                                                                 (1, 1001, 'CS101', 3, 'A', 'Fall 2025', 'Dr. Smith', true),
                                                                                                                 (2, 1001, 'CS102', 3, NULL, 'Spring 2026', 'Dr. Smith', false),
                                                                                                                 (3, 1002, 'MATH101', 4, 'B+', 'Fall 2025', 'Dr. Johnson', true);


INSERT INTO schedules (schedule_id, emplid, semester) VALUES
    (1, 1001, 'Spring 2026');


UPDATE enrollments
SET schedule_id = 1
WHERE enrollment_id = 2;


INSERT INTO reviews (id, professor_id, professor_name, course_code, comment, score, helpful, unhelpful, review_time) VALUES
                                                                                                                         (1, 1, 'Dr. Smith', 'CS101', 'Great professor, explains concepts very clearly and thoroughly.', 5, 0, 0, CURRENT_TIMESTAMP),
                                                                                                                         (2, 2, 'Dr. Johnson', 'MATH101', 'Challenging but fair, lots of homework but you learn a lot.', 4, 0, 0, CURRENT_TIMESTAMP);

