CREATE TABLE DOX_PBX (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  shortName    VARCHAR(255) NOT NULL,
  client_id    BIGINT       NOT NULL,
  FK_PARENT_PB BIGINT,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

ALTER TABLE DOX_PBX
ADD CONSTRAINT UK_pw3qvn8yqybqyse979onpt2ym UNIQUE (shortName);

ALTER TABLE DOX_PBX
ADD CONSTRAINT FK_qndqjuhq6atruiyhrwrjr5g3e
FOREIGN KEY (client_id)
REFERENCES DOX_CLIENT (id);

ALTER TABLE DOX_PBX
ADD CONSTRAINT FK_3vrftq80l390k9uj872r690hw
FOREIGN KEY (FK_PARENT_PB)
REFERENCES DOX_PBX (id);

-- USER PBX
create table DOX_UPBX (
  id bigint not null,
  user_id bigint not null,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table DOX_UPBX
add constraint FK_sepqcaceml2fh2hm3ha2pi0nu
foreign key (user_id)
references DOX_USER (id);

alter table DOX_UPBX
add constraint FK_3txuix9s9lp7rmdrhkhw7p17o
foreign key (id)
references DOX_PBX (id);

-- PBX ITEM SOURCE

CREATE TABLE DOX_PBX_ITM_SRC (
  id     BIGINT       NOT NULL AUTO_INCREMENT,
  source VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

