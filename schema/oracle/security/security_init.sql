insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(10001,100,'权限管理','ROLE_SEC');
--security Authority
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000104,10001,'授权管理','ROLE_SEC_AUT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010401,1000104,'查看','ROLE_SEC_AUT_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010402,1000104,'新增','ROLE_SEC_AUT_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010403,1000104,'修改','ROLE_SEC_AUT_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010404,1000104,'删除','ROLE_SEC_AUT_REMOVE');
--security Organization
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000102,10001,'部门管理','ROLE_SEC_ORG');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010201,1000102,'查看','ROLE_SEC_ORG_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010202,1000102,'新增','ROLE_SEC_ORG_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010203,1000102,'修改','ROLE_SEC_ORG_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010204,1000102,'删除','ROLE_SEC_ORG_REMOVE');
--security Role
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000103,10001,'角色管理','ROLE_SEC_ROLE');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010301,1000103,'查看','ROLE_SEC_ROLE_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010302,1000103,'新增','ROLE_SEC_ROLE_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010303,1000103,'修改','ROLE_SEC_ROLE_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010304,1000103,'删除','ROLE_SEC_ROLE_REMOVE');
--security SysUser
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000101,10001,'用户管理','ROLE_SEC_USER');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010101,1000101,'查看','ROLE_SEC_USER_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010102,1000101,'新增','ROLE_SEC_USER_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010103,1000101,'修改','ROLE_SEC_USER_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010104,1000101,'删除','ROLE_SEC_USER_REMOVE');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100010105,1000101,'重置密码','ROLE_SEC_USER_RESETPWD');

insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(10002,100,'审计管理','ROLE_AUD');
--audit AuditLog
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000201,10002,'审计日志','ROLE_AUD_LOG');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100020101,1000201,'查看','ROLE_AUD_LOG_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100020102,1000201,'新增','ROLE_AUD_LOG_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100020103,1000201,'修改','ROLE_AUD_LOG_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100020104,1000201,'删除','ROLE_AUD_LOG_REMOVE');

--system
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(10003,100,'系统管理','ROLE_SYS');
--system dictionary
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(1000301,10003,'数据字典','ROLE_SYS_DIC');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100030101,1000301,'查看','ROLE_SYS_DIC_QUERY');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100030102,1000301,'新增','ROLE_SYS_DIC_ADD');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100030103,1000301,'修改','ROLE_SYS_DIC_EDIT');
insert into AUTHORITY(AUTHORITY_ID,PARENT_ID,NAME,CODE) values(100030104,1000301,'删除','ROLE_SYS_DIC_REMOVE');

insert into SYS_USER(SYS_USER_ID,USERNAME,LOGIN_NAME,PASSWORD) values(1,'admin','admin','21232f297a57a5a743894a0e4a801fc3');
insert into ROLE(ROLE_ID,NAME,CODE) values(1,'admin','admin');
insert into SYS_USER_ROLE(ROLE_ID,SYS_USER_ID) values(1,1);
insert into ROLE_AUTHORITY(ROLE_ID,AUTHORITY_ID) select 1 ROLE_ID, AUTHORITY_ID from AUTHORITY;

insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(1,0,'从化3期','从化3期');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(2,1,'SIP','SIP');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(3,1,'CIP','CIP');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(4,1,'CEO','CEO');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(41,4,'CEO1','CEO1');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(42,4,'CEO2','CEO2');
insert into ORGANIZATION(ORGANIZATION_ID,PARENT_ID,NAME,CODE) values(43,4,'CEO3','CEO3');
commit;