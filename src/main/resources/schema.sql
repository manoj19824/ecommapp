drop table category;
drop table product;
drop table product_category;
create table if not exists category (
    id bigint,
    name varchar(100) not null,
    parentid bigint,
    primary key (id),
    foreign key (parentid) references category(id)
);

create table if not exists product (
    id bigint,
    name varchar(300) not null,
    price double not null,
    primary key (id)
);

create table if not exists product_category (
    productid bigint,
    categoryid bigint,
    primary key (productid, categoryid),
    foreign key (productid) references product(id),
    foreign key (categoryid) references category(id)
);

create sequence if not exists hibernate_sequence start with 100;