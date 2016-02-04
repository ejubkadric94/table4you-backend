# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table abh_user_address (
  email                     varchar(80),
  streetName                varchar(100),
  city                      varchar(100),
  country                   varchar(100),
  constraint uq_abh_user_address_email unique (email))
;

create table abh_user_token (
  email                     varchar(80) not null,
  token                     varchar(32),
  expirationDate            timestamp,
  constraint pk_abh_user_token primary key (email))
;

create table abh_user (
  email                     varchar(80) not null,
  password                  varchar(100),
  passwordConfirmation      varchar(100),
  firstName                 varchar(25),
  lastName                  varchar(25),
  phone                     integer,
  gender                    varchar(6),
  birthdate                 date,
  auth_token_email          varchar(80),
  isConfirmed               boolean,
  constraint uq_abh_user_auth_token_email unique (auth_token_email),
  constraint pk_abh_user primary key (email))
;

alter table abh_user_address add constraint fk_abh_user_address_user_1 foreign key (email) references abh_user (email);
create index ix_abh_user_address_user_1 on abh_user_address (email);
alter table abh_user add constraint fk_abh_user_authToken_2 foreign key (auth_token_email) references abh_user_token (email);
create index ix_abh_user_authToken_2 on abh_user (auth_token_email);



# --- !Downs

drop table if exists abh_user_address cascade;

drop table if exists abh_user_token cascade;

drop table if exists abh_user cascade;

