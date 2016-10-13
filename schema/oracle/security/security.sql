drop table RESOURCE_URL;
create table RESOURCE_URL
(
  	RESOURCE_URL_ID   	number(12) not null primary key,
  	AUTHORITY_ID 		number(12) not null,
  	URL         		varchar(1000) not null,	
  	DATATYPE 			varchar(100),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_RESOURCE_URL_ID;
create sequence SEQ_RESOURCE_URL_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

drop table SYS_USER_ROLE;
create table SYS_USER_ROLE
(
  	ROLE_ID 			number(12) not null,
  	SYS_USER_ID			number(12) not null
);

drop table ROLE_AUTHORITY;
create table ROLE_AUTHORITY
(
 	ROLE_ID     		number(12) not null,
  	AUTHORITY_ID 		number(12) not null
);


