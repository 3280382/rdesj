spool log/create_user.log

/*==============================================================*/
/* DBMS Version:      ORACLE Version 10.2.0.4                    */
/* Created:            2010-03-01 15:07:36                       */
/*==============================================================*/

PROMPT ==============================================================
PROMPT ******DATABASE INSTALLATION GUIDE USER******
PROMPT ****create user and set default tablespace*****************
PROMPT ==============================================================

set concat on
set concat .
set verify off
set termout off
set termout on



PROMPT ==============================================================
PROMPT 'creating CICL user  ......'
PROMPT ==============================================================

 
CREATE USER  &user_name   IDENTIFIED BY  &user_password
 DEFAULT TABLESPACE &CICL
 TEMPORARY TABLESPACE &CICL_TEMP
 PROFILE DEFAULT
 QUOTA  UNLIMITED ON &CICL;
 
GRANT  CONNECT         TO  &user_name   WITH ADMIN OPTION;
GRANT  resource        TO  &user_name  ; 

grant  debug connect session to  &user_name ;

grant create view to  &user_name;
grant create materialized view to &user_name;
grant create job to &user_name;
grant create trigger to &user_name;
grant create sequence to &user_name;
grant EXECUTE ON DBMS_LOCK TO &user_name;

ALTER USER &user_name  TEMPORARY TABLESPACE &CICL_TEMP;
ALTER USER &user_name  QUOTA UNLIMITED ON   &CICL;
ALTER USER &user_name  QUOTA UNLIMITED ON   &CICL_IDX;

conn &user_name/&user_password

spool off;