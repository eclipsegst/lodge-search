--login in mysql
./mysql -u USERNAME -pPASSWORD -h HOSTNAMEORIP DATABASENAME 

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
) Engine=InnoDB;
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
	traffic varchar(500),
	pickup varchar (500),
	
	adult int,
	teenager int,
	infant int,
	
	primary key (id)
) Engine=InnoDB;

show columns from lodge;

drop table if exists experience;
create table experience (
	id bigint auto_increment,
	landlord_id  bigint,
	
	name varchar(100),
	description varchar(500),
	location varchar(200),
	
	course varchar (500),
	pickup varchar (500),
	category varchar(100),
	
	adult int,
	teenager int,
	infant int, 
	primary key (id)
) Engine=InnoDB;

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
) Engine=InnoDB;
show columns from gallery;



SHOW VARIABLES LIKE 'char%';
ALTER DATABASE lodge_search CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE lodge CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE landlord CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE experience CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE gallery CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;