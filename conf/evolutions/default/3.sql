# --- !Ups

INSERT INTO `abh_user_token`(`userEmail`, `token`) VALUES ("admin@table4you.com","3039e946-2644-44e2-af3c-bfe0830d81ce");
INSERT INTO `abh_user_address`(`userEmail`, `streetName`, `city`, `country`) VALUES ("admin@table4you.com","Admin","Admin","Admin");
INSERT INTO `abh_user`(`email`, `password`, `passwordConfirmation`, `firstName`, `lastName`,   `phone`, `gender`, `birthdate`, `auth_token_userEmail`, `isConfirmed`, `is_admin`) VALUES ("admin@table4you.com","admin","admin","admin","admin","54454788", "Male", "1994-01-16","admin@table4you.com",true,true);
