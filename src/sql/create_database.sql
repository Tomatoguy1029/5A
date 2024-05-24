CREATE TABLE classrooms (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    info TEXT NOT NULL
);

INSERT INTO classrooms (name, info) VALUES ('101', 'Classroom 101: Math, Capacity: 30');
INSERT INTO classrooms (name, info) VALUES ('102', 'Classroom 102: Science, Capacity: 25');
INSERT INTO classrooms (name, info) VALUES ('103', 'Classroom 103: History, Capacity: 20');
