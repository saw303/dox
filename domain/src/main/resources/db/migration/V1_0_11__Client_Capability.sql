CREATE TABLE DOX_CLIENT (
  id        BIGINT      NOT NULL AUTO_INCREMENT,
  shortName VARCHAR(25) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB;

ALTER TABLE DOX_CLIENT
ADD CONSTRAINT UK_cjqacgkjapa1usf90qlrxoi0p UNIQUE (shortName);

INSERT INTO DOX_CLIENT (shortName) VALUES ('wangler');

-- DOC_CLASS

ALTER TABLE DOX_DOC_CLASS ADD COLUMN client_id BIGINT NULL;

UPDATE DOX_DOC_CLASS
SET client_id = (SELECT
                   id
                 FROM DOX_CLIENT
                 WHERE shortName = 'wangler');

ALTER TABLE DOX_DOC_CLASS ADD COLUMN client_id BIGINT NOT NULL;

ALTER TABLE DOX_DOC_CLASS
ADD CONSTRAINT FK_ahrd5jrttgc117sb9tfpn470q
FOREIGN KEY (client_id)
REFERENCES DOX_CLIENT (id);

-- ATTRIBUTE

ALTER TABLE DOX_ATTR ADD COLUMN client_id BIGINT NULL;

UPDATE DOX_ATTR
SET client_id = (SELECT
                   id
                 FROM DOX_CLIENT
                 WHERE shortName = 'wangler');

ALTER TABLE DOX_ATTR ADD COLUMN client_id BIGINT NOT NULL;

ALTER TABLE DOX_ATTR
ADD CONSTRAINT FK_1w9k5bufjopui91desoa9w0be
FOREIGN KEY (client_id)
REFERENCES DOX_CLIENT (id);

-- USER

CREATE TABLE DOX_USER_DOX_CLIENT (
  DOX_USER_id BIGINT NOT NULL,
  clients_id  BIGINT NOT NULL,
  PRIMARY KEY (DOX_USER_id, clients_id)
)
  ENGINE =InnoDB;

ALTER TABLE DOX_USER_DOX_CLIENT
ADD CONSTRAINT FK_synp6jnkvu4dah88pf1dlkuj6
FOREIGN KEY (clients_id)
REFERENCES DOX_CLIENT (id);

ALTER TABLE DOX_USER_DOX_CLIENT
ADD CONSTRAINT FK_m3puppi5y39qjs0suraekgq8w
FOREIGN KEY (DOX_USER_id)
REFERENCES DOX_USER (id);

INSERT INTO DOX_USER_DOX_CLIENT (DOX_USER_id, clients_id) VALUES ((SELECT
                                                                     id
                                                                   FROM DOX_CLIENT
                                                                   WHERE shortName = 'wangler'), (SELECT
                                                                                                    id
                                                                                                  FROM DOX_USER));

-- DOCUMENT
ALTER TABLE DOX_DOC ADD COLUMN client_id BIGINT NULL;

UPDATE DOX_DOC
SET client_id = (SELECT
                   id
                 FROM DOX_CLIENT
                 WHERE shortName = 'wangler');

ALTER TABLE DOX_DOC ADD COLUMN client_id BIGINT NOT NULL;

ALTER TABLE DOX_DOC
ADD CONSTRAINT FK_s9vyghjc5mjhajeaaadixh6ag
FOREIGN KEY (client_id)
REFERENCES DOX_CLIENT (id);