drop database if exists lodge_search;
create database lodge_search CHARACTER SET utf8 COLLATE utf8_general_ci;
use lodge_search;

drop table if exists landlord;
create table landlord (
	id bigint auto_increment,
	name varchar(50),
	description varchar(500),
	location varchar(200),
	email varchar(200),
	lodgenumber int,
	experiencenumber int,
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';
show columns from landlord;

drop table if exists lodge;
create table lodge (
	id bigint auto_increment,
	
	name varchar(100),
	description varchar(500),
	location varchar(200),
	
	price varchar(500),
	food varchar(500),
	capacity varchar(500),
	equipment varchar(500),
	amenity varchar(500),
	traffic varchar(500),
	pickup varchar (500),
	
	adult int,
	teenager int,
	infant int,
	
	latitude decimal(10, 8),
	longitude decimal(11, 8),
	
	fk bigint,
	adultprice int,
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

show columns from lodge;

drop table if exists experience;
create table experience (
	id bigint auto_increment,
	
	name varchar(100),
	description varchar(500),
	location varchar(200),
	
	course varchar (500),
	pickup varchar (500),
	category varchar(100),
	
	adult int,
	teenager int,
	infant int, 
	
	latitude decimal(10, 8),
	longitude decimal(11, 8),
	
	fk  bigint,
	adultprice int,
	
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

show columns from experience;

drop table if exists gallery;
create table gallery (
	id bigint auto_increment,
	fk  bigint,
	category varchar(50),
	title varchar(100),
	active boolean not null default 0,
	image longblob not null,
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';
show columns from gallery;

drop table if exists food;
create table food (
	id bigint auto_increment,
	fk bigint,
	category varchar(50),
	title varchar(100),
	adult int,
	teenager int,
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

drop table if exists location;
create table location (
	id bigint auto_increment,
	name varchar(100),
	latitude decimal(10, 8),
	longitude decimal(11, 8),
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

drop table if exists calendar;
create table calendar (
	id bigint auto_increment,
    fk bigint,
    category varchar(50),
	closeddate date,
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';


drop table if exists cart;
create table cart (
	id bigint auto_increment,
	shoppingid bigint,
	fk bigint,
	category varchar(50),
	foodfk bigint,
	checkin date,
	checkout date,
	
	adult int,
	teenager int,
	infant int, 
	
	payment numeric(15,2),
	
	created datetime,
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

drop table if exists shopping;
create table shopping (
	id bigint auto_increment,
	userid bigint,
	payment numeric(15,2),
	valid boolean,
	created datetime,
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';

drop table if exists user;
create table user (
	id bigint auto_increment,
	email varchar(200),
	password_hash varchar(500),
	role varchar(10),
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';
insert into user (email, password_hash, role)
values ('demo@localhost', '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'ADMIN');

drop table if exists userinfo;
create table userinfo (
	id bigint auto_increment,
	userid bigint,
	name varchar(200),
	phone varchar(200),
	address varchar(200),
	zipcode varchar(200),
	email varchar(200),
	primary key(id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';



SHOW VARIABLES LIKE 'char%';
ALTER DATABASE lodge_search CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE lodge CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE landlord CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE experience CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE gallery CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;