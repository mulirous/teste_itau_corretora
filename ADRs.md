# Registros de Decisões Arquiteturais (ADRs)

Este documento registra as decisões arquiteturais tomadas durante o desenvolvimento do projeto. Cada decisão é
documentada com um título, data, contexto, decisão e consequências. O objetivo é fornecer um histórico claro das
escolhas arquiteturais e facilitar a compreensão das razões por trás de cada decisão.

---

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
  manutenção do projeto. Seguimos
  com o uso do Apache Kafka para mensageria.
- **Consequências**: A escolha do Spring Boot e do PostgreSQL permite desenvolver o projeto de forma eficiente, de
  maneira igual ao uso do .NET Core, aproveitando as vantagens oferecidas por essas tecnologias.
- **Status**: Aceita.
- **Autor**: Murilo Costa

---

## ADR 002: Estruturação do Projeto

- **Data**: 2026-04-21
- **Contexto**: Para garantir uma organização clara e eficiente do código, é necessário definir uma estrutura de pastas
  e pacotes para o projeto.
- **Decisão**: Optamos por não seguir a convenção de estruturação de projetos do Spring Boot, organizando o código em
  pacotes como `controller`, `service`, `repository`, `model`, entre outros. Pensando em uma estrutura mais orientada a
  funcionalidades, organizando o código em uma arquitetura modular como `cliente`, `cesta`, `cotacao`, `compra`,
  `custodia`, `fiscal` , etc. Cada módulo conterá suas próprias camadas de domínio, aplicação, infraestrutura e web,
  facilitando a manutenção e a evolução do projeto ao longo do tempo.
- **Consequências**: A estruturação orientada a funcionalidades pode facilitar a navegação e a compreensão do código,
  especialmente para pessoas de fora do desenvolvimento, pois agrupa as classes relacionadas a uma funcionalidade
  específica.
- **Status**: Aceita.
- **Autor**: Murilo Costa

---

## ADR 003: Flyway para Gerenciamento de Migrações e Versionamento de Banco de Dados

- **Data**: 2026-04-21
- **Contexto**: Para garantir a consistência e a integridade do banco de dados ao longo do desenvolvimento, é necessário
  adotar uma ferramenta de gerenciamento de migrações e versionamento de banco de dados.
- **Decisão**: Optamos por utilizar o Flyway para gerenciamento de migrações e versionamento de banco de dados. O Flyway
  é uma ferramenta leve e fácil de usar que permite
  criar, gerenciar e aplicar migrações de banco de dados de forma eficiente. Ele suporta uma variedade de bancos de
  dados, incluindo PostgreSQL, e se integra facilmente com o Spring Boot, facilitando a automação do processo de
  migração durante o desenvolvimento e a implantação.
- **Consequências**: A adoção do Flyway para gerenciamento de migrações e versionamento de banco de dados garante que as
  mudanças no esquema do banco de dados sejam aplicadas de forma controlada e consistente, evitando problemas de
  incompatibilidade e garantindo a integridade dos dados.
- **Status**: Aceita.
- **Autor**: Murilo Costa

---

## ADR 004: Uso do Apache Kafka para Mensageria

- **Data**: 2026-04-21
- **Contexto**: Para garantir a comunicação eficiente entre os diferentes módulos do sistema, é necessário adotar uma
  solução de mensageria que permita a troca de mensagens de forma assíncrona e escalável.
- **Decisão**: Optamos por utilizar o Apache Kafka para mensageria. Consta nos requisitos.
- **Consequências**: A adoção do Apache Kafka para mensageria permite a comunicação eficiente entre os diferentes
  módulos do sistema, facilitando a troca de mensagens de forma assíncrona e escalável.
- **Status**: Aceita.
- **Autor**: Murilo Costa

---

## ADR 005: Estratégia de Testes

- **Data**: 2026-04-21
- **Contexto**: Para garantir a qualidade e a confiabilidade do código, é necessário adotar uma estratégia de testes que
  permita a validação das funcionalidades do sistema.
- **Decisão**: Optamos por adotar uma estratégia de testes que inclui testes unitários, testes de integração e testes de
  ponta a ponta. Os testes unitários serão escritos para validar o comportamento de classes e métodos individuais,
  enquanto os testes de integração serão utilizados para validar a interação entre diferentes componentes do sistema. Os
  testes de ponta a ponta serão utilizados para validar o comportamento do sistema como um todo, simulando cenários
  reais de uso. Além disso, foi decidido, conforme os requisitos, a cobertura de no mínimo 70% do código por testes, 
  sendo validadas pela ferramenta JaCoCo.
- **Consequências**: A adoção de uma estratégia de testes abrangente garante a qualidade e a confiabilidade do
  código, permitindo a identificação e correção de problemas de forma eficiente.
- **Status**: Aceita.
- **Autor**: Murilo Costa

---

## ADR 006: Uso do Thymeleaf para Visualização

- **Data**: 2026-04-21
- **Contexto**: Para a visualização do sistema, é necessário adotar uma solução que permita a criação de interfaces de
  usuário de forma eficiente e integrada com o backend.
- **Decisão**: Optamos por utilizar o Thymeleaf para visualização. O Thymeleaf é um mecanismo de template para Java que
  se integra facilmente com o Spring Boot, permitindo a criação de interfaces de usuário de forma eficiente e flexível.
  Ele suporta a criação de templates HTML dinâmicos, facilitando a construção de páginas web interativas e responsivas.
- **Consequências**: A adoção do Thymeleaf para visualização permite a criação de interfaces de usuário de forma
  eficiente e integrada com o backend, facilitando a construção de páginas web interativas e responsivas.
- **Status**: Aceita.
- **Autor**: Murilo Costa