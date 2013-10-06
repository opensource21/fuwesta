insert into T_USER (ID, VERSION, FIRST_NAME, LAST_NAME, USER_ID) values (1, 1,'Erwin','Mustermann','emum');
insert into TAG (ID, VERSION, ACTIVE, NAME) values (1,1, true,'tag1');
insert into TAG (ID, VERSION, ACTIVE, NAME) values (2,1, true,'tag2');
insert into PUBLIC.POST (ID, VERSION, CONTENT, CREATION_TIME, TITLE, USER_ID) values (1,1,'This is the content',null,'TestPost', null);
