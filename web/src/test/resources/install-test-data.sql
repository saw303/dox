INSERT INTO DOX_CLIENT (short_name) VALUES ('wangler_test');

INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('DUMMY_DOC', (SELECT
                                                                         id
                                                                       FROM DOX_CLIENT
                                                                       WHERE short_name = 'wangler_test'));

INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 1, 'name', NULL, 'S_05', 1, (SELECT
                                                 id
                                               FROM DOX_CLIENT
                                               WHERE short_name = 'wangler_test'));

INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id)
VALUES ((SELECT
           id
         FROM DOX_ATTR
         WHERE short_name = 'name'),
        (SELECT
           id
         FROM DOX_DOC_CLASS
         WHERE short_name = 'DUMMY_DOC'));

INSERT INTO DOX_USER (email, password, username)
VALUES ('root@local.localdomain', '$2a$08$zrzPEOC3CCwTEdCV9tCfk.EB1VQHFCtB2VxCXRfK5sAuNu/zt1CPO', 'root_test');

INSERT INTO DOX_USER_CLIENTS (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'root_test'), (SELECT
                                                                                                       c.id
                                                                                                     FROM DOX_CLIENT c
                                                                                                     WHERE c.short_name =
                                                                                                           'wangler'));
INSERT INTO DOX_USER_CLIENTS (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'root_test'), (SELECT
                                                                                                       c.id
                                                                                                     FROM DOX_CLIENT c
                                                                                                     WHERE c.short_name =
                                                                                                           'wangler_test'));

INSERT INTO DOX_USER_ROLES (DOX_USER_id, roles_id)
VALUES ((SELECT
           id
         FROM DOX_USER
         WHERE username = 'root_test'), (SELECT
                                           id
                                         FROM DOX_ROLE
                                         WHERE name = 'USER'));

INSERT INTO DOX_DOMAIN (short_name, strict) VALUES ('strictdomain', 1);

INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL)
VALUES ((SELECT
           id
         FROM DOX_DOMAIN
         WHERE short_name = 'strictdomain'), 'Sunrise');

INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL)
VALUES ((SELECT
           id
         FROM DOX_DOMAIN
         WHERE short_name = 'strictdomain'), 'Swisscom');

INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 1, 'strictcompany', (SELECT
                                         id
                                       FROM DOX_DOMAIN
                                       WHERE short_name = 'strictdomain'), 'S_09', 1, (SELECT
                                                                                        id
                                                                                      FROM DOX_CLIENT
                                                                                      WHERE short_name = 'wangler'));

INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('CURRENCY', 1, 'money', NULL, 'C_01', 1, (SELECT
                                                    id
                                                  FROM DOX_CLIENT
                                                  WHERE short_name = 'wangler'));

INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id)
VALUES ((SELECT
           id
         FROM DOX_ATTR
         WHERE short_name = 'strictcompany'),
        (SELECT
           id
         FROM DOX_DOC_CLASS
         WHERE short_name = 'INVOICE'));

INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id)
VALUES ((SELECT
           id
         FROM DOX_ATTR
         WHERE short_name = 'money'),
        (SELECT
           id
         FROM DOX_DOC_CLASS
         WHERE short_name = 'INVOICE'));

INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:strictdomain', 'Strict test domain', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC)
VALUES ('Attribute:strictcompany', 'Fixierte Testdomain', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:money', 'Geldbetrag', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:money', 'Amount of Money', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:name', 'Name', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:name', 'Name', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:DUMMY_DOC', 'Dummy Document', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:DUMMY_DOC', 'Dummy Dokument', 'de');

INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('3', 'DOCUMENT_REFERENCE', '2011-02-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('3', 'DOCUMENT_REFERENCE', '2011-03-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('3', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('4', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('4', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, reference_type, timestamp, username)
VALUES ('5', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');

INSERT INTO DOX_TAG(name) VALUES ('Business');
INSERT INTO DOX_TAG(name) VALUES ('Important');
INSERT INTO DOX_TAG(name) VALUES ('Useless');