
create Database Desktop_Banking;
use Desktop_Banking;
show tables;
create table users(
user_id int auto_increment primary key,
username varchar(50) not null unique,
password varchar(255) not null,
email varchar(100) not null unique,
phone_number varchar(15) not null,
created_at timestamp default current_timestamp
);

show tables;

create table accounts(
account_id int auto_increment primary key,
user_id int,
account_type varchar(20) not null,
balance decimal(15,2) default 0.00,
created_at timestamp default current_timestamp,
foreign key (user_id) references users(user_id) on delete cascade
);

show tables;


create table transactions(
transaction_id int auto_increment primary key,
account_id int,
amount decimal(15,2) not null,
transaction_type varchar(20) not null,
description varchar(255),
created_at timestamp default current_timestamp,
foreign key (account_id) references accounts(account_id) on delete cascade

);

show tables;

create table fund_transfers(
transfer_id int auto_increment primary key,
from_account_id int,
to_account_id int,
amount decimal(15,2) not null,
transfer_date timestamp default current_timestamp,
status varchar(20) default 'completed',
foreign key (from_account_id) references accounts(account_id),
foreign key (to_account_id) references accounts(account_id)
);

show tables;

select * from users;
select * from accounts;
select * from transactions;
select * from fund_transfers;