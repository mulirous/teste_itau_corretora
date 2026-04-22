# Compra Programada de Ações

Sistema de compra programada de ações inspirado no desafio técnico da Itaú Corretora. O produto permite que clientes
adiram a uma carteira recomendada de 5 ações, chamada **Top Five**, façam aportes mensais recorrentes e tenham suas
compras executadas, distribuídas e acompanhadas automaticamente.

O desafio original propõe implementação em .NET Core com MySQL. Neste projeto, por objetivo de estudo e evolução técnica,
a solução será desenvolvida com **Java 21, Spring Boot, PostgreSQL, Apache Kafka e Thymeleaf**, preservando as regras de
negócio, os fluxos principais, a mensageria e a leitura de arquivos COTAHIST da B3.

## Objetivo Do Sistema

O sistema deve permitir que uma corretora:

- Cadastre clientes interessados em compra programada de ações.
- Crie uma conta gráfica individual para cada cliente.
- Mantenha uma conta master da corretora para compras consolidadas.
- Cadastre e versione a carteira recomendada Top Five.
- Execute compras programadas nos dias úteis equivalentes aos dias 5, 15 e 25 de cada mês.
- Leia cotações de fechamento a partir de arquivos COTAHIST da B3.
- Calcule compra consolidada, lote padrão, mercado fracionário, distribuição proporcional e resíduos.
- Atualize custódia, preço médio e rentabilidade dos clientes.
- Publique eventos fiscais no Kafka.
- Execute rebalanceamentos quando houver mudança de cesta ou desvio relevante de proporção.

## Stack Técnica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework backend | Spring Boot |
| API REST | Spring WebMVC |
| Validação | Spring Validation |
| Persistência | Spring Data JPA |
| Banco de dados | PostgreSQL |
| Versionamento de banco | Flyway |
| Mensageria | Apache Kafka |
| Interface web | Thymeleaf |
| Documentação de API | Springdoc OpenAPI / Swagger |
| Observabilidade básica | Spring Boot Actuator |
| Build | Maven |
| Infra local | Docker Compose |

## Estrutura Do Repositório

```text
.
├── ADRs.md
├── CHANGELOG.md
├── README.md
├── docker-compose.yml
├── requirements/
│   ├── desafio-tecnico-compra-programada.md
│   ├── regras-negocio-detalhadas.md
│   ├── exemplos-contratos-api.md
│   ├── glossario-compra-programada.md
│   ├── layout-cotahist-b3.md
│   └── diagramas *.drawio
└── teste/
    ├── cotacoes/
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/itau/teste/
        │   │   ├── cesta/
        │   │   ├── cliente/
        │   │   ├── compra/
        │   │   ├── cotacao/
        │   │   ├── custodia/
        │   │   ├── fiscal/
        │   │   ├── rebalanceamento/
        │   │   └── shared/
        │   └── resources/
        └── test/
```

Observação: o projeto Spring Boot está dentro da pasta `teste/`. Os comandos Maven devem ser executados a partir dessa
pasta.

## Arquitetura

A arquitetura escolhida é um **monólito modular orientado a contextos de negócio**, inspirado em Clean Architecture e
arquitetura hexagonal.

Cada módulo de negócio tende a seguir a estrutura:

```text
modulo/
├── domain/
├── application/
├── infrastructure/
└── web/
```

Responsabilidades esperadas:

| Camada | Responsabilidade |
|---|---|
| `domain` | Regras de negócio puras, entidades de domínio, value objects e serviços de domínio. Deve evitar dependência de Spring. |
| `application` | Casos de uso, orquestração de transações, entrada e saída dos fluxos de negócio. |
| `infrastructure` | Implementações técnicas: JPA, Kafka, parser COTAHIST, integrações e adapters. |
| `web` | Controllers REST, controllers Thymeleaf, DTOs de request/response e validações de entrada. |
| `shared` | Componentes compartilhados, exceções, configurações e utilitários realmente comuns. |

Módulos planejados:

