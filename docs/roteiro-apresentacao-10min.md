# Roteiro Final de Apresentacao - 10 minutos

## 0:00 - 0:45 | Abertura

Boa noite. Neste case eu tratei o problema de modernizacao de cobranca e renegociacao com foco em evolucao incremental, desacoplamento e capacidade online. O objetivo foi provar uma estrategia viavel de saida do legado sem Big Bang.

## 0:45 - 2:00 | Contexto e premissas

O cenario atual e um monolito mainframe com alto acoplamento e dependente de processamento batch. Para reduzir risco, assumi que o legado permanece como sistema de registro no curto prazo, enquanto extraimos funcionalidades para servicos especializados. Tambem assumi integracoes externas com birôs e gateways como mockadas nesta primeira fase.

## 2:00 - 3:45 | Arquitetura proposta

A proposta usa API Gateway, servicos de Renegociacao, Cobranca e Pagamentos, banco por servico e integracao orientada a eventos. O ponto tecnico central e o uso de Outbox para garantir confiabilidade na publicacao de eventos de dominio, evitando inconsistencias entre transacao de banco e mensageria. O legado e isolado por uma camada Anti-Corruption, que protege os novos servicos de contratos antigos e permite migracao gradual.

## 3:45 - 6:30 | Demonstracao funcional

O recorte implementado foi o servico de renegociacao.

Fluxo de demo:

1. Simulo a renegociacao de uma divida aberta.
2. Efetivo o acordo.
3. Consulto o acordo criado.
4. Tendo a mesma divida ja renegociada, provo que nova simulacao e bloqueada por regra de negocio com retorno 422.

Esse fluxo demonstra:

- regra de negocio encapsulada;
- consistencia transacional;
- estado de dominio evoluindo de OPEN para RENEGOTIATED;
- contrato REST pronto para consumo dos canais digitais.

Comando de fallback para execucao em ambiente de entrevista:

`docker compose up --build`

## 6:30 - 8:00 | Qualidade tecnica

A implementacao utiliza Java 21 e Spring Boot 3, com validacao de entrada, erros padronizados com ProblemDetail e testes unitarios e de integracao. O projeto tambem tem setup local com H2 e setup containerizado com Postgres via docker-compose para aproximar do ambiente de producao.

## 8:00 - 9:15 | Plano de evolucao

A evolucao prevista ocorre em ondas:

1. Introduzir idempotencia nos comandos de criacao de acordo.
2. Publicar outbox para broker e plugar consumidores de cobranca e pagamentos.
3. Externalizar politicas de desconto para motor de regras.
4. Expandir observabilidade com tracing distribuido e SLOs por jornada.
5. Avancar no strangler pattern ate desativar funcoes equivalentes no legado.

## 9:15 - 10:00 | Encerramento

Esta entrega prioriza clareza arquitetural e viabilidade de execucao. O recorte mostra como modernizar com seguranca, preservando operacao atual enquanto aumenta velocidade de evolucao de produto. Posso detalhar a seguir as decisoes de modelagem, trade-offs e estrategia de rollout.
