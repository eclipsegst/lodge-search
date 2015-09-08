--login in mysql
./mysql -u USERNAME -pPASSWORD -h HOSTNAMEORIP DATABASENAME 

--lodge
drop table if exists lodge;
create table lodge (
	id bigint auto_increment,
	name varchar(100),
	description varchar(500),
	location varchar(200),
	primary key (id)
) Engine=InnoDB;

show columns from lodge;

--gallery
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