| Módulo | Responsabilidade |
|---|---|
| `cliente` | Adesão, saída, alteração de aporte e dados cadastrais. |
| `cesta` | Cadastro, validação, ativação e histórico da Top Five. |
| `cotacao` | Importação, parse e consulta de cotações COTAHIST. |
| `custodia` | Posição dos clientes, posição master, preço médio e valorização. |
| `compra` | Motor de compra programada, ordens consolidadas, distribuição e resíduos. |
| `fiscal` | Cálculo e publicação de eventos de IR. |
| `rebalanceamento` | Ajuste de carteira por mudança de cesta ou desvio de proporção. |
| `shared` | Infraestrutura comum, tratamento de erros, configurações e tipos compartilhados. |

## Regras De Domínio Mais Importantes

- A cesta Top Five deve conter exatamente 5 ações.
- A soma dos percentuais da cesta deve ser exatamente 100%.
- Apenas uma cesta pode estar ativa por vez.
- Clientes ativos participam das compras programadas.
- O valor mensal do cliente é dividido em 3 parcelas iguais.
- Compras devem ocorrer nos dias úteis iguais ou subsequentes aos dias 5, 15 e 25.
- A cotação usada para cálculo deve ser o fechamento do último pregão disponível no COTAHIST.
- Quantidades compradas devem ser inteiras e truncadas para baixo.
- Compras devem priorizar lote padrão e usar mercado fracionário para o restante.
- A distribuição aos clientes deve ser proporcional ao valor aportado.
- Resíduos devem permanecer na custódia master.
- O preço médio deve ser recalculado apenas em compras.
- Vendas de rebalanceamento devem respeitar a regra de isenção até R$ 20.000,00 no mês.
- Eventos fiscais devem ser publicados no Kafka.

## Infraestrutura Local

O arquivo `docker-compose.yml` define os serviços locais principais:

| Serviço | Porta | Uso |
|---|---:|---|
| PostgreSQL | `5432` | Banco de dados da aplicação |
| Kafka | `9092` | Broker de eventos fiscais |
| Kafka UI | `8080` | Interface para inspeção de tópicos Kafka |

Subir infraestrutura:

```bash
docker compose up -d
```

Verificar containers:

```bash
docker compose ps
```

Ver logs:

```bash
docker compose logs db
docker compose logs kafka
docker compose logs kafka-ui
```

Parar infraestrutura:

```bash
docker compose down
```

Remover volumes locais:

```bash
docker compose down -v
```

## Como Rodar A Aplicação

Entrar na pasta da aplicação:

```bash
cd teste
```

Rodar testes:

```bash
./mvnw test
```

Rodar aplicação com profile local:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Observação: caso a aplicação use a porta `8080`, haverá conflito com o Kafka UI. A estratégia recomendada é configurar a
aplicação local para rodar em outra porta, como `8081`, ou alterar a porta externa do Kafka UI.

## Cotações COTAHIST

O desafio exige leitura de arquivos COTAHIST da B3. Os arquivos devem seguir o layout descrito em
`requirements/layout-cotahist-b3.md`.

Estratégia planejada:

- Armazenar arquivos `.TXT` em uma pasta `cotacoes/`.
- Processar apenas registros de detalhe com `TIPREG = 01`.
- Filtrar mercados relevantes: lote padrão e fracionário.
- Converter preços com duas casas decimais implícitas.
- Persistir cotações importadas no PostgreSQL.
- Usar a cotação de fechamento mais recente disponível por ticker.

## API E Interface Web

O projeto deve expor dois tipos de interface:

- **API REST**, documentada via Swagger/OpenAPI.
- **Dashboards Thymeleaf**, usados para visualização administrativa e consulta de carteira/rentabilidade.

Endpoints REST planejados:

