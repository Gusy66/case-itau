# Evidencias para demonstracao ao vivo

## Evidencia 1 - Simulacao bem-sucedida

Request:

- `POST /api/v1/debts/11111111-1111-1111-1111-111111111111/simulate`

Resultado esperado:

- Status `200 OK`
- Retorno com `debtId`, `negotiatedAmount`, `installmentAmount`

## Evidencia 2 - Criacao de acordo bem-sucedida

Request:

- `POST /api/v1/agreements`

Resultado esperado:

- Status `201 Created`
- Retorno com `agreementId` e `status: CREATED`

## Evidencia 3 - Bloqueio de renegociacao repetida

Request:

- `POST /api/v1/debts/11111111-1111-1111-1111-111111111111/simulate` apos criacao do acordo

Resultado esperado:

- Status `422 Unprocessable Entity`
- `detail: Divida indisponivel para renegociacao`

## Comando de fallback de execucao

```bash
docker compose up --build
```
