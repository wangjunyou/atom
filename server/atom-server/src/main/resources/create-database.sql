create table t_user
(
    `id`            int not null,
    `user_name`     varchar(64),
    `user_password` varchar(64),
    `phone`         varchar(11),
    `email`         varchar(64),
    `create_time`   datetime,
    `update_time`   datetime,
    primary key (`id`)
)

create table t_role
(
    `id`          int not null,
    `parent_id`   int,
    `role_name`   varchar(64),
    `description` varchar(64),
    `create_time` datetime,
    `update_time` datetime,
    primary key (`id`)
)

create table t_user_role
(
    `id`      int not null,
    `user_id` int,
    `role_id` int,
    primary key (`id`),
    UNIQUE KEY `un_user_role_inx` (`user_id`,`role_id`)
)

create table t_menu
(
    `id`             int not null,
    `parent_id`      int,
    `menu_name`      varchar(11),
    `menu_path`      varchar(11),
    `menu_component` varchar(11),
    `menu_icon`      varchar(11),
    `display`        tinyint,
    `order_num`      double(20, 0
) ,
	`create_time` datetime,
	`update_time` datetime,
	primary key (`id`)
)

create table t_role_menu
(
    `id`      int not null,
    `role_id` int,
    `menu_id` int,
    primary key (`id`),
    unique key `un_role_menu_inx` (`role_id`,`menu_id`)
)



