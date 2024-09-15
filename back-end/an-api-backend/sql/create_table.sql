-- 接口信息
create table if not exists anapibackend.`interface_info`
(
    `id` bigint not null auto_increment comment 'id' primary key,
    `name` varchar(256) not null comment '接口名称',
    `method` varchar(256) null comment '请求类型',
    `url` varchar(512) not null comment '接口地址',
    `userId` bigint not null comment '创建人id',
    `requestHeader` text null comment '请求头',
    `responseHeader` text null comment '响应头',
    `interfaceDescription` varchar(1024) null comment '接口描述',
    `interfaceStatus` tinyint default 0 not null comment '接口状态 0-关闭 1-开启',
    `isDelete` tinyint default 0 not null comment '逻辑删除',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '接口信息';

insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('郝昊焱', 'GET', 'www.justin-cormier.org', 46511332, 0, '2022-01-22 18:57:32', '2022-04-10 03:20:35');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('吴子骞', 'GET', 'www.shalon-skiles.co', 6, 0, '2022-06-01 01:21:12', '2022-03-10 02:53:19');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('顾越泽', 'GET', 'www.angelina-christiansen.com', 5892466, 0, '2022-07-28 15:56:28', '2022-09-07 00:46:43');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('曹风华', 'GET', 'www.angle-mueller.biz', 65, 0, '2022-04-10 13:29:22', '2022-10-08 22:02:57');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('萧晟睿', 'GET', 'www.annalisa-williamson.name', 17858, 0, '2022-07-04 08:09:26', '2022-08-07 09:40:56');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('黎聪健', 'GET', 'www.gordon-labadie.org', 83263356, 0, '2022-06-30 01:15:12', '2022-12-02 17:13:15');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('龙文昊', 'GET', 'www.laverne-baumbach.com', 48, 0, '2022-06-09 12:21:57', '2022-12-10 18:55:49');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('毛昊焱', 'GET', 'www.gilberto-stokes.name', 814036167, 0, '2022-02-22 05:18:13', '2022-11-05 17:43:51');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('沈志泽', 'GET', 'www.bret-fritsch.net', 20478828, 0, '2022-11-05 07:35:30', '2022-07-06 03:25:13');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('武鸿煊', 'GET', 'www.jarrett-kerluke.biz', 6, 0, '2022-10-21 12:25:27', '2022-10-15 19:22:43');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('阎致远', 'GET', 'www.benedict-kiehn.org', 8532892239, 0, '2022-08-04 20:05:56', '2022-06-10 20:24:05');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('石瑾瑜', 'GET', 'www.oretha-nolan.biz', 2, 0, '2022-03-11 23:00:43', '2022-12-30 06:51:35');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('贺致远', 'GET', 'www.tracy-reilly.co', 4677, 0, '2022-07-22 00:01:30', '2022-04-26 21:34:46');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('唐涛', 'GET', 'www.antonia-durgan.io', 8, 0, '2022-09-02 11:19:09', '2022-04-09 13:27:32');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('罗旭尧', 'GET', 'www.jimmy-mcclure.org', 569936143, 0, '2022-10-07 22:03:48', '2022-11-24 04:31:37');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('侯晓博', 'GET', 'www.odis-murazik.com', 1, 0, '2022-01-20 01:25:46', '2022-07-20 20:48:36');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('廖鸿涛', 'GET', 'www.roy-runolfsson.com', 86574663, 0, '2022-11-06 21:04:51', '2022-04-11 03:27:35');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('严潇然', 'GET', 'www.chi-gusikowski.org', 85493, 0, '2022-02-14 23:37:53', '2022-01-30 14:28:29');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('吕靖琪', 'GET', 'www.santiago-renner.co', 5472750, 0, '2022-01-18 00:51:56', '2022-07-10 03:46:30');
insert into anapibackend.`interface_info` (`name`, `method`, `url`, `userId`, `isDelete`, `createTime`, `updateTime`) values ('方彬', 'GET', 'www.landon-grady.com', 459, 0, '2022-07-22 10:10:59', '2022-11-08 10:09:52');

-- 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    accessKey    varchar(512)                       null comment 'accesskey',
    secretKey   varchar(512)                       null comment 'seceretkey',
    tags         varchar(1024)                      null comment '标签 json 列表'
)
    comment '用户';


-- 用户接口调用表
create table if not exists openapi.user_interface_invoke
(
    id           bigint auto_increment comment 'id' primary key,
    userId       bigint                             not null comment '调用人id',
    interfaceId  bigint                             not null comment '接口id',
    totalInvokes bigint   default 0                 not null comment '总调用次数',
    status       tinyint  default 0                 not null comment '调用状态（0- 正常 1- 禁止）',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除'
)
    comment '用户接口调用表';
