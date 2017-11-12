INSERT INTO DOX_CLIENT (short_name) VALUES ('wangler');

INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('INVOICE', (SELECT
                                                                       id
                                                                     FROM DOX_CLIENT
                                                                     WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('TAXES', (SELECT
                                                                     id
                                                                   FROM DOX_CLIENT
                                                                   WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('SALARY_REPORTS', (SELECT
                                                                              id
                                                                            FROM DOX_CLIENT
                                                                            WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('CONTRACTS', (SELECT
                                                                         id
                                                                       FROM DOX_CLIENT
                                                                       WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('BANK_DOCUMENTS', (SELECT
                                                                              id
                                                                            FROM DOX_CLIENT
                                                                            WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('VARIA', (SELECT
                                                                     id
                                                                   FROM DOX_CLIENT
                                                                   WHERE short_name = 'wangler'));
INSERT INTO DOX_DOC_CLASS (short_name, client_id) VALUES ('DIPLOMA', (SELECT
                                                                       id
                                                                     FROM DOX_CLIENT
                                                                     WHERE short_name = 'wangler'));

INSERT INTO DOX_DOMAIN (short_name, strict) VALUES ('company', 0);
INSERT INTO DOX_DOMAIN (short_name, strict) VALUES ('banks', 0);

INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'company'), 'Sunrise');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'company'), 'Swisscom');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'company'), 'Jemako');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'company'), 'Coop Supercard');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'banks'), 'Credit Suisse');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'banks'), 'Raiffeisen');
INSERT INTO DOX_DOMAIN_VALUES (domain_id, VAL) VALUES ((SELECT
                                                          id
                                                        FROM DOX_DOMAIN
                                                        WHERE short_name = 'banks'), 'PostFinance');

INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 0, 'company', (SELECT
                                   id
                                 FROM DOX_DOMAIN
                                 WHERE short_name = 'company'), 'S_01', 1, (SELECT
                                                                             id
                                                                           FROM DOX_CLIENT
                                                                           WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('DATE', 0, 'invoiceDate', NULL, 'LD_01', 1, (SELECT
                                                       id
                                                     FROM DOX_CLIENT
                                                     WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('DATE', 0, 'taxDate', NULL, 'LD_02', 1, (SELECT
                                                   id
                                                 FROM DOX_CLIENT
                                                 WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('DOUBLE', 0, 'invoiceAmount', NULL, 'F_01', 1, (SELECT
                                                          id
                                                        FROM DOX_CLIENT
                                                        WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('DOUBLE', 0, 'salaryAmount', NULL, 'F_02', 1, (SELECT
                                                         id
                                                       FROM DOX_CLIENT
                                                       WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('DATE', 0, 'salaryDate', NULL, 'LD_03', 1, (SELECT
                                                      id
                                                    FROM DOX_CLIENT
                                                    WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 0, 'title', NULL, 'S_02', 1, (SELECT
                                                  id
                                                FROM DOX_CLIENT
                                                WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 0, 'institute', (SELECT
                                     id
                                   FROM DOX_DOMAIN
                                   WHERE short_name = 'banks'), 'S_03', 1, (SELECT
                                                                             id
                                                                           FROM DOX_CLIENT
                                                                           WHERE short_name = 'wangler'));
INSERT INTO DOX_ATTR (data_type, optional, short_name, domain_id, mapping_column, updateable, client_id)
VALUES ('STRING', 0, 'accountNumber', NULL, 'S_04', 1, (SELECT
                                                          id
                                                        FROM DOX_CLIENT
                                                        WHERE short_name = 'wangler'));

INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'company'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 short_name
                                                                                                                 =
                                                                                                                 'INVOICE'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'company'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 short_name
                                                                                                                 =
                                                                                                                 'CONTRACTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'invoiceDate'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE short_name = 'INVOICE'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'invoiceAmount'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE short_name = 'INVOICE'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'taxDate'), (SELECT
                                                                                                                 id
                                                                                                               FROM
                                                                                                                 DOX_DOC_CLASS
                                                                                                               WHERE
                                                                                                                 short_name
                                                                                                                 =
                                                                                                                 'TAXES'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'salaryAmount'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE short_name = 'SALARY_REPORTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'salaryDate'), (SELECT
                                                                                                                    id
                                                                                                                  FROM
                                                                                                                    DOX_DOC_CLASS
                                                                                                                  WHERE
                                                                                                                    short_name
                                                                                                                    =
                                                                                                                    'SALARY_REPORTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               short_name
                                                                                                               =
                                                                                                               'CONTRACTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'institute'), (SELECT
                                                                                                                   id
                                                                                                                 FROM
                                                                                                                   DOX_DOC_CLASS
                                                                                                                 WHERE
                                                                                                                   short_name
                                                                                                                   =
                                                                                                                   'BANK_DOCUMENTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'accountNumber'),
                                                                               (SELECT
                                                                                  id
                                                                                FROM DOX_DOC_CLASS
                                                                                WHERE short_name = 'BANK_DOCUMENTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               short_name
                                                                                                               =
                                                                                                               'BANK_DOCUMENTS'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               short_name
                                                                                                               =
                                                                                                               'VARIA'));
INSERT INTO dox_doc_class_attributes (attributes_id, document_classes_id) VALUES ((SELECT
                                                                                  id
                                                                                FROM DOX_ATTR
                                                                                WHERE short_name = 'title'), (SELECT
                                                                                                               id
                                                                                                             FROM
                                                                                                               DOX_DOC_CLASS
                                                                                                             WHERE
                                                                                                               short_name
                                                                                                               =
                                                                                                               'DIPLOMA'));

INSERT INTO DOX_USER (email, password, username)
VALUES ('root@local.localdomain', '$2a$08$zrzPEOC3CCwTEdCV9tCfk.EB1VQHFCtB2VxCXRfK5sAuNu/zt1CPO', 'root');
INSERT INTO dox_user_clients (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'root'), (SELECT
                                                                                                  c.id
                                                                                                FROM DOX_CLIENT c
                                                                                                WHERE c.short_name =
                                                                                                      'wangler'));
INSERT INTO DOX_USER (email, password, username)
VALUES ('a.faehndrich@hotmail.com', '$2a$08$XZJZKipnDVJ9apDYpqSlJOZMxMcsg3eJlsYnYI40V0hXVYrf9F6Ru', 'angela');
INSERT INTO dox_user_clients (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'angela'), (SELECT
                                                                                                    c.id
                                                                                                  FROM DOX_CLIENT c
                                                                                                  WHERE c.short_name =
                                                                                                        'wangler'));
INSERT INTO DOX_USER (email, password, username)
VALUES ('silvio.wangler@gmail.com', '$2a$08$OGYo6G66Tu2qGfT820xhG.9be026.ayZp0nomgPeWcCkx4WyiA7nG', 'saw303');
INSERT INTO dox_user_clients (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     u.id
                                                                   FROM DOX_USER u
                                                                   WHERE u.username = 'saw303'), (SELECT
                                                                                                    c.id
                                                                                                  FROM DOX_CLIENT c
                                                                                                  WHERE c.short_name =
                                                                                                        'wangler'));

INSERT INTO DOX_ROLE (NAME) VALUES ('USER');
INSERT INTO DOX_ROLE (NAME) VALUES ('ADMIN');

INSERT INTO DOX_USER_ROLES (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'root'), (SELECT
                                                                                            id
                                                                                          FROM DOX_ROLE
                                                                                          WHERE NAME = 'USER'));
INSERT INTO DOX_USER_ROLES (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'root'), (SELECT
                                                                                            id
                                                                                          FROM DOX_ROLE
                                                                                          WHERE NAME = 'ADMIN'));
INSERT INTO DOX_USER_ROLES (DOX_USER_id, roles_id) VALUES ((SELECT
                                                                 id
                                                               FROM DOX_USER
                                                               WHERE username = 'angela'), (SELECT
                                                                                              id
                                                                                            FROM DOX_ROLE
                                                                                            WHERE NAME = 'USER'));
INSERT INTO DOX_USER_ROLES (DOX_USER_id, roles_id) VALUES ((SELECT
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
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC)
VALUES ('DocumentClass:BANK_DOCUMENTS', 'Banking documents', 'en');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC)
VALUES ('DocumentClass:SALARY_REPORTS', 'Lohnabrechnungen', 'de');
INSERT INTO DOX_TRANSLATIONS (TRS_KEY, TRS_TXT, TRS_LOC)
VALUES ('DocumentClass:SALARY_REPORTS', 'Salary reports', 'en');
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