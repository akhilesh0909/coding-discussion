INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Akhilesh' AS col1,'bhatt.akhilesh@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'Developer' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'bhatt.akhilesh@thalesgroup.com'
) LIMIT 1;

INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Anika' AS col1,'anika.kalia@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'Developer' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'anika.kalia@thalesgroup.com'
) LIMIT 1;

INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Aarti' AS col1,'aarti-a.patel@external.thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'Developer' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'aarti-a.patel@external.thalesgroup.com'
) LIMIT 1;

INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Sudhanshoo' AS col1,'sudhanshoo.upadhyay@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'DevOps' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'sudhanshoo.upadhyay@thalesgroup.com'
) LIMIT 1;

INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Mohit' AS col1,'mohit.kumar@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'Developer' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'mohit.kumar@thalesgroup.com'
) LIMIT 1;

INSERT INTO user (name,email,password,is_admin,role)
SELECT col1,col2,col3,col4,col5
FROM (SELECT 'Amit Rohatgi' AS col1,'amit.rohatgi@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4,'Developer' AS col5) t
WHERE NOT EXISTS (
    SELECT * FROM user WHERE email= 'amit.rohatgi@thalesgroup.com'
) LIMIT 1;