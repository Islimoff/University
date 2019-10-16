
CREATE TABLE groups  (
group_id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL
);

CREATE TABLE students (
student_id SERIAL PRIMARY KEY,
group_id INTEGER,
name VARCHAR(100),
FOREIGN KEY(group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE teachers (
teacher_id SERIAL PRIMARY KEY,
name VARCHAR(100)
);

CREATE TABLE subjects  (
subject_id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL
);

CREATE TABLE audiences  (
audience_id SERIAL PRIMARY KEY,
number INTEGER NOT NULL
);

CREATE TABLE teachers_subjects  (
teacher_id INTEGER NOT NULL,
subject_id INTEGER NOT NULL,
FOREIGN KEY(teacher_id) REFERENCES teachers(teacher_id )ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(subject_id) REFERENCES subjects(subject_id) ON UPDATE CASCADE ON DELETE CASCADE,
UNIQUE(teacher_id, subject_id)
);

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
