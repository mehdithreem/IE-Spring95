drop table user_roles if exists;
-- drop table deposit_user if exists;
drop table share if exists;
drop table orders if exists;
drop table transaction if exists;
drop table symbol if exists;
drop table user if exists;

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
--  	reqid integer not null,
--  	username varchar(20) not null,
--  	status varchar(10) not null,
--  	amount integer not null,
--  	constraint username_fk foreign key(username)
--  	references user(username) ON DELETE CASCADE
-- );

create table symbol (
 	ownerid integer not null,
	id varchar(10) not null,
	status varchar(10),
	primary key (id)
);
	
create table share (
 	userid varchar(20) not null,
 	symbolid varchar(10) not null,
 	quantity integer not null,
 	primary key (userid, symbolid),
 	constraint share_userid_fk foreign key(userid)
 	references user(username) ON DELETE CASCADE,
 	constraint share_symbolid_fk foreign key(symbolid)
 	references symbol(id) ON DELETE CASCADE
);

create table orders (
 	orderid integer not null,
 	ownerid varchar(20) not null,
 	symbolid varchar(10) not null,
 	price integer not null,
 	quantity integer not null,
 	command varchar(10) not null,
 	status varchar(10) not null,
 	primary key (orderid),
 	constraint orders_userid_fk foreign key(ownerid)
 	references user(username) ON DELETE CASCADE,
 	constraint orders_symbolid_fk foreign key(symbolid)
 	references symbol(id) ON DELETE CASCADE
);

create table transaction (
	transid integer not null,
	buyerid varchar(10) not null,
	sellerid varchar(10) not null,
	symbolid varchar(10) not null,
	price integer not null,
 	quantity integer not null,
 	time varchar(50) not null,
 	primary key (transid)
);

insert into user values ('admin', 'admin', 'Super', 'Power', 'good@universe.com', 0);
insert into user_roles values ('admin', 'admin');
insert into user_roles values ('admin', 'member');
