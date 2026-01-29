drop table if exists products;
create table products
(
    id         bigint auto_increment primary key,
    name       varchar(100) not null,
    category   varchar(50)  not null,
    price      int          not null default 0,
    stock      int          not null default 0,
    description longtext    null,
    is_deleted boolean      not null default false,
    created_at datetime     not null default current_timestamp,
    updated_at datetime     not null on update current_timestamp
);

alter table products add column description longtext;

commit;