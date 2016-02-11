# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table abh_user_address (
  userEmail                 VARCHAR(80) DEFAULT 'test@test.com' not null,
  streetName                varchar(100),
  city                      varchar(100),
  country                   varchar(100),
  constraint pk_abh_user_address primary key (userEmail))
;

create table abh_user_token (
  userEmail                 VARCHAR(80) DEFAULT 'test@test.com' not null,
  token                     varchar(200),
  expirationDate            datetime(6),
  constraint pk_abh_user_token primary key (userEmail))
;

create table abh_user (
  email                     varchar(80) not null,
  password                  varchar(100),
  passwordConfirmation      varchar(100),
  firstName                 varchar(25),
  lastName                  varchar(25),
  address_userEmail         VARCHAR(80) DEFAULT 'test@test.com',
  phone                     BIGINT,
  gender                    varchar(6),
  birthdate                 date,
  auth_token_userEmail      VARCHAR(80) DEFAULT 'test@test.com',
  isConfirmed               tinyint(1) default 0,
  constraint uq_abh_user_address_userEmail unique (address_userEmail),
  constraint uq_abh_user_auth_token_userEmail unique (auth_token_userEmail),
  constraint pk_abh_user primary key (email))
;

alter table abh_user add constraint fk_abh_user_address_1 foreign key (address_userEmail) references abh_user_address (userEmail) on delete restrict on update restrict;
create index ix_abh_user_address_1 on abh_user (address_userEmail);
alter table abh_user add constraint fk_abh_user_authToken_2 foreign key (auth_token_userEmail) references abh_user_token (userEmail) on delete restrict on update restrict;
create index ix_abh_user_authToken_2 on abh_user (auth_token_userEmail);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table abh_user_address;

drop table abh_user_token;

drop table abh_user;

SET FOREIGN_KEY_CHECKS=1;

