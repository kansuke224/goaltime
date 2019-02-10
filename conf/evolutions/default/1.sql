# project_properties schema

# --- !Ups

create table project_properties (
project_id TEXT not null,
twitter_id bigint not null,
project_name TEXT not null,
week_goal bigint not null,
week_sum bigint not null,
created_time bigint not null,
updated_time TEXT not null,
start_time bigint not null,
last_week_sum bigint not null,
last_week_goal bigint not null,
user_name TEXT not null,
comment_user text not null,
comment_content text not null,
archived_time bigint not null
);

# --- !Downs

drop table project_properties;