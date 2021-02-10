create table pending_verification
(
    id           bigint auto_increment primary key,
    expiration   datetime(6) not null,
    otp          varchar(8)  not null,
    phone_number varchar(30) not null
);

create table user
(
    id           bigint auto_increment primary key,
    phone_number varchar(30)  not null,
    name         varchar(100) not null,
    online       boolean  default false,
    avatar       longtext default null,
    constraint unique_phone_number unique (phone_number)
);