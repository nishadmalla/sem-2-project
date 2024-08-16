create database Jump;
use Jump;

create table Newplayer(
player_id int auto_increment not null Primary key,
first_name varchar(255) ,
last_name varchar(255) ,
email varchar(255),
username varchar(255),
player_password varchar(255) check (Length (player_password)>=8),
confirm_password varchar(255) 
);
select * from Newplayer;


create table Player(
login_id int auto_increment Primary key,
player_id int,
username varchar (255) not null,
player_password varchar(50) not null ,
Foreign key(player_id) references Newplayer(player_id)
);
  

select * from Player;



