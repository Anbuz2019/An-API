-- 接口信息
create table interface_info
(
    id                   bigint auto_increment comment 'id'
        primary key,
    name                 varchar(256)                       not null comment '接口名称',
    method               varchar(256)                       null comment '请求类型',
    url                  varchar(512)                       not null comment '接口地址',
    userId               bigint                             not null comment '创建人id',
    requestHeader        text                               null comment '请求头',
    responseHeader       text                               null comment '响应头',
    interfaceDescription varchar(1024)                      null comment '接口描述',
    interfaceStatus      tinyint  default 0                 not null comment '接口状态 0-关闭 1-开启',
    costScore            int                                not null comment '消耗积分',
    requestParams        json                               null comment '请求参数',
    responseParams       json                               null comment '响应参数',
    requestExample       json                               null comment '请求示例',
    isDelete             tinyint  default 0                 not null comment '逻辑删除',
    createTime           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '接口信息';

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
