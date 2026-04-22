create table if not exists eventos_ir
(
    id              bigserial primary key,
    cliente_id      bigint,
    tipo            varchar(40)              not null,
    valor_base      numeric(18, 2)           not null,
    valor_ir        numeric(18, 2)           not null,
    publicado_kafka boolean                  not null default false,
    data_evento     timestamp with time zone not null default now(),

    constraint fk_eventos_ir_cliente
        foreign key (cliente_id) references clientes (id),
    constraint ck_tipo_evento_ir check (tipo in ('DEDO_DURO', 'IR_VENDA'))
);

create table if not exists rebalanceamentos
(
    id                  bigserial primary key,
    cliente_id          bigint,
    tipo                varchar(40)              not null,
    ticker_vendido      varchar(10),
    ticker_comprado     varchar(10),
    valor_venda         numeric(18, 2),
    data_rebalanceamento timestamp with time zone not null default now(),

    constraint fk_rebalanceamentos_cliente
        foreign key (cliente_id) references clientes (id),
    constraint ck_tipo_rebalanceamento check (tipo in ('MUDANCA_CESTA', 'DESVIO'))
);