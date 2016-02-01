# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table users (
  email                     varchar(255) not null,
  surname                   varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  token                     varchar(255),
  is_confirmed              boolean,
  constraint pk_users primary key (email))
;




# --- !Downs

drop table if exists users cascade;

