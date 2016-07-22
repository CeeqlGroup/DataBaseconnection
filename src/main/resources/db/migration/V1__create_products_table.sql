create table products (
  id bigint auto_increment primary key,
  code varchar(10) NOT NULL,
  name varchar(50) NOT NULL,
  price decimal(7,2) NOT NULL,
  description varchar(255) NOT NULL, 
  chargeCard  varchar(255) NOT NULL
 );