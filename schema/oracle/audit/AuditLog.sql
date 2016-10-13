drop table AUDIT_LOG;
create table AUDIT_LOG
(
	AUDIT_LOG_ID 		number(12) not null primary key,
	USER_ID				varchar(50),
	USERNAME			varchar(50),
	LOGIN_NAME			varchar(50),
	USER_IP				varchar(200),
	TARGET_ID			varchar(100),
	TARGET_ENTITY_TYPE	varchar(50),
	TARGET_NAME			varchar(100),
	TARGET_DESC			varchar(1000),
	TARGET_SNAPSHOT		varchar(4000),
	OP_TYPE				varchar(200),
	OP_TIME				timestamp with time zone default sysdate,
	OP_DESC				varchar(1000),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_AUDIT_LOG_ID;
create sequence SEQ_AUDIT_LOG_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

