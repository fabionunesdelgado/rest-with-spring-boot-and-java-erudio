use treinamento_api_rest;

create table book 
(
id int(10) auto_increment primary key,
author longtext,
launch_date datetime(6) not null,
price decimal(65,2) not null,
title longtext
) engine=InnoDB default charset=latin1;