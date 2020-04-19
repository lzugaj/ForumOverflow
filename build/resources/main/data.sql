-- insert into table role
insert into "role" values (1, 'ADMIN');
insert into "role" values (2, 'USER');

-- insert into table category
insert into "category" values (1, 'Feed');
insert into "category" values (2, 'Marketing');
insert into "category" values (3, 'Technology startup');
insert into "category" values (4, 'Information technology');
insert into "category" values (5, 'Studying');
insert into "category" values (6, 'Schools');
insert into "category" values (7, 'Smart phones');
insert into "category" values (8, 'Technology trends');
insert into "category" values (9, 'Fashion and style');
insert into "category" values (10, 'Design');
insert into "category" values (11, 'Visiting and travel');
insert into "category" values (12, 'Business');
insert into "category" values (13, 'Technology');
insert into "category" values (14, 'Sessions');

-- insert into table user status
insert into "user_status" values (1, 'ACTIVE');
insert into "user_status" values (2, 'INACTIVE');
insert into "user_status" values (3, 'BLOCKED');

-- insert into table content status
insert into "content_status" values (1, 'VALID');
insert into "content_status" values (2, 'INVALID');

-- insert into table users
insert into "user" values (100, 'Admin', 'Administrator', 'admin', 'admin@gmail.com', 'password', 0, 1);

-- insert into table user role
insert into "user_role" values (100, 1);

----------------------------------------

-- insert into "user" values (101, 'Luka', 'Zugaj', 'lzugaj', 'lzugaj@gmail.com', 'luka123', 0, 1);
-- insert into "user_role" values (101, 2);
-- insert into "post" values (1, 'Title 1', 'Desc 1', current_date, 101, 7, 1);
-- insert into "comment" values (1, 'Desc comment 1', current_date, 101, 1, 1);
-- insert into "comment" values (2, 'Desc comment 2', current_date, 101, 1, 1);
-- insert into "comment" values (3, 'Desc comment 3', current_date, 101, 1, 2);
-- insert into "comment" values (4, 'Desc comment 4', current_date, 101, 1, 1);
--
-- insert into "user" values (102, 'Dalibor', 'Torma', 'dtorma', 'dtorma@gmail.com', 'palsi', 0, 1);
-- insert into "user_role" values (102, 2);
-- insert into "post" values (2, 'Title 2', 'Desc 2', current_date, 102, 2, 1);
-- insert into "comment" values (5, 'Desc comment 1', current_date, 101, 2, 1);
-- insert into "comment" values (6, 'Desc comment 2', current_date, 101, 2, 1);
-- insert into "comment" values (7, 'Desc comment 3', current_date, 101, 2, 2);
-- insert into "comment" values (8, 'Desc comment 4', current_date, 101, 1, 1);