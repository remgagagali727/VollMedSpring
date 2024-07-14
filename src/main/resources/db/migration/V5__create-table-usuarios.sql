create table usuarios(
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    login varchar(300),
    clave varchar(300),

    primary key(id)
);