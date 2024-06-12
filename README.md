
<div style="text-align: center;">
  <img src="https://www.radcorp.com.br/site/assets/images/construcao-1000x750.png" alt="Em Construção">
</div>

### Descrição do Projeto: API de Emissão de Cartão de Crédito com Microsserviços e RabbitMQ

#### Visão Geral
O projeto "API de Emissão de Cartão de Crédito" visa desenvolver um sistema robusto e escalável para gerar e emitir cartões de crédito utilizando uma arquitetura de microsserviços. O sistema será composto por diversos microsserviços independentes, que se comunicarão entre si por meio de RabbitMQ, um sistema de mensageria eficiente e confiável.

#### Objetivos
1. **Modularidade**: Dividir a aplicação em microsserviços independentes que podem ser desenvolvidos, implantados e escalados individualmente.
2. **Escalabilidade**: Garantir que o sistema possa crescer horizontalmente para lidar com uma grande quantidade de solicitações e operações.
3. **Resiliência**: Assegurar que a aplicação continue a funcionar corretamente mesmo que alguns dos microsserviços falhem.
4. **Desempenho**: Otimizar a comunicação entre os serviços utilizando RabbitMQ para garantir um alto desempenho.

#### Componentes do Sistema

1. **Gateway API**:
   - **Função**: Servir como ponto de entrada para todas as requisições dos clientes.
   - **Tecnologia**: Spring Boot.
   
2. **Serviço de Usuário**:
   - **Função**: Gerenciar a criação, atualização e consulta de dados dos usuários.
   - **Tecnologia**: Spring Boot.
   - **Banco de Dados**: PostgreSQL.

3. **Serviço de Cartão**:
   - **Função**: Gerenciar a geração e emissão dos cartões de crédito.
   - **Tecnologia**: Spring Boot.
   - **Banco de Dados**: PostgreSQL.

4. **Serviço de Transações**:
   - **Função**: Registrar e processar transações feitas com os cartões de crédito.
   - **Tecnologia**: Spring Boot.
   - **Banco de Dados**: PostgreSQL.

5. **Serviço de Notificações**:
   - **Função**: Enviar notificações aos usuários sobre eventos importantes, como a emissão de um novo cartão ou transações realizadas.
   - **Tecnologia**: Spring Boot.
   - **Mensageria**: RabbitMQ.

6. **RabbitMQ**:
   - **Função**: Facilitar a comunicação assíncrona e desacoplada entre os microsserviços.
   - **Tecnologia**: RabbitMQ.

#### Segurança

- **Autenticação e Autorização**: Utilização do Keycloak para gerenciar autenticação e autorização dos usuários, garantindo que apenas usuários autenticados e autorizados possam acessar os recursos.

#### Fluxo de Trabalho

1. **Registro do Usuário**:
   - O cliente envia uma solicitação para registrar um novo usuário via Gateway API.
   - A Gateway API encaminha a solicitação para o Serviço de Usuário.
   - O Serviço de Usuário processa a solicitação e armazena os dados do usuário no PostgreSQL.

2. **Emissão do Cartão**:
   - O cliente solicita a emissão de um novo cartão via Gateway API.
   - A Gateway API encaminha a solicitação para o Serviço de Cartão.
   - O Serviço de Cartão gera um novo cartão e armazena os detalhes no PostgreSQL.
   - O Serviço de Cartão envia uma mensagem ao Serviço de Notificações via RabbitMQ para informar o usuário sobre a emissão do cartão.

3. **Processamento de Transações**:
   - As transações realizadas com os cartões são enviadas para o Serviço de Transações.
   - O Serviço de Transações processa as transações e atualiza os registros no PostgreSQL.
   - Notificações de transações são enviadas ao Serviço de Notificações via RabbitMQ.

4. **Notificações**:
   - O Serviço de Notificações recebe mensagens via RabbitMQ e envia notificações apropriadas aos usuários.

#### Tecnologias Utilizadas

- **Linguagens**: Java
- **Frameworks**: Spring Boot
- **Banco de Dados**: PostgreSQL, H2 (para testes)
- **Mensageria**: RabbitMQ
- **Autenticação e Autorização**: Keycloak
- **Containerização**: Docker

#### Benefícios

- **Manutenção Facilitada**: A modularidade dos microsserviços facilita a manutenção e a adição de novas funcionalidades.
- **Desempenho Otimizado**: RabbitMQ garante uma comunicação eficiente entre os serviços.
- **Escalabilidade**: A arquitetura permite escalar serviços individualmente conforme necessário.
- **Resiliência**: A independência dos microsserviços aumenta a resiliência do sistema como um todo.

Este projeto proporcionará uma plataforma eficiente e escalável para a emissão de cartões de crédito, atendendo às necessidades modernas de serviços financeiros digitais.
