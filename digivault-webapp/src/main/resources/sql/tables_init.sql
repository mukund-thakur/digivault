create table if not exists `digivault_user`
(
    id      BIGINT primary key not null auto_increment,
    created_at DATETIME,
    updated_at DATETIME,
    name    varchar(64) not null,
    dob     varchar(64) not null,
    gender  varchar(64) not null,
    contact_num varchar(64) not null,
    active  int not null,
    email   varchar(64) default null,
    password varchar(64) not null,
    pan     varchar(64) default null,
    aadhar  varchar(64) default null,
    gcm_id  varchar(256) not null
) ENGINE = InnoDb;