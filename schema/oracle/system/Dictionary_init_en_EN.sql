--NODE_TYPE: CAT|VALUE	CAT字典表分类节点，树状显示，VALUE字典表值列表，列表显示
--并且某一分类下PARENT_KEY会自动维护为上级分类的KEY

--security
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(10001,	100,		'SEC',			'',				'CAT',			'Security',			'');
					
--insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,		PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
--					values(1000101,	10001,		'SEC_USER',		'',				'CAT',		'User',					'');	
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(1000101,	10001,		'SEC_USER_TYPE','SEC',			'CAT',			'User Type',		'');
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(1000102,	10001,		'SEC_ORG_TYPE',	'SEC',			'CAT',			'Organization Type','');	
					
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010101,1000101,	'1',			'SEC_USER_TYPE','VALUE',		'SIP',				'');	
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010102,1000101,	'2',			'SEC_USER_TYPE','VALUE',		'CIP',				'');	
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010103,1000101,	'3',			'SEC_USER_TYPE','VALUE',		'VIP',				'');					
					
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010301,1000102,	'1',			'SEC_ORG_TYPE','VALUE',			'MARKET',				'');	
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010302,1000102,	'2',			'SEC_ORG_TYPE','VALUE',			'DEV',				'');	
insert into SYS_DICTIONARY(ID,		PARENT_ID,	KEY,			PARENT_KEY,		NODES_TYPE,		VALUE,				ALIAS) 
					values(100010203,1000102,	'3',			'SEC_ORG_TYPE','VALUE',			'TEST',				'');			
					
commit;					