--login in mysql
./mysql -u USERNAME -pPASSWORD -h HOSTNAMEORIP DATABASENAME 
create database lodge_search CHARACTER SET utf8 COLLATE utf8_general_ci;
create database lodge_search;

drop table if exists landlord;
create table landlord (
	id bigint auto_increment,
	name varchar(50),
	description varchar(500),
	location varchar(200),
	email varchar(200),
	lodge_number int,
	experience_number int,
	primary key (id)
) Engine=InnoDB CHARACTER SET='utf8' COLLATE 'utf8_unicode_ci';
show columns from landlord;

drop table if exists lodge;
create table lodge (
	id bigint auto_increment,
	landlord_id  bigint,
	
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
	latitude varchar(20),
	longitude varchar(20),
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

SHOW VARIABLES LIKE 'char%';
ALTER DATABASE lodge_search CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE lodge CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE landlord CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE experience CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE gallery CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;