# Registros de Decisões Arquiteturais (ADRs)

Este documento registra as decisões arquiteturais tomadas durante o desenvolvimento do projeto. Cada decisão é
documentada com um título, data, contexto, decisão e consequências. O objetivo é fornecer um histórico claro das
escolhas arquiteturais e facilitar a compreensão das razões por trás de cada decisão.

## ADR 001: Escolha do Framework de Desenvolvimento

- **Data**: 2026-04-21
- **Contexto**: Para o desenvolvimento do projeto foi imposto nos requisitos o uso de .NET Core junto com o MySQL.
  Diante disso, por ser um projeto sem fins de divulgação para o teste técnico, foi tomada a liberdade da escolha de
  frameworks e bibliotecas para o desenvolvimento diferentes das originalmente requeridas. Para a isualização não houve
  especificação alguma do framework a ser utilizado, o que nos deu liberdade para escolher o que melhor se encaixasse no
  projeto.
- **Decisão**: Optamos por utilizar o ecossistema Spring Boot para o desenvolvimento do projeto, utilizando Java como
  linguagem de programação e PostgreSQL como banco de dados. Essa escolha foi motivada pela familiaridade com
  o Spring Boot, que oferece uma ampla gama de recursos e uma comunidade ativa, facilitando o desenvolvimento e a
  manutenção do projeto. Além disso, para o desenvolvimento de visualizações foi optado pelo uso de Thymeleaf. Seguimos
  com o uso do Apache Kafka para mensageria.
- **Consequências**: A escolha do Spring Boot e do PostgreSQL permite desenvolver o projeto de forma eficiente, de
  maneira igual ao uso do .NET Core, aproveitando as vantagens oferecidas por essas tecnologias. No entanto, é
  importante documentar essa decisão para garantir a transparência e facilitar futuras manutenções ou mudanças na
  arquitetura do projeto.
- **Status**: Aceita


