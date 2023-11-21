INSERT INTO User (name,email,password,is_admin)
SELECT col1,col2,col3,col4
FROM (SELECT 'Akhilesh' AS col1,'bhatt.akhilesh@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4) t
WHERE NOT EXISTS (
    SELECT * FROM User WHERE email= 'bhatt.akhilesh@thalesgroup.com'
) LIMIT 1;

INSERT INTO User (name,email,password,is_admin)
SELECT col1,col2,col3,col4
FROM (SELECT 'Anika' AS col1,'anika.kalia@thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4) t
WHERE NOT EXISTS (
    SELECT * FROM User WHERE email= 'anika.kalia@thalesgroup.com'
) LIMIT 1;

INSERT INTO User (name,email,password,is_admin)
SELECT col1,col2,col3,col4
FROM (SELECT 'Aarti' AS col1,'aarti-a.patel@external.thalesgroup.com' AS col2,'$2a$10$LiWV.6cuMckDJvmYINcgs.n2Z82ZH.RuPx1XPUk20SMWw/sYMA0s.' AS col3,TRUE AS col4) t
WHERE NOT EXISTS (
    SELECT * FROM User WHERE email= 'aarti-a.patel@external.thalesgroup.com'
) LIMIT 1;