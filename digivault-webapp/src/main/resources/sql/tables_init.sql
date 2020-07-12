create table if not exists `digivault_user`
(
    id      bigint primary key not null auto_increment,
    created_at DATETIME,
    updated_at DATETIME,
    name    varchar(64) not null,
    dob     varchar(64) default null,
    gender  varchar(64) not null,
    contact_num varchar(64) default null,
    active  int not null,
    email   varchar(64) default null,
    email_verified int not null,
    password varchar(64) not null,
    pan     varchar(64) default null,
    aadhar  varchar(64) default null,
    gcm_id  varchar(256) not null
) ENGINE = InnoDb;

create table if not exists `digivault_asset`
(
    id      bigint primary key not null auto_increment,
    created_at DATETIME,
    updated_at DATETIME,
    type    varchar(64) not null,
    service_provider     varchar(64) not null,
    account_id  varchar(64) not null,
    account_holder_name varchar(64) not null,
    active  int not null,
    amount_invested   bigint default null,
    is_nominee_reg int not null,
    nominee_name varchar(64) default null,
    notes     varchar(1024) default null,
    document_id  varchar(256) default null,
    user_id bigint not null,
    INDEX user_assets (user_id),
    FOREIGN KEY (`user_id`) REFERENCES `digivault_user` (`id`)
) ENGINE = InnoDb;

create table if not exists `digivault_loggedin_user`
(
    user_id bigint not null,
    token varchar(1024) not null,
    INDEX digivault_loggedin_user (user_id),
    FOREIGN KEY (`user_id`) REFERENCES `digivault_user` (`id`)
) ENGINE = InnoDb;