# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table users (
  email                     varchar(255) not null,
  last_name                 varchar(255),
  first_name                varchar(255),
  password                  varchar(255),
  auth_token                varchar(255),
  is_confirmed              boolean,
  phone                     integer,
  gender                    varchar(255),
  birthdate                 timestamp,
  street_name               varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  constraint pk_users primary key (email))
;




# --- !Downs

drop table if exists users cascade;

