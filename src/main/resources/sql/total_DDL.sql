drop table if exists products;
create table products
(
    id          bigint auto_increment primary key,
    name        varchar(100) not null,
    category    varchar(50)  not null,
    price       int          not null default 0,
    stock       int          not null default 0,
    description longtext     null,
    is_existed  boolean               default true,
    created_at  datetime     not null default current_timestamp,
    updated_at  datetime     not null on update current_timestamp
);

alter table products
    add unique key uk_name_is_exist (name, is_existed);

drop table if exists orders;
create table orders
(
    id          bigint auto_increment primary key,
    quantity    int    not null default 0,
    order_date  datetime   not null default current_timestamp,
    product_id  bigint not null
);

alter table orders
    add constraint fk_product_id
        foreign key (product_id) references products (id);

commit;