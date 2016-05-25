drop table poll_choices if exists;
drop table poll if exists;

create table user (
	id integer not null,
	name varchar(100),
	lastName varchar(100),
	credit integer not null,
	primary key (id)
);

create table symbol (
	id varchar(10) not null,
	primary key (id)
);

create table share (
	userid integer not null,
	symbolid varchar(10) not null,
	quantity integer not null,
	primary key (userid, symbolid),
	constraint userid_fk foreign key(userid)
	references user(userid),
	constraint symbolid_fk foreign key(symbolid)
	references symbol(symbolid)
);

create table order (
	ownerid integer not null,
	symbolid varchar(10) not null,
	price integer not null,
	quantity integer not null,
	type varchar(3) not null,
	status tinyint not null,
	command bit not null,
	constraint userid_fk foreign key(userid)
	references user(userid),
	constraint symbolid_fk foreign key(symbolid)
	references symbol(symbolid)
);

-- create table ordertype (
-- 	type varchar(3) not null,
-- 	classfile longbolb not null,
-- 	primary key (type),
-- );

insert into user values (1, 'admin', 'admin', 0);