| Área | Endpoint | Descrição |
|---|---|---|
| Cliente | `POST /api/clientes/adesao` | Aderir ao produto |
| Cliente | `POST /api/clientes/{clienteId}/saida` | Sair do produto |
| Cliente | `PUT /api/clientes/{clienteId}/valor-mensal` | Alterar aporte mensal |
| Cliente | `GET /api/clientes/{clienteId}/carteira` | Consultar carteira |
| Cliente | `GET /api/clientes/{clienteId}/rentabilidade` | Consultar rentabilidade detalhada |
| Admin | `POST /api/admin/cesta` | Criar ou alterar cesta Top Five |
| Admin | `GET /api/admin/cesta/atual` | Consultar cesta ativa |
| Admin | `GET /api/admin/cesta/historico` | Consultar histórico de cestas |
| Admin | `GET /api/admin/conta-master/custodia` | Consultar resíduos da conta master |
| Motor | `POST /api/motor/executar-compra` | Executar compra manualmente para testes |

Telas Thymeleaf planejadas:

| Tela | Objetivo |
|---|---|
| `/dashboard` | Visão geral do produto |
| `/clientes` | Listagem e consulta de clientes |
| `/clientes/{id}/carteira` | Carteira do cliente |
| `/clientes/{id}/rentabilidade` | Rentabilidade detalhada |
| `/admin/cesta` | Cadastro e visualização da Top Five |
| `/admin/cesta/historico` | Histórico de cestas |
| `/admin/conta-master` | Custódia master e resíduos |
| `/admin/compras` | Execuções do motor de compra |
| `/admin/cotacoes` | Importação e consulta de cotações |

## Estratégia De Testes

Meta mínima: **70% de cobertura**.

Prioridade de testes:

| Tipo | Escopo |
|---|---|
| Unitários de domínio | Cálculos financeiros, distribuição, preço médio, IR, validação de cesta e calendário. |
| Testes de aplicação | Casos de uso, transações e orquestração dos fluxos. |
| Testes de integração | Repositories, PostgreSQL, Kafka producer e controllers REST. |
| Testes web | Renderização básica de páginas Thymeleaf e validações de formulário. |

Ferramentas planejadas:

- JUnit 5.
- Spring Boot Test.
- Testcontainers.
- JaCoCo.

## Roadmap

### Fase 1 - Fundação Técnica

- [x] Criar projeto Spring Boot.
- [x] Definir stack principal.
- [x] Criar infraestrutura local inicial.
- [x] Criar estrutura modular por contexto.
- [x] Iniciar ADRs.
- [x] Criar pasta de cotações.
- [x] Finalizar profiles `local` e `test`.
- [x] Validar Docker Compose localmente.
- [ ] Criar migrations iniciais com Flyway.
- [x] Configurar cobertura de testes.
- [x] Refinar documentação técnica.

### Fase 2 - Cliente, Cesta E Base De Dados

- [ ] Implementar adesão de cliente.
- [ ] Criar conta gráfica filhote.
- [ ] Registrar histórico de alteração de aporte.
- [ ] Implementar saída do produto.
- [ ] Implementar cadastro da cesta Top Five.
- [ ] Garantir apenas uma cesta ativa.
- [ ] Criar primeiras migrations reais.

### Fase 3 - Cotações

- [ ] Implementar parser COTAHIST.
- [ ] Importar cotações para o banco.
- [ ] Consultar fechamento mais recente por ticker.
- [ ] Criar testes com arquivo COTAHIST sintético.

### Fase 4 - Motor De Compra Programada

- [ ] Agrupar clientes ativos.
- [ ] Calcular aporte por data.
- [ ] Calcular compra consolidada.
- [ ] Considerar resíduos da conta master.
- [ ] Separar lote padrão e mercado fracionário.
- [ ] Distribuir ativos para clientes.
- [ ] Atualizar preço médio.
- [ ] Atualizar custódia master.
- [ ] Publicar eventos fiscais no Kafka.

### Fase 5 - Carteira, Rentabilidade E Dashboards

- [ ] Consultar carteira por cliente.
- [ ] Calcular P/L por ativo.
- [ ] Calcular P/L total.
- [ ] Calcular rentabilidade percentual.
- [ ] Criar dashboards Thymeleaf.

### Fase 6 - Rebalanceamento

