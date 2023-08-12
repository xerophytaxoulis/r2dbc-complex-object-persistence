create table if not exists PERSON (
      ID serial primary key not null
    , NAME varchar(255)
    , ADDRESS_ID integer
    , FRIENDS_ID integer
);

create table if not exists ADDRESS (
      ID serial primary key not null
    , ADDRESS varchar(255)
);
