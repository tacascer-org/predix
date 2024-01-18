CREATE TABLE IF NOT EXISTS users
(
    id      serial primary key,
    name    varchar(255) not null,
    version int          not null
);

create table if not exists user_events
(
    id                 serial primary key,
    title              varchar(255) not null,
    description        text         not null,
    created_by         int          not null references users (id),
    created_at         timestamp    not null default now(),
    last_modified_date timestamp    not null default now(),
    version            int          not null
);