- [ ] Rebalancear por mudança de cesta.
- [ ] Rebalancear por desvio de proporção.
- [ ] Calcular vendas mensais.
- [ ] Aplicar regra de isenção de R$ 20.000,00.
- [ ] Publicar eventos de IR sobre venda.

### Fase 7 - Qualidade E Entrega

- [ ] Completar cobertura mínima.
- [ ] Criar GitHub Actions.
- [ ] Finalizar README.
- [ ] Finalizar ADRs.
- [ ] Gravar vídeo demonstrativo.

## Matriz De Requisitos

Status usados:

- `Não iniciado`: requisito ainda não implementado.
- `Parcial`: base ou preparação existente, mas comportamento final ainda incompleto.
- `Implementado`: requisito atendido.
- `Validado`: requisito implementado e coberto por testes.

### Requisitos De Cliente

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-001 | Cliente deve informar nome, CPF, email e valor mensal de aporte na adesão. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-002 | CPF deve ser único no sistema. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-003 | Valor mensal mínimo deve ser validado, com sugestão de R$ 100,00. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-004 | Ao aderir, criar automaticamente conta gráfica filhote e custódia filhote. | `regras-negocio-detalhadas.md` | `cliente`, `custodia` | Não iniciado |
| RN-005 | Cliente inicia com status ativo. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-006 | Data de adesão deve ser registrada. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-007 | Ao sair, cliente deve mudar para ativo igual a falso. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-008 | Saída do produto mantém posição existente na custódia filhote. | `regras-negocio-detalhadas.md` | `cliente`, `custodia` | Não iniciado |
| RN-009 | Cliente inativo não participa mais das compras programadas. | `regras-negocio-detalhadas.md` | `cliente`, `compra` | Não iniciado |
| RN-010 | Cliente pode consultar carteira mesmo após sair do produto. | `regras-negocio-detalhadas.md` | `cliente`, `custodia` | Não iniciado |
| RN-011 | Cliente pode alterar valor mensal a qualquer momento. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |
| RN-012 | Alteração de valor mensal deve valer para a próxima data de compra. | `regras-negocio-detalhadas.md` | `cliente`, `compra` | Não iniciado |
| RN-013 | Valor mensal anterior deve ser mantido em histórico. | `regras-negocio-detalhadas.md` | `cliente` | Não iniciado |

### Requisitos De Cesta Top Five

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-014 | Cesta deve conter exatamente 5 ações. | `regras-negocio-detalhadas.md` | `cesta` | Não iniciado |
| RN-015 | Soma dos percentuais deve ser exatamente 100%. | `regras-negocio-detalhadas.md` | `cesta` | Não iniciado |
| RN-016 | Cada percentual deve ser maior que 0%. | `regras-negocio-detalhadas.md` | `cesta` | Não iniciado |
| RN-017 | Ao alterar cesta, cesta anterior deve ser desativada e nova cesta ativa criada. | `regras-negocio-detalhadas.md` | `cesta` | Não iniciado |
| RN-018 | Apenas uma cesta pode estar ativa por vez. | `regras-negocio-detalhadas.md` | `cesta` | Não iniciado |
| RN-019 | Alteração da cesta deve disparar rebalanceamento para clientes ativos. | `regras-negocio-detalhadas.md` | `cesta`, `rebalanceamento` | Não iniciado |

