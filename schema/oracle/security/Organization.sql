drop table  ORGANIZATION;
create table ORGANIZATION
(
	ORGANIZATION_ID 		number(12) not null primary key,
	PARENT_ID			number(12),
	NAME			varchar(200) not null,
	CODE			varchar(200) not null,
	SORT_ORDER			varchar(200),
	STATUS			number(12),
	DATATYPE			varchar(2000),
	DESCRIPTION			varchar(2000),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_ORGANIZATION_ID;
create sequence SEQ_ORGANIZATION_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

