INSERT INTO DOX_DOC_CLASS(shortName) VALUES('INVOICE');
INSERT INTO DOX_DOC_CLASS(shortName) VALUES('TAXES');

INSERT INTO DOX_DOMAIN(shortName) VALUES ('company');

INSERT INTO Domain_values(Domain_id, values) VALUES ((select id from DOX_DOMAIN where shortName='company'), 'Sunrise');
INSERT INTO Domain_values(Domain_id, values) VALUES ((select id from DOX_DOMAIN where shortName='company'), 'Swisscom');

INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'company', (select id from DOX_DOMAIN where shortName='company'), 'S_01', 1);
INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'invoiceDate', NULL, 'D_01', 1);
INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DOUBLE', 0, 'invoiceAmount', NULL, 'F_01', 1);

INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((select id from DOX_ATTR where shortName='company'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((select id from DOX_ATTR where shortName='invoiceDate'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));
INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((select id from DOX_ATTR where shortName='invoiceAmount'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));