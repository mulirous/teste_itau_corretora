create table if not exists cestas_recomendacao
(
    id               bigserial primary key,
    nome             varchar(100)             not null,
    ativa            boolean                  not null default true,
    data_criacao     timestamp with time zone not null default now(),
    data_desativacao timestamp with time zone,

    constraint ck_nome_not_null check (nome is not null and nome <> ''),
    constraint ck_data_desativacao_after_criacao check (data_desativacao is null or data_desativacao > data_criacao)
);

create table if not exists itens_cesta
(
    id         bigserial primary key,
    cesta_id   bigint,
    ticker     varchar(10)   not null,
    percentual numeric(5, 2) not null,

    constraint fk_itens_cesta_cesta_recomendacao
        foreign key (cesta_id) references cestas_recomendacao (id),
    constraint uk_itens_cesta_cesta_id_ticker unique (cesta_id, ticker)
)
