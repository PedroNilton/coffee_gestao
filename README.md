# Coffee Gestão

Aplicação desktop em Java para organizar o atendimento técnico de equipamentos de café, reunindo clientes, aparelhos, produtos e ordens de serviço em uma única base.

> **Status:** projeto em desenvolvimento. A estrutura de domínio e persistência já foi iniciada, mas algumas telas e fluxos ainda estão sendo implementados.

## Objetivo

O projeto foi criado para praticar a construção de uma aplicação Java em camadas, separando responsabilidades de interface, regras de negócio, acesso a dados e modelos de domínio.

## Estado atual

- Modelos para clientes, usuários, aparelhos, produtos e ordens de serviço
- Controllers e repositories organizados por responsabilidade
- Inicialização e persistência local com SQLite
- Estrutura inicial para login, dashboard, clientes e ordens de serviço
- Interface e fluxos de uso ainda em evolução

## Tecnologias

- Java 17
- Maven
- SQLite
- JDBC

## Estrutura

```text
src/main/java/br/com/coffeegestao/
├── controller/   # Coordenação dos fluxos da aplicação
├── database/     # Conexão e inicialização do SQLite
├── model/        # Entidades do domínio
├── repository/   # Acesso e persistência de dados
├── service/      # Regras e serviços da aplicação
├── view/         # Telas em desenvolvimento
└── Main.java     # Ponto de entrada
```

## Como executar

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.9 ou superior

1. Clone o repositório.
2. Abra o projeto em uma IDE com suporte a Maven.
3. Aguarde a resolução das dependências.
4. Execute `br.com.coffeegestao.Main`.

Na inicialização, a aplicação prepara o banco SQLite local. Arquivos `*.db` são ignorados pelo Git.

## Próximos passos

- Finalizar as telas e conectar os fluxos aos controllers
- Completar as operações de clientes e ordens de serviço
- Adicionar validações e tratamento de erros
- Criar testes automatizados
- Documentar o fluxo completo com screenshots

## Aprendizados

Este projeto explora organização em camadas, JDBC, persistência local, modelagem de domínio e evolução incremental de uma aplicação Java.


