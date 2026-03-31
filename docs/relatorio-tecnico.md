# Relatorio Tecnico - Case Itau

## 1. Contexto

Proposta de modernizacao de um legado de cobranca e renegociacao com foco em desacoplamento, performance e evolucao continua.

## 2. Premissas

- Sistema legado permanece como sistema de registro no curto prazo
- Integracoes externas de mensageria e buro estao mockadas nesta fase
- Recorte implementado no case: simulacao e efetivacao de acordo
- Arquitetura online-first com comunicacao assincrona por eventos

## 3. Arquitetura

Diagrama e detalhamento tecnico: `docs/architecture.md`

## 4. Funcionalidade codificada

Servico Java implementado: `renegotiation-service`

Funcionalidades:

- Simulacao de renegociacao
- Efetivacao de acordo
- Atualizacao de status da divida
- Registro de evento `AgreementCreated` em outbox

Endpoints:

- `POST /api/v1/debts/{debtId}/simulate`
- `POST /api/v1/agreements`
- `GET /api/v1/agreements/{agreementId}`

## 5. Tecnologias e padroes

- Java 21
- Spring Boot 3
- Arquitetura em camadas
- Persistencia com Spring Data JPA
- Outbox pattern para integracao assincrona
- Testes com JUnit 5 e Spring Boot Test

## 6. Evidencias de qualidade

- Validacoes de entrada com Bean Validation
- Erros de negocio padronizados com ProblemDetail
- Teste unitario da regra de simulacao
- Teste de integracao de endpoint

## 7. Evolucao planejada

- Inclusao de chave de idempotencia na criacao de acordos
- Publicacao assicrona da outbox para broker
- Separacao em microservicos de cobranca e pagamentos
- Integracao real com birôs e gateways de comunicacao

## 8. Referencias

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
- [Microservices.io - Strangler Fig Pattern](https://microservices.io/post/refactoring/2023/06/21/strangler-fig-application-pattern-incremental-modernization-to-services.md.html)
- [Microservices.io - Transactional Outbox](https://microservices.io/patterns/data/transactional-outbox.html)

## 9. GitHub

Perfil GitHub: `https://github.com/<seu-usuario>`

Repositorio do case: `https://github.com/<seu-usuario>/case-itau-renegotiation`

Status atual para submissao:

- Projeto estruturado e validado localmente
- Fluxo de API validado ponta a ponta
- Documentacao tecnica e roteiro de apresentacao prontos
