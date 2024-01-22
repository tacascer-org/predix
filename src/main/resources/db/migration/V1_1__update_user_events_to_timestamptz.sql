alter table user_events
    alter column created_at type timestamptz;
create index user_events_created_at_idx on user_events (created_at);

alter table user_events
    alter column last_modified_date type timestamptz;