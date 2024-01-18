CREATE TABLE IF NOT EXISTS users
(
    id      serial primary key,
    name    varchar(255) not null,
    version int          not null
)