create table if not exists PERSON (
      ID serial primary key not null
    , NAME varchar(255)
    , ADDRESS_ID integer
);

create table if not exists ADDRESS (
      ID serial primary key not null
    , ADDRESS varchar(255)
);

create table if not exists SUBSCRIPTION (
      ID serial primary key not null
    , SUBSCRIPTION_PROVIDER_NAME varchar(255)
);

create table if not exists PERSON_SUBSCRIPTION (
      ID serial primary key not null
    , PERSON_ID integer
    , SUBSCRIPTION_ID integer
);

create table if not exists PERSON_FRIEND (
      ID serial primary key not null
    , PERSON_ID integer
    , FRIEND_ID integer
);
