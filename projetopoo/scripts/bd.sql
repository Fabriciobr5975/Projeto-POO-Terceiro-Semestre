create database if not exists projetopoo;

use projetopoo;

select * from marca;

create table if not exists marca(
	id integer primary key auto_increment,
    nome varchar(100) not null,
    logo mediumblob 
);