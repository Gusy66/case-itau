# Case Itau - Renegociacao

Projeto que fiz para o case de modernizacao de cobranca e renegociacao.

O recorte implementado foi o fluxo de renegociacao:

- simular acordo de uma divida
- efetivar acordo
- consultar acordo criado
- impedir nova renegociacao da mesma divida

## Tecnologias

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2
- PostgreSQL
- JUnit 5

## Como rodar local

```bash
mvn spring-boot:run
```

URLs:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

## Como rodar com Docker

```bash
docker compose up --build
```

URLs:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Postgres: `localhost:5432` (usuario `reneg`, senha `reneg`)

Para parar:

```bash
docker compose down
```

Para parar limpando volume:

```bash
docker compose down -v
```

## Endpoints

- `POST /api/v1/debts/{debtId}/simulate`
- `POST /api/v1/agreements`
- `GET /api/v1/agreements/{agreementId}`

## Massa inicial para teste

- `11111111-1111-1111-1111-111111111111`
- `22222222-2222-2222-2222-222222222222`

## Testes de request

Arquivo: `requests.http`

Fluxo recomendado:

1. simular divida 1
2. criar acordo da divida 1
3. consultar acordo criado
4. tentar simular de novo a mesma divida (esperado 422)
5. repetir com a divida 2

## Regras de negocio usadas no recorte

- valor total da divida = principal + encargos
- desconto por quantidade de parcelas:
  - 1 parcela: 12%
  - 2 a 6 parcelas: 8%
  - 7 a 12 parcelas: 4%
  - 13 a 24 parcelas: 0%
- bonus de 3% se entrada for >= 20% do valor original
- parcela minima de 50.00
- so permite renegociar divida com status `OPEN`
