# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table abh_user_address (
  userEmail                 VARCHAR(80) DEFAULT 'test@test.com' not null,
  streetName                varchar(100),
  city                      varchar(100),
  country                   varchar(100),
  restaurantId              BIGINT,
  constraint pk_abh_user_address primary key (userEmail))
;

create table abh_coordinates (
  restaurantId              BIGINT auto_increment not null,
  latitude                  double,
  longitude                 double,
  constraint pk_abh_coordinates primary key (restaurantId))
;

create table abh_reservation (
  reservationId             BIGINT auto_increment not null,
  restaurantId              BIGINT,
  dateTime                  DATETIME,
  guestCount                integer,
  note                      varchar(300),
  constraint pk_abh_reservation primary key (reservationId))
;

create table abh_restaurant (
  restaurantId              BIGINT auto_increment not null,
  name                      varchar(100),
  address_userEmail         VARCHAR(80) DEFAULT 'test@test.com',
  coordinates_restaurantId  BIGINT,
  phone                     BIGINT,
  workingHours              varchar(20),
  rating                    double,
  reservationPrice          double,
  deals                     varchar(200),
  image                     varchar(255),
  constraint uq_abh_restaurant_address_userEmail unique (address_userEmail),
  constraint uq_abh_restaurant_coordinates_restaurantId unique (coordinates_restaurantId),
  constraint pk_abh_restaurant primary key (restaurantId))
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

alter table abh_restaurant add constraint fk_abh_restaurant_address_1 foreign key (address_userEmail) references abh_user_address (userEmail) on delete restrict on update restrict;
create index ix_abh_restaurant_address_1 on abh_restaurant (address_userEmail);
alter table abh_restaurant add constraint fk_abh_restaurant_coordinates_2 foreign key (coordinates_restaurantId) references abh_coordinates (restaurantId) on delete restrict on update restrict;
create index ix_abh_restaurant_coordinates_2 on abh_restaurant (coordinates_restaurantId);
alter table abh_user add constraint fk_abh_user_address_3 foreign key (address_userEmail) references abh_user_address (userEmail) on delete restrict on update restrict;
create index ix_abh_user_address_3 on abh_user (address_userEmail);
alter table abh_user add constraint fk_abh_user_authToken_4 foreign key (auth_token_userEmail) references abh_user_token (userEmail) on delete restrict on update restrict;
create index ix_abh_user_authToken_4 on abh_user (auth_token_userEmail);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table abh_user_address;

drop table abh_coordinates;

drop table abh_reservation;

drop table abh_restaurant;

drop table abh_user_token;

drop table abh_user;

SET FOREIGN_KEY_CHECKS=1;