### Requisitos Do Motor De Compra Programada

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-020 | Compras devem ocorrer nos dias 5, 15 e 25 de cada mês. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-021 | Se a data cair em sábado ou domingo, executar no próximo dia útil. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-022 | Dias úteis são simplificados como segunda a sexta-feira. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-023 | Valor mensal deve ser dividido em 3 parcelas iguais. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-024 | Apenas clientes ativos participam do agrupamento. | `regras-negocio-detalhadas.md` | `compra`, `cliente` | Não iniciado |
| RN-025 | Valor de cada cliente na data deve ser valor mensal dividido por 3. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-026 | Valores dos clientes devem ser consolidados em compra única na conta master. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-027 | Usar cotação de fechamento do último pregão disponível no COTAHIST. | `regras-negocio-detalhadas.md` | `cotacao`, `compra` | Não iniciado |
| RN-028 | Quantidade deve ser truncada para baixo a partir de valor dividido por cotação. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-029 | Antes de comprar, verificar saldo remanescente da custódia master. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-030 | Saldo master deve ser descontado da quantidade a comprar. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-031 | Quantidades maiores ou iguais a 100 devem usar lote padrão em múltiplos de 100. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-032 | Restante entre 1 e 99 ações deve usar mercado fracionário. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-033 | Ticker fracionário deve receber sufixo `F`. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-034 | Distribuição deve ser proporcional ao aporte de cada cliente. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-035 | Proporção do cliente deve ser aporte do cliente dividido pelo total de aportes. | `regras-negocio-detalhadas.md` | `compra` | Não iniciado |
| RN-036 | Quantidade por cliente deve ser truncada para baixo. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-037 | Quantidade disponível deve considerar compras mais saldo master anterior. | `regras-negocio-detalhadas.md` | `compra`, `custodia` | Não iniciado |
| RN-038 | Ao distribuir, preço médio da custódia filhote deve ser atualizado. | `regras-negocio-detalhadas.md` | `custodia`, `compra` | Não iniciado |
| RN-039 | Ações não distribuídas permanecem na custódia master. | `regras-negocio-detalhadas.md` | `custodia`, `compra` | Não iniciado |
| RN-040 | Saldo da custódia master deve ser considerado na próxima compra. | `regras-negocio-detalhadas.md` | `custodia`, `compra` | Não iniciado |

### Requisitos De Preço Médio

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-041 | Preço médio deve ser calculado por ativo por cliente. | `regras-negocio-detalhadas.md` | `custodia` | Não iniciado |
| RN-042 | Fórmula de preço médio deve ponderar quantidade anterior, PM anterior, quantidade nova e preço da nova compra. | `regras-negocio-detalhadas.md` | `custodia` | Não iniciado |
| RN-043 | Venda não altera preço médio. | `regras-negocio-detalhadas.md` | `custodia`, `rebalanceamento` | Não iniciado |
| RN-044 | Preço médio só deve ser recalculado em compras. | `regras-negocio-detalhadas.md` | `custodia` | Não iniciado |

### Requisitos De Rebalanceamento

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-045 | Rebalanceamento por mudança deve ser disparado quando o administrador alterar a Top Five. | `regras-negocio-detalhadas.md` | `rebalanceamento`, `cesta` | Não iniciado |
| RN-046 | Para cada cliente ativo, identificar ativos que saíram da cesta. | `regras-negocio-detalhadas.md` | `rebalanceamento` | Não iniciado |
| RN-047 | Vender toda a posição dos ativos que saíram da cesta. | `regras-negocio-detalhadas.md` | `rebalanceamento`, `custodia` | Não iniciado |
| RN-048 | Comprar novos ativos com o valor obtido, seguindo nova composição. | `regras-negocio-detalhadas.md` | `rebalanceamento`, `compra` | Não iniciado |
| RN-049 | Rebalancear ativos que permaneceram, mas mudaram de percentual. | `regras-negocio-detalhadas.md` | `rebalanceamento` | Não iniciado |
| RN-050 | Rebalanceamento por desvio ocorre quando proporção real diverge significativamente da cesta. | `regras-negocio-detalhadas.md` | `rebalanceamento` | Não iniciado |
| RN-051 | Sistema pode definir limiar de desvio, com sugestão de 5 pontos percentuais. | `regras-negocio-detalhadas.md` | `rebalanceamento` | Não iniciado |
| RN-052 | Vender ativos sobre-alocados e comprar ativos sub-alocados. | `regras-negocio-detalhadas.md` | `rebalanceamento` | Não iniciado |

