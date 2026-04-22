create table if not exists  clientes
(
    id           bigserial primary key,
    nome         varchar(200)             not null,
    cpf          varchar(11)              not null unique,
    email        varchar(200)             not null,
    valor_mensal numeric(18, 2)           not null,
    ativo        boolean                  not null default true,
    data_adesao  timestamp with time zone not null default now(),

    constraint uk_clientes_cpf unique (cpf),
    constraint ck_clientes_cpf_formato check (cpf ~ '^[0-9]{11}$'),
    constraint ck_valor_mensal_non_negative check (valor_mensal >= 0),
    constraint ck_email_not_null check (email is not null and email <> ''),
    constraint ck_nome_not_null check (nome is not null and nome <> '')
);

create table if not exists  contas_graficas
(
    id           bigserial primary key,
    cliente_id   bigint,
    numero_conta varchar(20)              not null unique,
    tipo         varchar(50)              not null,
    data_criacao timestamp with time zone not null default now(),

    constraint fk_contas_graficas_cliente
        foreign key (cliente_id) references clientes (id),
    constraint uk_contas_graficas_numero_conta unique (numero_conta),
    constraint ck_tipo_contas_graficas check (tipo in ('MASTER', 'FILHOTE')),
    constraint ck_contas_graficas_cliente_por_tipo check (
        (tipo = 'MASTER' and cliente_id is null)
            or
        (tipo = 'FILHOTE' and cliente_id is not null)
        )
);