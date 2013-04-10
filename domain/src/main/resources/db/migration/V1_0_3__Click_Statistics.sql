create table DOX_CLICK_STATS (
  id bigint not null auto_increment,
  reference varchar(255) not null,
  referenceType varchar(255) not null,
  timestamp datetime not null,
  username varchar(255) not null,
  primary key (id)
) ENGINE=InnoDB;