
    create table DOX_ATTR (
        id bigint not null auto_increment,
        dataType varchar(255),
        mappingColumn varchar(4) not null,
        optional boolean not null,
        shortName varchar(15) not null unique,
        updateable boolean not null,
        domain_id bigint,
        primary key (id),
        unique (dataType, mappingColumn)
    );

    create table DOX_DOC (
        id bigint not null auto_increment,
        creationDate datetime not null,
        hash varchar(64) not null unique,
        mimeType varchar(50) not null,
        originalFilename varchar(255) not null,
        pageCount integer not null,
        documentClass_id bigint,
        indexStore_id bigint,
        primary key (id)
    );

    create table DOX_DOC_CLASS (
        id bigint not null auto_increment,
        shortName varchar(15) not null unique,
        primary key (id)
    );

    create table DOX_DOC_CLASS_DOX_ATTR (
        documentClasses_id bigint not null,
        attributes_id bigint not null,
        primary key (documentClasses_id, attributes_id)
    );

    create table DOX_DOMAIN (
        id bigint not null auto_increment,
        shortName varchar(15) not null unique,
        primary key (id)
    );

    create table DOX_DOMAIN_VALUES (
        Domain_id bigint not null,
        VAL varchar(255) not null
    );

    create table DOX_IDX_MAP (
        id bigint not null auto_increment,
        attributeName varchar(15) not null,
        stringRepresentation varchar(255) not null,
        document_id bigint,
        primary key (id)
    );

    create table DOX_IDX_STORE (
        id bigint not null auto_increment,
        D_01 datetime,
        D_02 datetime,
        D_03 datetime,
        D_04 datetime,
        D_05 datetime,
        D_06 datetime,
        D_07 datetime,
        D_08 datetime,
        D_09 datetime,
        D_10 datetime,
        F_01 decimal(19,2),
        F_02 decimal(19,2),
        F_03 decimal(19,2),
        F_04 decimal(19,2),
        F_05 decimal(19,2),
        F_06 decimal(19,2),
        F_07 decimal(19,2),
        F_08 decimal(19,2),
        F_09 decimal(19,2),
        F_10 decimal(19,2),
        L_01 bigint,
        L_02 bigint,
        L_03 bigint,
        L_04 bigint,
        L_05 bigint,
        L_06 bigint,
        L_07 bigint,
        L_08 bigint,
        L_09 bigint,
        L_10 bigint,
        S_01 varchar(255),
        S_02 varchar(255),
        S_03 varchar(255),
        S_04 varchar(255),
        S_05 varchar(255),
        S_06 varchar(255),
        S_07 varchar(255),
        S_08 varchar(255),
        S_09 varchar(255),
        S_10 varchar(255),
        S_11 varchar(255),
        S_12 varchar(255),
        S_13 varchar(255),
        S_14 varchar(255),
        S_15 varchar(255),
        S_16 varchar(255),
        S_17 varchar(255),
        S_18 varchar(255),
        S_19 varchar(255),
        S_20 varchar(255),
        document_id bigint not null,
        primary key (id),
        unique (document_id)
    );

    create table DOX_PERMISSION (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table DOX_ROLE (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table DOX_ROLE_DOX_PERMISSION (
        DOX_ROLE_id bigint not null,
        grantedAuthorities_id bigint not null,
        primary key (DOX_ROLE_id, grantedAuthorities_id),
        unique (grantedAuthorities_id)
    );

    create table DOX_TRANSLATIONS (
        id bigint not null auto_increment,
        TRS_KEY varchar(150) not null,
        TRS_TXT varchar(2500) not null,
        TRS_LOC varchar(5) not null,
        primary key (id),
        unique (TRS_LOC, TRS_KEY)
    );

    create table DOX_USER (
        id bigint not null auto_increment,
        email varchar(255) not null,
        password varchar(255) not null,
        username varchar(25) not null unique,
        primary key (id)
    );

    create table DOX_USER_DOX_ROLE (
        DOX_USER_id bigint not null,
        roles_id bigint not null,
        primary key (DOX_USER_id, roles_id)
    );

    alter table DOX_ATTR 
        add index FK866035C3414D87DC (domain_id), 
        add constraint FK866035C3414D87DC 
        foreign key (domain_id) 
        references DOX_DOMAIN (id);

    alter table DOX_DOC 
        add index FK98FAE3068CD40598 (documentClass_id), 
        add constraint FK98FAE3068CD40598 
        foreign key (documentClass_id) 
        references DOX_DOC_CLASS (id);

    alter table DOX_DOC 
        add index FK98FAE30612EFE6DC (indexStore_id), 
        add constraint FK98FAE30612EFE6DC 
        foreign key (indexStore_id) 
        references DOX_IDX_STORE (id);

    alter table DOX_DOC_CLASS_DOX_ATTR 
        add index FKF322172320CB07BD (attributes_id), 
        add constraint FKF322172320CB07BD 
        foreign key (attributes_id) 
        references DOX_ATTR (id);

    alter table DOX_DOC_CLASS_DOX_ATTR 
        add index FKF3221723D3CBB56A (documentClasses_id), 
        add constraint FKF3221723D3CBB56A 
        foreign key (documentClasses_id) 
        references DOX_DOC_CLASS (id);

    alter table DOX_DOMAIN_VALUES 
        add index FK6DF3E96B414D87DC (Domain_id), 
        add constraint FK6DF3E96B414D87DC 
        foreign key (Domain_id) 
        references DOX_DOMAIN (id);

    create index IDX_STR_VAL on DOX_IDX_MAP (stringRepresentation);

    alter table DOX_IDX_MAP 
        add index FK2340F882C16F3FC (document_id), 
        add constraint FK2340F882C16F3FC 
        foreign key (document_id) 
        references DOX_DOC (id);

    alter table DOX_IDX_STORE 
        add index FK45CB840D2C16F3FC (document_id), 
        add constraint FK45CB840D2C16F3FC 
        foreign key (document_id) 
        references DOX_DOC (id);

    alter table DOX_ROLE_DOX_PERMISSION 
        add index FKA02EE8F81E688A88 (grantedAuthorities_id), 
        add constraint FKA02EE8F81E688A88 
        foreign key (grantedAuthorities_id) 
        references DOX_PERMISSION (id);

    alter table DOX_ROLE_DOX_PERMISSION 
        add index FKA02EE8F884F09C54 (DOX_ROLE_id), 
        add constraint FKA02EE8F884F09C54 
        foreign key (DOX_ROLE_id) 
        references DOX_ROLE (id);

    alter table DOX_USER_DOX_ROLE 
        add index FK5AEE06AA137859F (roles_id), 
        add constraint FK5AEE06AA137859F 
        foreign key (roles_id) 
        references DOX_ROLE (id);

    alter table DOX_USER_DOX_ROLE 
        add index FK5AEE06AAA35C2909 (DOX_USER_id), 
        add constraint FK5AEE06AAA35C2909 
        foreign key (DOX_USER_id) 
        references DOX_USER (id);


    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('INVOICE');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('TAXES');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('SALARY_REPORTS');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('CONTRACTS');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('BANK_DOCUMENTS');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('VARIA');
    INSERT INTO DOX_DOC_CLASS(shortName) VALUES('DIPLOMA');

    INSERT INTO DOX_DOMAIN(shortName) VALUES ('company');
    INSERT INTO DOX_DOMAIN(shortName) VALUES ('banks');

    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Sunrise');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Swisscom');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Jemako');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'Coop Supercard');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='banks'), 'Credit Suisse');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='banks'), 'Raiffeisen');
    INSERT INTO DOX_DOMAIN_VALUES(Domain_id, VAL) VALUES ((SELECT id FROM DOX_DOMAIN WHERE shortName='banks'), 'PostFinance');

    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'company', (SELECT id FROM DOX_DOMAIN WHERE shortName='company'), 'S_01', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'invoiceDate', NULL, 'D_01', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'taxDate', NULL, 'D_02', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DOUBLE', 0, 'invoiceAmount', NULL, 'F_01', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DOUBLE', 0, 'salaryAmount', NULL, 'F_02', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('DATE', 0, 'salaryDate', NULL, 'D_03', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'title', NULL, 'S_02', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'institute', (SELECT id FROM DOX_DOMAIN WHERE shortName='banks'), 'S_03', 1);
    INSERT INTO DOX_ATTR(dataType, optional, shortName, domain_id, mappingColumn, updateable) VALUES('STRING', 0, 'accountNumber', NULL, 'S_04', 1);

    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='company'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id ) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='company'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='CONTRACTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='invoiceDate'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='invoiceAmount'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='INVOICE'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='taxDate'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='TAXES'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='salaryAmount'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='SALARY_REPORTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='salaryDate'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='SALARY_REPORTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='title'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='CONTRACTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='institute'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='BANK_DOCUMENTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='accountNumber'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='BANK_DOCUMENTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='title'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='BANK_DOCUMENTS'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='title'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='VARIA'));
    INSERT INTO DOX_DOC_CLASS_DOX_ATTR(attributes_id, documentClasses_id) VALUES ((SELECT id FROM DOX_ATTR WHERE shortName='title'), (SELECT id FROM DOX_DOC_CLASS WHERE shortName='DIPLOMA'));

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
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:TAXES', 'Steuern', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:TAXES', 'Taxes', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:CONTRACTS', 'Verträge', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:CONTRACTS', 'Contracts', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:BANK_DOCUMENTS', 'Bankdokumente', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:BANK_DOCUMENTS', 'Banking documents', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:SALARY_REPORTS', 'Lohnabrechnungen', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:SALARY_REPORTS', 'Salary reports', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:VARIA', 'Varia', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:VARIA', 'Varia', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:DIPLOMA', 'Diplome / Zeugnisse', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('DocumentClass:DIPLOMA', 'Diploma', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Domain:company', 'Firma', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Domain:company', 'Company', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:company', 'Firma', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:company', 'Company', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceDate', 'Rechnungsdatum', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceDate', 'Invoice date', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceAmount', 'Rechnungsbetrag', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:invoiceAmount', 'Invoice amount', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:taxDate', 'Steuerjahr', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:taxDate', 'Tax year', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:salaryAmount', 'Lohnbetrag', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:salaryAmount', 'Salary amount', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:salaryDate', 'Lohndatum', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:salaryDate', 'Salary date', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:title', 'Dokumenttitel', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:title', 'Document title', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:institute', 'Bankinstitut', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:institute', 'Bank institute', 'en');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:accountNumber', 'Kontonummer', 'de');
    INSERT INTO DOX_TRANSLATIONS(TRS_KEY, TRS_TXT, TRS_LOC) VALUES('Attribute:accountNumber', 'Account number', 'en');