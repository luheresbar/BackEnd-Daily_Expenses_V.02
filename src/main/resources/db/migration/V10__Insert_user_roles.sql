INSERT INTO user_roles (user_id, role_id) VALUES
((SELECT user_id FROM users WHERE username = 'luis'), (SELECT id FROM roles WHERE role_name = 'USER')),
((SELECT user_id FROM users WHERE username = 'daniel'), (SELECT id FROM roles WHERE role_name = 'DEVELOPER')),
((SELECT user_id FROM users WHERE username = 'andrea'), (SELECT id FROM roles WHERE role_name = 'INVITED')),
((SELECT user_id FROM users WHERE username = 'anyi'), (SELECT id FROM roles WHERE role_name = 'ADMIN'));