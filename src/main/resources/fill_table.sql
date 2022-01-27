--users--
INSERT INTO test.users (age, email, name, password, surname) VALUES (25, 'ivan@email.com', 'ivan', 'ivan', 'ivanov')
INSERT INTO test.users (age, email, name, password, surname) VALUES (45, 'petr@email.com', 'petr', 'petr', 'petrov')

--roles--
INSERT INTO test.roles (role_name) VALUES ('ROLE_ADMIN')
INSERT INTO test.roles (role_name) VALUES ('ROLE_USER')

--user_role--
INSERT INTO test.user_role (user_id, role_id) VALUES (1, 1)
INSERT INTO test.user_role (user_id, role_id) VALUES (1, 2)
INSERT INTO test.user_role (user_id, role_id) VALUES (2, 2)
COMMIT;