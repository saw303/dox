insert into DOX_DOC_CLASS(shortName) values('INVOICE');

insert into DOX_DOMAIN(shortName) values ('company');

insert into Domain_values(Domain_id, values) values ((select id from DOX_DOMAIN where shortName='company'), 'Sunrise');
insert into Domain_values(Domain_id, values) values ((select id from DOX_DOMAIN where shortName='company'), 'Swisscom');

insert into DOX_ATTR(dataType, optional, shortName, domain_id) values('STRING', 0, 'company', (select id from DOX_DOMAIN where shortName='company'));
insert into DOX_ATTR(dataType, optional, shortName, domain_id) values('DATE', 0, 'invoiceDate', NULL);

insert into DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((select id from DOX_ATTR where shortName='company'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));
insert into DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((select id from DOX_ATTR where shortName='invoiceDate'), (select id from DOX_DOC_CLASS where shortName='INVOICE'));