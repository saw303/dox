INSERT INTO DOX_USER(email,password,username) VALUES('root@local.localdomain', '6cf2a952c0a82909e038b6912e3fc3d06834fae0e8353487bf6248523f07fc7e', 'root_test');

INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((select id from DOX_USER where username = 'root_test'), (SELECT id FROM DOX_ROLE where name = 'USER'));