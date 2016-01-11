INSERT INTO DOX_CLIENT (shortName) VALUES ('wangler');

INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('INVOICE', (SELECT
                                                                       id
                                                                     FROM DOX_CLIENT
                                                                     WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('TAXES', (SELECT
                                                                     id
                                                                   FROM DOX_CLIENT
                                                                   WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('SALARY_REPORTS', (SELECT
                                                                              id
                                                                            FROM DOX_CLIENT
                                                                            WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('CONTRACTS', (SELECT
                                                                         id
                                                                       FROM DOX_CLIENT
                                                                       WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('BANK_DOCUMENTS', (SELECT
                                                                              id
                                                                            FROM DOX_CLIENT
                                                                            WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('VARIA', (SELECT
                                                                     id
                                                                   FROM DOX_CLIENT
                                                                   WHERE shortName = 'wangler'));
INSERT INTO DOX_DOC_CLASS (shortName, client_id) VALUES ('DIPLOMA', (SELECT
                                                                       id
                                                                     FROM DOX_CLIENT
                                                                     WHERE shortName = 'wangler'));

INSERT INTO DOX_DOMAIN (shortName, strict) VALUES ('company', 0);
INSERT INTO DOX_DOMAIN (shortName, strict) VALUES ('banks', 0);

INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'company'), 'Sunrise');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'company'), 'Swisscom');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'company'), 'Jemako');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'company'), 'Coop Supercard');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'banks'), 'Credit Suisse');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'banks'), 'Raiffeisen');
INSERT INTO DOX_DOMAIN_VALUES (Domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE shortName = 'banks'), 'PostFinance');

INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('STRING', 0, 'company', (SELECT
                                   id
                                 FROM DOX_DOMAIN
                                 WHERE shortName = 'company'), 'S_01', 1, (SELECT
                                                                             id
                                                                           FROM DOX_CLIENT
                                                                           WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('DATE', 0, 'invoiceDate', NULL, 'LD_01', 1, (SELECT
                                                       id
                                                     FROM DOX_CLIENT
                                                     WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('DATE', 0, 'taxDate', NULL, 'LD_02', 1, (SELECT
                                                   id
                                                 FROM DOX_CLIENT
                                                 WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('DOUBLE', 0, 'invoiceAmount', NULL, 'F_01', 1, (SELECT
                                                          id
                                                        FROM DOX_CLIENT
                                                        WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('DOUBLE', 0, 'salaryAmount', NULL, 'F_02', 1, (SELECT
                                                         id
                                                       FROM DOX_CLIENT
                                                       WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('DATE', 0, 'salaryDate', NULL, 'LD_03', 1, (SELECT
                                                      id
                                                    FROM DOX_CLIENT
                                                    WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('STRING', 0, 'title', NULL, 'S_02', 1, (SELECT
                                                  id
                                                FROM DOX_CLIENT
                                                WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('STRING', 0, 'institute', (SELECT
                                     id
                                   FROM DOX_DOMAIN
                                   WHERE shortName = 'banks'), 'S_03', 1, (SELECT
                                                                             id
                                                                           FROM DOX_CLIENT
                                                                           WHERE shortName = 'wangler'));
INSERT INTO DOX_ATTR (dataType, optional, shortName, domain_id, mappingColumn, updateable, client_id)
VALUES ('STRING', 0, 'accountNumber', NULL, 'S_04', 1, (SELECT
                                                          id
                                                        FROM DOX_CLIENT
                                                        WHERE shortName = 'wangler'));

INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'company'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 shortName
                                                                                                                 =
                                                                                                                 'INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'company'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 shortName
                                                                                                                 =
                                                                                                                 'CONTRACTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'invoiceDate'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE shortName = 'INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'invoiceAmount'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE shortName = 'INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'taxDate'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 shortName
                                                                                                                 =
                                                                                                                 'TAXES'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'salaryAmount'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE shortName = 'SALARY_REPORTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'salaryDate'), (SELECT
                                                                                                                    id
                                                                                                                  FROM
                                                                                                                    DOX_DOC_CLASS
                                                                                                                  WHERE
                                                                                                                    shortName
                                                                                                                    =
                                                                                                                    'SALARY_REPORTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               shortName
                                                                                                               =
                                                                                                               'CONTRACTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'institute'), (SELECT
                                                                                                                   id
                                                                                                                 FROM
                                                                                                                   DOX_DOC_CLASS
                                                                                                                 WHERE
                                                                                                                   shortName
                                                                                                                   =
                                                                                                                   'BANK_DOCUMENTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'accountNumber'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE shortName = 'BANK_DOCUMENTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               shortName
                                                                                                               =
                                                                                                               'BANK_DOCUMENTS'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               shortName
                                                                                                               =
                                                                                                               'VARIA'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR (attributes_id, documentClasses_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE shortName = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               shortName
                                                                                                               =
                                                                                                               'DIPLOMA'));

INSERT INTO DOX_USER (email, password, username)
VALUES ('root@local.localdomain', '$2a$08$zrzPEOC3CCwTEdCV9tCfk.EB1VQHFCtB2VxCXRfK5sAuNu/zt1CPO', 'root');
INSERT INTO DOX_USER_DOX_CLIENT (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'root'), (SELECT
                                                                                                  c.id
                                                                                                FROM DOX_CLIENT c
                                                                                                WHERE c.shortName =
                                                                                                      'wangler'));
INSERT INTO DOX_USER (email, password, username)
VALUES ('a.faehndrich@hotmail.com', '$2a$08$XZJZKipnDVJ9apDYpqSlJOZMxMcsg3eJlsYnYI40V0hXVYrf9F6Ru', 'angela');
INSERT INTO DOX_USER_DOX_CLIENT (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'angela'), (SELECT
                                                                                                    c.id
                                                                                                  FROM DOX_CLIENT c
                                                                                                  WHERE c.shortName =
                                                                                                        'wangler'));
INSERT INTO DOX_USER (email, password, username)
VALUES ('silvio.wangler@gmail.com', '$2a$08$OGYo6G66Tu2qGfT820xhG.9be026.ayZp0nomgPeWcCkx4WyiA7nG', 'saw303');
INSERT INTO DOX_USER_DOX_CLIENT (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'saw303'), (SELECT
                                                                                                    c.id
                                                                                                  FROM DOX_CLIENT c
                                                                                                  WHERE c.shortName =
                                                                                                        'wangler'));

INSERT INTO DOX_ROLE (NAME) VALUES ('USER');
INSERT INTO DOX_ROLE (NAME) VALUES ('ADMIN');

INSERT INTO DOX_USER_DOX_ROLE (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'root'), (SELECT
                                                                                            id
                                                                                          FROM DOX_ROLE
                                                                                          WHERE NAME = 'USER'));
INSERT INTO DOX_USER_DOX_ROLE (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'root'), (SELECT
                                                                                            id
                                                                                          FROM DOX_ROLE
                                                                                          WHERE NAME = 'ADMIN'));
INSERT INTO DOX_USER_DOX_ROLE (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'angela'), (SELECT
                                                                                              id
                                                                                            FROM DOX_ROLE
                                                                                            WHERE NAME = 'USER'));
INSERT INTO DOX_USER_DOX_ROLE (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'saw303'), (SELECT
                                                                                              id
                                                                                            FROM DOX_ROLE
                                                                                            WHERE NAME = 'USER'));

INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:INVOICE', 'Rechnung', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:INVOICE', 'Invoice', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:TAXES', 'Steuern', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:TAXES', 'Taxes', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:CONTRACTS', 'Verträge', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:CONTRACTS', 'Contracts', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:BANK_DOCUMENTS', 'Bankdokumente', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:BANK_DOCUMENTS', 'Banking documents', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:SALARY_REPORTS', 'Lohnabrechnungen', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:SALARY_REPORTS', 'Salary reports', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:VARIA', 'Varia', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:VARIA', 'Varia', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:DIPLOMA', 'Diplome / Zeugnisse', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('DocumentClass:DIPLOMA', 'Diploma', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:company', 'Firma', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:company', 'Company', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:banks', 'Bank', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Domain:banks', 'Bank', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:company', 'Firma', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:company', 'Company', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:invoiceDate', 'Rechnungsdatum', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:invoiceDate', 'Invoice date', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:invoiceAmount', 'Rechnungsbetrag', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:invoiceAmount', 'Invoice amount', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:taxDate', 'Steuerjahr', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:taxDate', 'Tax year', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:salaryAmount', 'Lohnbetrag', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:salaryAmount', 'Salary amount', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:salaryDate', 'Lohndatum', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:salaryDate', 'Salary date', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:title', 'Dokumenttitel', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:title', 'Document title', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:institute', 'Bankinstitut', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:institute', 'Bank institute', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:accountNumber', 'Kontonummer', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('Attribute:accountNumber', 'Account number', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('PostBox:invoices', 'Invoices', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC) VALUES ('PostBox:invoices', 'Rechnungseingang', 'de');


-- Inital Daten Postkorb
INSERT INTO DOX_PBX(shortName, client_id) VALUES ('WANGLER', (SELECT c.id FROM DOX_CLIENT c WHERE c.shortName = 'wangler'));
INSERT INTO DOX_PBX(shortName, client_id, FK_PARENT_PB) VALUES ('invoices', (SELECT c.id FROM DOX_CLIENT c WHERE c.shortName = 'wangler'), (SELECT p.id FROM DOX_PBX p where p.shortName = 'WANGLER'));
INSERT INTO DOX_PBX(shortName, client_id, FK_PARENT_PB) VALUES ((SELECT u.username FROM DOX_USER u WHERE u.username = 'saw303'), (SELECT c.id FROM DOX_CLIENT c WHERE c.shortName = 'wangler'), (SELECT p.id FROM DOX_PBX p where p.shortName = 'WANGLER'));
INSERT INTO DOX_UPBX(id, user_id) VALUES ((SELECT p.id FROM DOX_PBX p where p.shortName = 'saw303'), (SELECT u.id FROM DOX_USER u WHERE u.username = 'saw303'));
INSERT INTO DOX_PBX(shortName, client_id, FK_PARENT_PB) VALUES ((SELECT u.username FROM DOX_USER u WHERE u.username = 'angela'), (SELECT c.id FROM DOX_CLIENT c WHERE c.shortName = 'wangler'), (SELECT p.id FROM DOX_PBX p where p.shortName = 'WANGLER'));
INSERT INTO DOX_UPBX(id, user_id) VALUES ((SELECT p.id FROM DOX_PBX p where p.shortName = 'angela'), (SELECT u.id FROM DOX_USER u WHERE u.username = 'angela'));