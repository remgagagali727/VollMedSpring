create table pacientes(
    id bigint not null GENERATED ALWAYS AS IDENTITY,
    activo BOOLEAN,
    calle VARCHAR(100),
    ciudad VARCHAR(100),
    complemento VARCHAR(10),
    distrito VARCHAR(100),
    numero VARCHAR(10),
    documento_identidad VARCHAR(20),
    email VARCHAR(100),
    nombre VARCHAR(100),
    telefono VARCHAR(20),

    primary key(id)
);