drop database if exists bitshow;
create database bitshow;
use bitshow;

create table produto(
id Long not null primary key,
nome varchar(100),
marca varchar(50),
preco double
);