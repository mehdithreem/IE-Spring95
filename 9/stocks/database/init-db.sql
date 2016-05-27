drop table user if exists;
drop table user_roles if exists;
drop table deposit_user if exists;

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
	references user(username) ON DELETE CASCADE
);

-- create table deposit_user (
-- 	reqid integer not null,
-- 	username varchar(20) not null,
-- 	status varchar(10) not null,
-- 	amount integer not null,
-- 	constraint username_fk foreign key(username)
-- 	references user(username) ON DELETE CASCADE
-- )

create table symbol (
 	ownerid integer not null,
	id varchar(10) not null,
	status varchar(10),
	primary key (id)
);
	
-- create table share (
-- 	userid integer not null,
-- 	symbolid varchar(10) not null,
-- 	quantity integer not null,
-- 	primary key (userid, symbolid),
-- 	constraint userid_fk foreign key(userid)
-- 	references user(userid),
-- 	constraint symbolid_fk foreign key(symbolid)
-- 	references symbol(symbolid) on delete casecade
-- );

-- create table order (
-- orderid integer not null,
-- 	ownerid integer not null,
-- 	symbolid varchar(10) not null,
-- 	price integer not null,
-- 	quantity integer not null,
-- 	type varchar(3) not null,
-- 	command bit not null,
-- 	constraint userid_fk foreign key(userid)
-- 	references user(userid),
-- 	constraint symbolid_fk foreign key(symbolid)
-- 	references symbol(symbolid) on delete casecade
-- );

-- create table ordertype (
-- 	type varchar(3) not null,
-- 	classfile longbolb not null,
-- 	primary key (type),
-- );

insert into user values ('admin', 'admin', 'Super', 'Power', 'good@universe.com', 0);
insert into user_roles values ('admin', 'admin');
insert into user_roles values ('admin', 'regular');
