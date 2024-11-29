create table if not exists clients
(
    uuid                        uuid not null primary key,
    created_at                  timestamp,
    last_modified_at            timestamp,
    version                     bigint,
    shared_key                  varchar(255) not null unique,
    name                        varchar(255) not null,
    type                        varchar(255) not null,
    email                       varchar(255),
    phone                       varchar(255)
);