### Requisitos Fiscais

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-053 | IR dedo-duro tem alíquota de 0,005% sobre valor total da operação. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-054 | IR dedo-duro deve ser calculado para cada operação distribuída ao cliente. | `regras-negocio-detalhadas.md` | `fiscal`, `compra` | Não iniciado |
| RN-055 | Valor do IR deve ser publicado em tópico Kafka. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-056 | Mensagem Kafka deve conter cliente, CPF, ticker, valor da operação, valor IR e data. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-057 | Somar todas as vendas do cliente no mês corrente. | `regras-negocio-detalhadas.md` | `fiscal`, `rebalanceamento` | Não iniciado |
| RN-058 | Se total de vendas for menor ou igual a R$ 20.000,00, cliente é isento. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-059 | Se vendas excederem R$ 20.000,00, aplicar 20% sobre lucro líquido total. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-060 | Lucro deve ser valor de venda menos quantidade multiplicada pelo preço médio. | `regras-negocio-detalhadas.md` | `fiscal`, `custodia` | Não iniciado |
| RN-061 | Em caso de prejuízo, IR deve ser R$ 0,00. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |
| RN-062 | IR de venda deve ser publicado no Kafka. | `regras-negocio-detalhadas.md` | `fiscal` | Não iniciado |

### Requisitos De Rentabilidade

| Código | Requisito | Origem | Módulo | Status |
|---|---|---|---|---|
| RN-063 | Exibir saldo total da carteira. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-064 | Exibir P/L por ativo. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-065 | Exibir P/L total. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-066 | Exibir rentabilidade percentual. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-067 | Exibir preço médio de cada ativo. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-068 | Exibir quantidade de cada ativo em custódia. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |
| RN-069 | Exibir cotação atual de cada ativo. | `regras-negocio-detalhadas.md` | `cotacao`, `web` | Não iniciado |
| RN-070 | Exibir composição percentual real da carteira. | `regras-negocio-detalhadas.md` | `custodia`, `web` | Não iniciado |

### Requisitos Técnicos

| Código | Requisito | Origem | Módulo/Área | Status |
|---|---|---|---|---|
| RT-001 | Expor API REST para funcionalidades de cliente, administração, carteira e motor. | `desafio-tecnico-compra-programada.md` | `web` | Não iniciado |
| RT-002 | Documentar API com Swagger/OpenAPI. | `desafio-tecnico-compra-programada.md` | `shared`, `web` | Parcial |
| RT-003 | Usar Apache Kafka real via Docker para eventos fiscais. | `desafio-tecnico-compra-programada.md` | `fiscal`, infra | Parcial |
| RT-004 | Implementar leitura e parse de arquivo COTAHIST TXT. | `desafio-tecnico-compra-programada.md` | `cotacao` | Não iniciado |
| RT-005 | Manter arquivos COTAHIST em pasta `cotacoes/`. | `desafio-tecnico-compra-programada.md` | `cotacao` | Parcial |
| RT-006 | Persistir dados em banco relacional. | `desafio-tecnico-compra-programada.md` | infra | Parcial |
| RT-007 | Usar migrations para versionar o banco. | Decisão do projeto | infra | Parcial |
| RT-008 | Atingir cobertura mínima de 70%. | `desafio-tecnico-compra-programada.md` | testes | Não iniciado |
| RT-009 | Manter README completo com instruções, arquitetura e decisões técnicas. | `desafio-tecnico-compra-programada.md` | documentação | Parcial |
| RT-010 | Registrar decisões arquiteturais em ADRs. | Decisão do projeto | documentação | Parcial |
| RT-011 | Criar interface web como diferencial. | `desafio-tecnico-compra-programada.md` | Thymeleaf | Não iniciado |
| RT-012 | Configurar observabilidade básica. | Diferencial do projeto | infra | Parcial |
| RT-013 | Configurar CI/CD. | `desafio-tecnico-compra-programada.md` | entrega | Não iniciado |

## Decisões Arquiteturais

As decisões arquiteturais são registradas em `ADRs.md`.

## CHANGELOG

As mudanças relevantes do projeto são registradas em `CHANGELOG.md`.

## Referências Do Projeto

- `requirements/desafio-tecnico-compra-programada.md`
- `requirements/regras-negocio-detalhadas.md`
- `requirements/exemplos-contratos-api.md`
- `requirements/glossario-compra-programada.md`
- `requirements/layout-cotahist-b3.md`
