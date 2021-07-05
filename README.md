
 # Camel Mybatis with SQLServer
* Before running please connect to the database and create these tables:
1. `create table People (
 id integer primary key,
 name varchar(200) not null,
 age integer not null);`
 
 2. `create table Examples (
 id integer primary key,
 name varchar(200) not null,
 description varchar(500) not null);`
 
 3. ` insert into examples values ( 1, 'camel example', 'A short camel example');`
 
 ## After this, clone the project
 git clone https://github.com/avi5kdonrh/camel-bp-mybatis
 ## Now
 - cd camel-bp-mybatis
 - mvn clean install camel:run
