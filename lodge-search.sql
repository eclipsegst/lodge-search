--login in mysql
./mysql -u USERNAME -pPASSWORD -h HOSTNAMEORIP DATABASENAME 

--lodge
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
	traffic varchar(500),
	pickup varchar (500),
	
	adult int,
	teenager int,
	infant int,
	
	primary key (id)
) Engine=InnoDB;

show columns from lodge;

-- gallery
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

-- experience
drop table if exists experience;
create table experience (
	id bigint auto_increment,
	name varchar(100),
	description varchar(500),
	location varchar(200),
	course varchar (500),
	pickup varchar (500),
	adult int,
	teenager int,
	infant int, 
	primary key (id)
) Engine=InnoDB;

show columns from experience;

SHOW VARIABLES LIKE 'char%'
ALTER DATABASE databasename CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE lodge CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--must alter all the tables