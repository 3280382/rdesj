drop table  SYS_USER;
create table SYS_USER
(
	SYS_USER_ID 		number(12) not null primary key,
	USERNAME			varchar(200) not null,
	LOGIN_NAME			varchar(200) not null,
	PASSWORD			varchar(200),
	EMAIL			varchar(200),
	STATUS			number(12),
	ACCOUNT_NON_EXPIRED			number(1) default 1,
	CREDENTIALS_NON_EXPIRED			number(1) default 1,
	ENABLED			number(1) default 1,
	ACCOUNT_NON_LOCKED			number(1) default 1,
	ORGANIZATION_ID			number(12),
	CODE			varchar(200),
	MOBILE			varchar(200),
	PHONE			varchar(200),
	ADDRESS			varchar(200),
	DESCRIPTION			varchar(2000),
	RECENT_PASSWORD			varchar(2000),
	IS_LOGIN			number(1) default 0,
	TRY_TIMES			number(12),
	FAIL_LOGIN_TIMES			number(12),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_SYS_USER_ID;
create sequence SEQ_SYS_USER_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

