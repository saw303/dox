INSERT INTO DOX_USER (email, password, username)
VALUES ('root@local.localdomain', '6cf2a952c0a82909e038b6912e3fc3d06834fae0e8353487bf6248523f07fc7e', 'root_test');

INSERT INTO DOX_USER_DOX_ROLE (DOX_USER_id, roles_id)
VALUES ((SELECT id FROM DOX_USER WHERE username = 'root_test'), (SELECT id FROM DOX_ROLE WHERE name = 'USER'));

INSERT INTO DOX_DOMAIN (shortName, strict) VALUES ('strictdomain', 1);

INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL)
VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName = 'strictdomain'), 'Sunrise');

INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL)
VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName = 'strictdomain'), 'Swisscom');

INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable)
VALUES ('STRING', 1, 'strictcompany', (SELECT id FROM DOX_DOMAIN WHERE shortName = 'strictdomain'), 'S_09', 1);

INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable)
VALUES ('CURRENCY', 1, 'money', NULL, 'C_01', 1);

INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id)
VALUES ((SELECT id FROM DOX_ATTR WHERE shortName = 'strictcompany'),
        (SELECT id FROM DOX_DOC_CLASS  WHERE shortName = 'INVOICE'));

INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id)
VALUES ((SELECT id FROM DOX_ATTR WHERE shortName = 'money'),
        (SELECT id FROM DOX_DOC_CLASS WHERE shortName = 'INVOICE'));

INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:strictdomain', 'Strict test domain', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:strictcompany', 'Fixierte Testdomain', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:money', 'Geldbetrag', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:money', 'Amount of Money', 'en');

INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('3', 'DOCUMENT_REFERENCE', '2011-02-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('3', 'DOCUMENT_REFERENCE', '2011-03-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('3', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('4', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('4', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');
INSERT INTO DOX_CLICK_STATS (reference, referenceType, timestamp, username) VALUES ('5', 'DOCUMENT_REFERENCE', '2011-04-01', 'a user');