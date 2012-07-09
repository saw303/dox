insert into DOX_DOC_CLASS(shortName) values('INVOICE');

insert into DOX_DOMAIN(shortName) values ('company');

insert into Domain_values(Domain_id, values) values ((select id from DOX_DOMAIN where shortName='company'), 'Sunrise');
insert into Domain_values(Domain_id, values) values ((select id from DOX_DOMAIN where shortName='company'), 'Swisscom');

insert into DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn) values('STRING', 0, 'company', (select id from DOX_DOMAIN where shortName='company'), 'S_01');
insert into DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn) values('DATE', 0, 'invoiceDate', NULL, 'D_01');

insert into DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((select id from DOX_ATTR where shortName='company'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));
insert into DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((select id from DOX_ATTR where shortName='invoiceDate'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));