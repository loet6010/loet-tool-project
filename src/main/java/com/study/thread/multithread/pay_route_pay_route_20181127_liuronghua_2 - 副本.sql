--增加字段
ALTER TABLE tbl_pay_channel ADD COLUMN share_status int2 DEFAULT 0 not NULL;
ALTER TABLE tbl_pay_channel ADD COLUMN share_content VARCHAR;
COMMENT ON COLUMN tbl_pay_channel.share_status is '共享标志：0，非共享渠道；1，共享渠道；默认0';
COMMENT ON COLUMN tbl_pay_channel.share_content is '共享字段：是共享渠道且共享字段相同的多个渠道为共享渠道';


--改原C1800369709渠道数据,测试环境id=10019, 现网id=10017
update tbl_pay_channel
set code = 'helibao3',share_status = 1, share_content = 'C1800372162'
where id = 10017;

--改新增C1800372162渠道数据,测试环境id=10026, 现网id=10018
update tbl_pay_channel
set share_status = 1, share_content = 'C1800372162'
where id = 10018;

--新增速借的switch表合利宝的支付渠道：测试环境channel_id=10026,现网channel_id=10018
insert into tbl_pay_platform_switch(channel_id,platform,memo,onoff) values(10018,'sj','速借合利宝商保还款',0);

--原速借的连连还款渠道关闭:测试环境id=21, 现网id=15
update tbl_pay_platform_switch set onoff = 1 where id = 15;


--新增config表配置，测试环境channel_id=10026, 现网channel_id=10018
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10000,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10001,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10002,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10003,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10004,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10005,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10006,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10007,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10008,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10009,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10010,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10011,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10012,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10013,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10014,10018,100,1000000,10000000,10000000,0,'管理员');
insert into tbl_pay_channel_config(bank_id,channel_id,fee_rate,per_limit,day_limit,month_limit,status,create_user) values(10015,10018,100,1000000,10000000,10000000,0,'管理员');