drop table SYS_DICTIONARY;
create table SYS_DICTIONARY
(
	ID 					number(12) not null primary key,
	PARENT_ID			number(12),
	NODES_TYPE			varchar(10),
	PARENT_KEY			varchar(200),
	KEY					varchar(200) not null,
	ALIAS				varchar(200),
	VALUE				varchar(200),
	VALUETYPE			varchar(200),
	VALIDATION			varchar(1000),
	VALUE1				varchar(200),
	VALUETYPE1			varchar(200),
	VALIDATION1			varchar(1000),
	SORT_ORDER			varchar(200),
	ENABLE				number(12) default 1,
	VISUALABLE			number(12) default 1,
	DISPLAY_TYPE		varchar(10),
	EDITABLE			number(12) default 1,
	DESCRIPTION			varchar(2000),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_SYS_DICTIONARY_ID;
create sequence SEQ_SYS_DICTIONARY_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 100000000 cache 20 cycle;

