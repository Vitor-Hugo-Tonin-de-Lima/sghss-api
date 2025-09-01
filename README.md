# API do Sistema de Gestão Hospitalar (SGHSS)
## 📖 Sobre o Projeto
Este repositório contém o código-fonte do back-end para o Sistema de Gestão Hospitalar e de Serviços de Saúde (SGHSS). O projeto foi desenvolvido como parte da disciplina de Projeto Multidisciplinar do curso de Tecnologia em Análise e Desenvolvimento de Sistemas da UNINTER.

O objetivo foi criar uma API RESTful robusta e segura para gerenciar as operações da instituição fictícia "VidaPlus", que administra hospitais, clínicas e laboratórios. A API centraliza o gerenciamento de pacientes, profissionais de saúde e consultas, com um forte foco em segurança de dados e conformidade com a LGPD.

## ✨ Funcionalidades Implementadas
API RESTful com arquitetura em camadas (Controller, Service, Model, Repository).

Autenticação e Autorização com Spring Security e Tokens JWT, protegendo os endpoints contra acesso não autorizado.

Autorização baseada em Perfis (Roles) para diferenciar o acesso de Pacientes, Profissionais e Administradores.

Gerenciamento completo (CRUD) de Pacientes e Profissionais de Saúde.

Lógica de Agendamento de Consultas, com validação de regras de negócio.

Sistema de "Soft Delete", que desativa registros em vez de apagá-los permanentemente, garantindo a integridade e o histórico dos dados hospitalares.

Lógica de Reativação de Contas para usuários "deletados" que tentem se cadastrar novamente.

## 🛠️ Tecnologias Utilizadas
Linguagem: Java 22

Framework Principal: Spring Boot 4

Segurança: Spring Security 6

Acesso a Dados: Spring Data JPA / Hibernate

Banco de Dados: MySQL 8

Autenticação: JSON Web Tokens (JWT)

Gerenciamento de Dependências: Apache Maven

Testes de API: Postman

## 🚀 Como Executar o Projeto
Siga os passos abaixo para executar a aplicação localmente.

### Pré-requisitos
Antes de começar, você vai precisar ter instalado em sua máquina:

* JDK 22 ou superior;

* Apache Maven 3.8 ou superior;

* MySQL 8.0 ou superior.

* Git.
    
### 1. Clonar o Repositório
Bash:   

git clone https://github.com/Vitor-Hugo-Tonin-de-Lima/sghss-api.git

cd sghss-api
### 2. Configurar o Banco de Dados
Abra seu cliente MySQL (como o MySQL Workbench).

Crie um novo schema (banco de dados) com o nome que preferir. Ex: sghss_db.

A aplicação criará as tabelas automaticamente na primeira inicialização.

### 3. Configurar a Aplicação
Navegue até o arquivo src/main/resources/application.properties.

Altere as seguintes propriedades para corresponder à sua configuração do MySQL e defina uma chave secreta para o JWT:

* spring.datasource.url=jdbc:mysql://localhost:3306/sghss_db

* spring.datasource.username=seu_usuario_mysql

* spring.datasource.password=sua_senha_mysql

* api.security.token.secret=SUA_CHAVE_SECRETA_EM_BASE64_AQUI

### 4. Executar a Aplicação
Abra um terminal na raiz do projeto.

Execute o seguinte comando Maven:

Bash:

mvn spring-boot:run

A API estará disponível em http://localhost:8080. 

Um usuário admin@sghss.com com senha admin123 será criado na primeira inicialização.

## ✔️ Endpoints da API

A API é protegida por JWT. Para acessar os endpoints protegidos, primeiro obtenha um token através do endpoint de login.

* POST /auth/login - Autentica um usuário e retorna um token JWT.

* POST /api/pacientes - Cadastra um novo paciente.

* GET /api/pacientes - Lista todos os pacientes ativos (requer autenticação).

* POST /api/profissionais - Cadastra um novo profissional de saúde.

* POST /api/consultas - Agenda uma nova consulta (requer autenticação).

* DELETE /api/pacientes/{id} - Desativa um paciente (requer perfil de ADMIN).

Para uma documentação mais detalhada de todos os endpoints e regras de negócio, consulte o documento PDF do projeto.

## ✒️ Autor
Vitor Hugo Tonin de Lima

## 📎 RU
4458152

GitHub: [GitHub](https://github.com/Vitor-Hugo-Tonin-de-Lima)

LinkedIn: [LinkedIn](www.linkedin.com/in/vitor-hugo-tonin-de-lima-3b119223a)
