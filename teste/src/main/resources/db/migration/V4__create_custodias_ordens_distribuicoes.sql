create table if not exists custodias
(
    id                      bigserial primary key,
    conta_grafica_id        bigint,
    ticker                  varchar(10)              not null,
    quantidade              int                      not null,
    preco_medio             numeric(18, 2)           not null,
    data_ultima_atualizacao timestamp with time zone not null default now(),

    constraint fk_custodias_conta_grafica
        foreign key (conta_grafica_id) references contas_graficas (id),
    constraint uk_custodias_conta_grafica_ticker unique (conta_grafica_id, ticker),
    constraint ck_quantidade_non_negative check (quantidade >= 0), -- Sem Posição vendida
    constraint ck_preco_medio_non_negative check (preco_medio >= 0)
);

create table if not exists ordens_compras
(
    id              bigserial primary key,
    conta_master_id bigint,
    ticker          varchar(10)              not null,
    quantidade      int                      not null,
    preco_unitario  numeric(18, 2)           not null,
    tipo_mercado    varchar(20)              not null,
    data_execucao   timestamp with time zone not null default now(),

    constraint fk_ordens_compras_conta_master
        foreign key (conta_master_id) references contas_graficas (id),
    constraint ck_tipo_mercado check (tipo_mercado in ('LOTE', 'FRACIONARIO'))
);

create table if not exists distribuicoes
(
    id                  bigserial primary key,
    ordem_compra_id     bigint,
    custodia_filhote_id bigint,
    ticker              varchar(10)              not null,
    quantidade          int                      not null,
    preco_unitario      numeric(18, 2)           not null,
    data_distribuicao   timestamp with time zone not null default now(),

    constraint fk_distribuicoes_ordem_compra
        foreign key (ordem_compra_id) references ordens_compras (id),
    constraint fk_distribuicoes_custodia_filhote
        foreign key (custodia_filhote_id) references custodias (id),
    constraint ck_quantidade_distribuicao_positive check (quantidade > 0),
    constraint ck_preco_unitario_non_negative check (preco_unitario >= 0)
);