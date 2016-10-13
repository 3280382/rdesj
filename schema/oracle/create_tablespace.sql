spool log/createTablespace.log;
PROMPT ==============================================================
PROMPT *********** CREATE TABLESPACE ******
PROMPT ==============================================================


set concat on
set concat .
set verify off
set termout off
set termout on

PROMPT /*========================NOTES=================================*/
PROMPT /* Table space[CICL_PATH]:             for  tablespace &CICL(need 10G)               PATH      */
PROMPT /* Table space[CICL_IDX_PATH]:   	     for  tablespace &CICL_IDX(need 10G)           PATH      */
PROMPT /* TEMP Table space[CICL_TEMP_PATH]:   for  tablespace &CICL_TEMP(need 10G)	          PATH      */ 
PROMPT /*==============================================================*/

PROMPT ==============================================================
PROMPT 'creating tablespace &CICL......'
PROMPT ==============================================================
CREATE TABLESPACE &CICL DATAFILE 
'&CICL_PATH/&CICL._01.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M,
'&CICL_PATH/&CICL._02.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M
extent management local autoallocate; 

PROMPT ==============================================================
PROMPT 'creating tablespace &CICL_IDX......'
PROMPT ==============================================================
CREATE TABLESPACE &CICL_IDX DATAFILE 
'&CICL_IDX_PATH/&CICL_IDX._01.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M,
'&CICL_IDX_PATH/&CICL_IDX._02.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M
extent management local autoallocate; 



PROMPT ==============================================================
PROMPT 'creating tablespace &CICL_TEMP......'
PROMPT ==============================================================
CREATE TEMPORARY TABLESPACE &CICL_TEMP   TEMPFILE 
'&CICL_TEMP_PATH/&CICL_TEMP._01.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M,
'&CICL_TEMP_PATH/&CICL_TEMP._02.dbf' SIZE 10M REUSE autoextend on next 10M  maxsize 10000M
EXTENT MANAGEMENT LOCAL UNIFORM SIZE 5M  ; 


spool off;