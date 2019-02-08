# picture_properties schema

# --- !Ups

create table project_properties(
project_id varchar(255) not null,
twitter_id bigint(20) not null,
project_name varchar(255) not null,
week_goal bigint(20) not null,
week_sum bigint(20) not null,
created_time bigint(20) not null,
updated_time varchar(255) not null,
start_time bigint(20) not null,
last_week_sum bigint(20) not null,
last_week_goal bigint(20) not null,
user_name varchar(255) not null,
comment_user text not null,
comment_content text not null,
archived_time bigint(20) not null,
index(twitter_id)
) engine=innodb charset=utf8mb4;

# --- !Downs

drop table project_properties;