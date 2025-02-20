-- Role: ADMIN
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1); -- CREATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2); -- READ
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3); -- UPDATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 4); -- DELETE

-- Role: USER
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1); -- CREATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2); -- READ

-- Role: INVITED
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 2); -- READ

-- Role: DEVELOPER
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 1); -- CREATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 2); -- READ
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 3); -- UPDATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 4); -- DELETE
INSERT INTO role_permissions (role_id, permission_id) VALUES (4, 5); -- REFACTOR