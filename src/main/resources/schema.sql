
-- role table
create table "role" (
    id bigserial not null,
    name varchar(50) not null,
    primary key (id)
);

-- user table
create table "user" (
    id bigserial not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    username varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    primary key (id)
);

-- user_role table
create table if not exists "user_role" (
    id_user bigserial not null,
    id_role bigserial not null,
    primary key (id_user, id_role),
    constraint fk_user foreign key (id_user) references "user" (id),
    constraint fk_role foreign key (id_role) references "role" (id)
);

-- category table
create table "category" (
    id bigserial not null,
    name varchar(100) not null,
    primary key (id)
);

-- post table
create table "post" (
    id bigserial not null,
    title varchar(100) not null,
    description varchar(4096),
    created_date timestamp not null,
    id_user bigserial not null,
    id_category bigserial not null,
    primary key (id),
    foreign key (id_user) references "user" (id) on delete cascade,
    foreign key (id_category) references "category" (id) on delete cascade
);

-- comment table
create table "comment" (
    id bigserial not null,
    description varchar(4096) not null,
    created_date timestamp not null,
    id_user bigserial not null,
    id_post bigserial not null,
    primary key(id),
    foreign key(id_user) references "user"(id) on delete cascade,
    foreign key(id_post) references "post"(id) on delete cascade
);

