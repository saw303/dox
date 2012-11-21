INSERT INTO DOX_USER(email,password,username) VALUES('root@local.localdomain', 'f6ff543853bb6db0b095484ca9ec220ed4ec242f0dad7cb1b411e49360460063', 'root_test');

INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((select id from DOX_USER where username = 'root_test'), (SELECT id FROM DOX_ROLE where name = 'USER'));