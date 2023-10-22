
# iMauá 🥐😋🌯

Projeto semestral para o fechamento da 3ª série do curso de Engenharia de Computação do Instituto Mauá de Tecnologia da disciplina Linguagens de Programação I.


## Objetivos 🥸
O objetivo desse projeto era criar um programa em Java que fizesse conexão com banco de dados em MySQL com tema livre.

## Tema escolhido ✏️📋
O tema escolhido foi desenvolver um sistema inspirado no IFood, que pudesse realizar e retirar pedidos dos restaurantes do IMT.

## Implementação 🧑‍🔬🧪🔬
O sistema se baseia em um banco de dados de usuários e pedidos feitos. Cada usuário tem um login único e cada pedido de usuário está atrelado ao mesmo. A interface foi criado utilizando a próprio biblioteca do Java para criação de janelas.

## Diagrama para organização do banco de dados 😁
![image](https://github.com/enzosakamoto/imaua/assets/98707474/55d1e551-72c6-463a-bd86-664f9e659d5c)

## Banco de dados 🪑🎲

Para rodar esse projeto, é necessário criar um banco de dados específico em MySQL. Os passos abaixo foram executados no Prompt do MySQL Command Line.

###### 1º: Criar a database

    create database imaua;

###### 2º: Acessar a database

    use imaua;

###### 3º: Criar tabela de clientes

    create table clients(id varchar(36) not null primary key, name varchar(100) not null, password varchar(100) not null, credits decimal(5, 2) unsigned not null);

###### 4º: Criar tabela de pedidos

    create table orders(id varchar(36) not null primary key, id_client varchar(36) not null, date varchar(18) not null, id_restaurant smallint unsigned not null, meal varchar(100) not null, isdone boolean not null, foreign key (id_client) references clients (id) on delete cascade on update cascade);

## Agradecimentos 🙏
Obrigado aos professores Igor Cataneo Silveira e Robson Calvetti pelo lecionamento da disciplina e pelo apoio nas aulas.


## Autores 🤯🤡🤫

- [Enzo Sakamoto](https://www.github.com/enzosakamoto)
- [Flavio Murata](https://github.com/flaviomurata)
- [Heitor Mariano](https://github.com/HeitorMarian0)

