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
   - **Tecnologia**: Spring Boot e Java.
   - **Banco de Dados**: MongoDB.

3. **Serviço de Cartão**:
   - **Função**: Gerenciar a geração e emissão dos cartões de crédito.
   - **Tecnologia**: Spring Boot e Java.
   - **Banco de Dados**: PostgreSQL.

5. **Serviço de Avaliação de Credito**:
   - **Função**: Responsável por analisar o perfil do cliente com base no seu ID,
   listar os cartões disponíveis de acordo com a renda do cliente, e aplicar uma restrição de 30% da renda como limite aprovado para cada cartão.
   - **Tecnologia**: Spring Boot e Java.
   - **Mensageria**: RabbitMQ.

6. **RabbitMQ**:
   - **Função**: Facilitar a comunicação assíncrona e desacoplada entre os microsserviços.
   - **Tecnologia**: RabbitMQ.

7. **Eureka Server**:
  - **Função**: Serviço de registro e descoberta de microsserviços na arquitetura de microsserviços.
  - **Tecnologia**: Spring Boot e Java.
  - **Benefícios**: Permite que os microsserviços se registrem, localizem e comuniquem dinamicamente uns com os outros.

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
     
3. **Avaliação de Crédito**:
  - O cliente solicita a emissão de um novo cartão via Gateway API.
  - A Gateway API encaminha a solicitação para o Serviço de Avaliação de Crédito.
  - O Serviço de Avaliação de Crédito consulta os dados do cliente no Serviço de Usuário para obter informações financeiras relevantes, como a renda mensal.
  - Com base na renda mensal do cliente, o Serviço de Avaliação de Crédito determina os cartões de crédito disponíveis que se enquadram na restrição de limite de crédito, limitando-o a 30% da renda mensal     do cliente.
  - O Serviço de Avaliação de Crédito retorna ao Serviço de Cartão a lista de cartões disponíveis com os limites aprovados.

#### Tecnologias Utilizadas

- **Linguagens**: Java
- **Frameworks**: Spring Boot
- **Banco de Dados**: PostgreSQL,MongoDB ,H2 (para testes)
- **Mensageria**: RabbitMQ
- **Autenticação e Autorização**: Keycloak
- **Containerização**: Docker

### Pré-requisitos

- DockerDestop instalado em sua máquina.

### Subir o Keycloak (Docker)
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.6 start-dev

### Subir o RabbitMQ (Docker)
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management

- Acessar ao http://localhost:15672/

- Login 

Username:guest

Password: guest

- Clicar em Queues and streams
- Criar uma Queues com nome "card-issuance"


#### Benefícios

- **Manutenção Facilitada**: A modularidade dos microsserviços facilita a manutenção e a adição de novas funcionalidades.
- **Desempenho Otimizado**: RabbitMQ garante uma comunicação eficiente entre os serviços.
- **Escalabilidade**: A arquitetura permite escalar serviços individualmente conforme necessário.
- **Resiliência**: A independência dos microsserviços aumenta a resiliência do sistema como um todo.

Este projeto proporcionará uma plataforma eficiente e escalável para a emissão de cartões de crédito, atendendo às necessidades modernas de serviços financeiros digitais.
