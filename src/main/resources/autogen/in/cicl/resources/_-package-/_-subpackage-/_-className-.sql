drop table if exists ${tableName};
create table ${tableName}
(
	ID 		bigint not null primary key,
	<#list col as r>
	${r.column}			${r.type.db},
	</#list>
	CREATED_DATE		timestamp with time zone default now(),
	MODIFIED_DATE		timestamp with time zone
);

drop sequence if exists SEQ_${tableName}_ID;
create sequence SEQ_${tableName}_ID
increment by 1 minvalue 10000 maxvalue 99999999999999999 start 10000 cache 20 cycle;

