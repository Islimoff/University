DROP TABLE IF EXISTS groups CASCADE;

CREATE TABLE groups  (
group_id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS students CASCADE;

CREATE TABLE students (
student_id SERIAL PRIMARY KEY,
group_id INTEGER,
name VARCHAR(100),
FOREIGN KEY(group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL
);

DROP TABLE IF EXISTS teachers CASCADE;

CREATE TABLE teachers (
teacher_id SERIAL PRIMARY KEY,
name VARCHAR(100)
);

DROP TABLE IF EXISTS subjets CASCADE;

CREATE TABLE subjects  (
subject_id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS audiences CASCADE;

CREATE TABLE audiences  (
audience_id SERIAL PRIMARY KEY,
number INTEGER NOT NULL
);

DROP TABLE IF EXISTS teachers_subjects CASCADE;

CREATE TABLE teachers_subjects  (
teacher_id INTEGER NOT NULL,
subject_id INTEGER NOT NULL,
FOREIGN KEY(teacher_id) REFERENCES teachers(teacher_id )ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(subject_id) REFERENCES subjects(subject_id) ON UPDATE CASCADE ON DELETE CASCADE,
UNIQUE(teacher_id, subject_id)
);

DROP TABLE IF EXISTS lessons CASCADE;

CREATE TABLE lessons  (
lesson_id SERIAL PRIMARY KEY,
audience_id INTEGER,
teacher_id INTEGER,
subject_id INTEGER,
group_id INTEGER,
date DATE,
start_lesson TIME,
end_lesson TIME,
FOREIGN KEY(audience_id) REFERENCES audiences(audience_id),
FOREIGN KEY(teacher_id) REFERENCES teachers(teacher_id),
FOREIGN KEY(subject_id) REFERENCES subjects(subject_id),
FOREIGN KEY(group_id) REFERENCES groups(group_id)
);
