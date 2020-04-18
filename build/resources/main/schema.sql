-- drop tables
-- drop table if exists "role";
-- drop table if exists "user_status";
-- drop table if exists "user";
-- drop table if exists "user_role";
-- drop table if exists "category";
-- drop table if exists "content_status";
-- drop table if exists "post";
-- drop table if exists "comment";

-- create sequences
create sequence category_seq_generator no maxvalue increment 1 start 15 no cycle;

-- role table
create table if not exists "role" (
    id serial not null,
    name varchar(50) not null,
    primary key (id)
);

-- create tables
-- user status
create table if not exists "user_status" (
    id serial not null,
    name varchar(50) not null,
    primary key (id)
);

-- user table
create table if not exists "user" (
    id serial not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    username varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    blocker_counter serial,
    id_status serial not null,
    primary key (id),
    constraint fk_user_status foreign key (id_status) references "user_status" (id) on delete cascade
);

-- user_role table
create table if not exists "user_role" (
    id_user serial not null,
    id_role serial not null,
    primary key (id_user, id_role),
    constraint fk_user foreign key (id_user) references "user" (id) on delete cascade,
    constraint fk_role foreign key (id_role) references "role" (id) on delete cascade
);

-- category table
create table if not exists "category" (
    id serial not null,
    name varchar(100) not null,
    primary key (id)
);

-- content status table
create table if not exists "content_status" (
    id serial not null,
    name varchar(50) not null,
    primary key (id)
);

-- post table
create table if not exists "post" (
    id serial not null,
    title varchar(100) not null,
    description varchar(4096),
    created_date timestamp not null,
    id_user serial not null,
    id_category serial not null,
    id_status serial not null,
    primary key (id),
    constraint fk_user_post foreign key (id_user) references "user" (id) on delete cascade,
    constraint fk_category_post foreign key (id_category) references "category" (id) on delete cascade,
    constraint fk_post_status foreign key (id_status) references "content_status" (id) on delete cascade
);

-- comment table
create table if not exists "comment" (
    id serial not null,
    description varchar(4096) not null,
    created_date timestamp not null,
    id_user serial not null,
    id_post serial not null,
    id_status serial not null,
    primary key (id),
    constraint fk_user_comment foreign key(id_user) references "user"(id) on delete cascade,
    constraint fk_post_comment foreign key(id_post) references "post"(id) on delete cascade,
    constraint fk_post_status foreign key (id_status) references "content_status" (id) on delete cascade
);