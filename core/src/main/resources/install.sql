INSERT INTO DOX_DOC_CLASS(shortName) VALUES('INVOICE');
INSERT INTO DOX_DOC_CLASS(shortName) VALUES('TAXES');

INSERT INTO DOX_DOMAIN(shortName) VALUES ('company');

INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Sunrise');
INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Swisscom');

INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'company', (SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'S_01', 1);
INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'invoiceDate', NULL, 'D_01', 1);
-- INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'taxDate', NULL, 'D_02', 1);
INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DOUBLE', 0, 'invoiceAmount', NULL, 'F_01', 1);

INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='company'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='invoiceDate'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='invoiceAmount'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));

INSERT INTO DOX_USER(email,password,username) VALUES('root@local.localdomain', '118b1695b6f328ef2c403078c213e9c98b94da55edb6a7f84905cca1352718e5', 'root');
INSERT INTO DOX_USER(email,password,username) VALUES('a.faehndrich@hotmail.com', '0b9574900e694db245bfeb747031e60f38dece540d258367ae7320cefd9fb540', 'angela');
INSERT INTO DOX_USER(email,password,username) VALUES('silvio.wangler@gmail.com', 'c329d2d5e38866dea452a016409a1b554290a2c2990e8604d57222cf06b481d1', 'saw303');

INSERT INTO DOX_ROLE(NAME) VALUES('USER');
INSERT INTO DOX_ROLE(NAME) VALUES('ADMIN');

INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((SELECT id FROM DOX_USER WHERE username = 'root'), (SELECT id FROM DOX_ROLE WHERE NAME = 'USER'));
INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((SELECT id FROM DOX_USER WHERE username = 'root'), (SELECT id FROM DOX_ROLE WHERE NAME = 'ADMIN'));
INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((SELECT id FROM DOX_USER WHERE username = 'angela'), (SELECT id FROM DOX_ROLE WHERE NAME = 'USER'));
INSERT INTO DOX_USER_DOX_ROLE(DOX_USER_id, roles_id) VALUES((SELECT id FROM DOX_USER WHERE username = 'saw303'), (SELECT id FROM DOX_ROLE WHERE NAME = 'USER'));

INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:INVOICE', 'Rechnung', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:INVOICE', 'Invoice', 'en');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:TAXES', 'Steuer', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:TAXES', 'Taxes', 'en');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Domain:company', 'Firma', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Domain:company', 'Company', 'en');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:company', 'Firma', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:company', 'Company', 'en');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceDate', 'Rechnungsdatum', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceDate', 'Invoice date', 'en');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceAmount', 'Rechnungsbetrag', 'de');
INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceAmount', 'Invoice amount', 'en');