drop table user if exists;
drop table user_roles if exists;

create table user (
	username varchar(20) not null,
	password varchar(100) not null,
	name varchar(100),
	lastName varchar(100),
	email varchar(100),
	credit integer not null,
	primary key (username)
);

create table user_roles (
	username varchar(20) not null,
	role varchar(20),
	constraint username_fk foreign key(username)
	references user(username)
);

-- create table symbol (
-- 	id varchar(10) not null,
-- 	primary key (id)
-- );

-- create table share (
-- 	userid integer not null,
-- 	symbolid varchar(10) not null,
-- 	quantity integer not null,
-- 	primary key (userid, symbolid),
-- 	constraint userid_fk foreign key(userid)
-- 	references user(userid),
-- 	constraint symbolid_fk foreign key(symbolid)
-- 	references symbol(symbolid)
-- );

-- create table order (
-- 	ownerid integer not null,
-- 	symbolid varchar(10) not null,
-- 	price integer not null,
-- 	quantity integer not null,
-- 	type varchar(3) not null,
-- 	status tinyint not null,
-- 	command bit not null,
-- 	constraint userid_fk foreign key(userid)
-- 	references user(userid),
-- 	constraint symbolid_fk foreign key(symbolid)
-- 	references symbol(symbolid)
-- );

-- create table ordertype (
-- 	type varchar(3) not null,
-- 	classfile longbolb not null,
-- 	primary key (type),
-- );

insert into user values ('admin', 'admin', 'Super', 'Power', 'god@universe.com', 0);
insert into user_roles values ('admin', 'admin');
insert into user_roles values ('admin', 'regular');
