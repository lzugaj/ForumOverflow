-- role table
create table "role" (
    id bigint not null,
    name varchar(50) not null,
    primary key (id)
);

-- user table
create table "user" (
    id bigint not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    primary key (id)
);

-- user_role table
create table "user_role" (
    id_user bigserial not null,
    id_role bigserial not null,
    primary key (id_user, id_role),
    constraint fk_user foreign key (id_user) references "user" (id),
    constraint fk_role foreign key (id_role) references "role" (id)
);

