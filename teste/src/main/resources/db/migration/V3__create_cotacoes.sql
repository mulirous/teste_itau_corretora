create table if not exists cotacoes
(
    id               bigserial primary key ,
    data_pregao      date           not null,
    ticker           varchar(10)    not null,
    preco_abertura   numeric(18, 2) not null,
    preco_fechamento numeric(18, 2) not null,
    preco_maximo     numeric(18, 2) not null,
    preco_minimo     numeric(18, 2) not null,

    constraint uk_cotacoes_data_pregao_ticker unique (data_pregao, ticker),
    constraint uk_cotacoes_preco_maximo_minimo check (preco_maximo >= preco_minimo)
);