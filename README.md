# Case Itau - Renegociacao

Implementacao de um recorte de modernizacao para o dominio de cobranca e renegociacao com Java e Spring Boot.

## Escopo implementado

- Simulacao de renegociacao
- Efetivacao de acordo
- Mudanca de status da divida para renegociada
- Publicacao de evento em outbox
- API REST documentada por OpenAPI
- Testes unitarios e de integracao

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2
- OpenAPI
- JUnit 5

## Como executar

```bash
mvn spring-boot:run
```

Aplicacao:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

## Como executar com Docker

```bash
docker compose up --build
```

Aplicacao:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Banco Postgres: `localhost:5432` (`reneg` / `reneg`)

Para encerrar:

```bash
docker compose down
```

Para encerrar removendo volume:

```bash
docker compose down -v
```

## Endpoints principais

- `POST /api/v1/debts/{debtId}/simulate`
- `POST /api/v1/agreements`
- `GET /api/v1/agreements/{agreementId}`

## Exemplo de simulacao

`POST /api/v1/debts/11111111-1111-1111-1111-111111111111/simulate`

```json
{
  "downPayment": 300.00,
  "installmentCount": 6
}
```

## Exemplo de criacao de acordo

`POST /api/v1/agreements`

```json
{
  "debtId": "11111111-1111-1111-1111-111111111111",
  "downPayment": 600.00,
  "installmentCount": 8
}
```

## Premissas de negocio adotadas

- Valor total da divida = principal + encargos
- Desconto por quantidade de parcelas
  - 1 parcela: 12%
  - 2 a 6 parcelas: 8%
  - 7 a 12 parcelas: 4%
  - 13 a 24 parcelas: 0%
- Bonus de 3% adicional para entrada >= 20% do valor original
- Parcela minima de 50.00
- Divida somente pode ser renegociada quando status estiver `OPEN`

## Arquitetura e relatorio

- Arquitetura: `docs/architecture.md`
- Relatorio tecnico: `docs/relatorio-tecnico.md`
- Roteiro de apresentacao: `docs/roteiro-apresentacao-10min.md`
- Evidencias para demo: `docs/evidencias-demo.md`
- Testes de API prontos: `requests.http`
