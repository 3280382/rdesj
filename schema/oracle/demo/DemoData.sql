drop table DEMODATA;
create table DEMODATA
(
	DEMODATA_ID 		number(12) not null primary key,
	NAME 				varchar(200) not null,
	BIRTHDAY			date,
	SEX					varchar(10),
	SALARY				decimal(10,4),
	EMAIL				varchar(200),
	DEMOCOUNTRY_ID		number(12),
	DEMOPROVINCE_ID		number(12),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_DEMODATA_ID;
create sequence SEQ_DEMODATA_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

drop table DEMOCOUNTRY;
create table DEMOCOUNTRY
(
	DEMOCOUNTRY_ID		number(12) not null primary key,
	NAME				varchar(200) not null,
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_DEMOCOUNTRY_ID;
create sequence SEQ_DEMOCOUNTRY_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

insert into DEMOCOUNTRY(DEMOCOUNTRY_ID,NAME) values(1,'中国');
insert into DEMOCOUNTRY(DEMOCOUNTRY_ID,NAME) values(2,'America');

drop table DEMOPROVINCE;
create table DEMOPROVINCE
(
	DEMOPROVINCE_ID		number(12) not null primary key,
	DEMOCOUNTRY_ID		number(12) not null,
	NAME				varchar(200) not null,
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_DEMOPROVINCE_ID;
create sequence SEQ_DEMOPROVINCE_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;

insert into DEMOPROVINCE(DEMOPROVINCE_ID, DEMOCOUNTRY_ID, NAME) values(10001,1,'广东');
insert into DEMOPROVINCE(DEMOPROVINCE_ID, DEMOCOUNTRY_ID, NAME) values(10002,1,'湖南');
insert into DEMOPROVINCE(DEMOPROVINCE_ID, DEMOCOUNTRY_ID, NAME) values(20001,2,'Alabama');
insert into DEMOPROVINCE(DEMOPROVINCE_ID, DEMOCOUNTRY_ID, NAME) values(20002,2,'Hawaii');

drop table DEMODEPT;
create table DEMODEPT
(
	DEMODEPT_ID 		number(12) not null primary key,
	PARENT_ID	 		number(12) not null,
	NAME 				varchar(200) not null,
	CODE				varchar(200) not null,
	SORT_ORDER			varchar(200),
	DEPT_TYPE			varchar(200),
	CREATED_DATE		timestamp with time zone default sysdate,
	MODIFIED_DATE		timestamp with time zone
);

drop sequence SEQ_DEMODEPT_ID;
create sequence SEQ_DEMODEPT_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start with 10000 cache 20 cycle;


