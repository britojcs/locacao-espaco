INSERT INTO users (id, username, password, firstname, lastname, enabled, created_by, created_at)
VALUES (
	1,
	'Administrador',
	'$2a$10$8.wOwherpNmGqp/XAnCQp.ARai0ZSpdLWnPEZ1Awx/TlHD6wxowgO', -- @admin
	'Administrador',
	'Sistema',
	true,
	'Sistema',
	CURRENT_TIMESTAMP
);
ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_ADMIN');




