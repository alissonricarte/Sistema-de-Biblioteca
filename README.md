## 📚 Sistema de Biblioteca
   Um sistema completo de gerenciamento de biblioteca desenvolvido em Java com PostgreSQL, seguindo os princípios de Programação Orientada a Objetos e padrões de arquitetura MVC.
---
## 🎯 Objetivo do Projeto
Criar um sistema simples, modular e orientado a objetos que permita:

- Gerenciar livros e usuários da biblioteca

- Controlar empréstimos e devoluções

- Buscar livros por título, autor ou disponibilidade

- Gerar relatórios de empréstimos e atrasos

- Operar tudo via menu no terminal com banco de dados PostgreSQL
  
---
## 🛠️ Tecnologias Utilizadas:
<div> <img alt="Java" width="90" height="26" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/> <img alt="PostgreSQL" width="110" height="26" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/> <img alt="Maven" width="90" height="26" src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/> <img alt="Git" width="70" height="26" src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img alt="GitHub" width="90" height="26" src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"/> </div>

---

## 📁 Arquitetura do Sistema

```
Sistema-de-Biblioteca/                    # Pasta raiz do projeto
│
├── src/                               # Código-fonte principal
│   ├── main/                          # Arquivos principais da aplicação
│   │   ├── java/                      # Código Java do projeto
│   │   │   ├── com/
│   │   │   │   └── biblioteca/
│   │   │   │       ├── Main.java              # Classe principal com menu
│   │   │   │       │
│   │   │   │       ├── model/                 # Modelos do sistema
│   │   │   │       │   ├── Livro.java         # Representa um livro
│   │   │   │       │   ├── Usuario.java       # Representa um usuário
│   │   │   │       │   └── Emprestimo.java    # Representa um empréstimo
│   │   │   │       │
│   │   │   │       ├── dao/                   # Data Access Object
│   │   │   │       │   ├── LivroDAO.java      # Operações com livros
│   │   │   │       │   ├── UsuarioDAO.java    # Operações com usuários
│   │   │   │       │   └── EmprestimoDAO.java # Operações com empréstimos
│   │   │   │       │
│   │   │   │       ├── service/               # Camada de serviço (regras de negócio)
│   │   │   │       │   ├── BibliotecaService.java    # Serviços principais
│   │   │   │       │   └── RelatorioService.java     # Serviços de relatório
│   │   │   │       │
│   │   │   │       └── util/                  # Utilitários
│   │   │   │           └── DatabaseConnection.java   # Conexão com banco
│
├── database/                          # Scripts do banco de dados
│   ├── schema.sql                     # Criação das tabelas
│   └── data.sql                       # Dados iniciais
│
├── pom.xml                           # Configuração Maven
└── README.md                         # Documentação do projeto
```

## 🗃️ Estrutura do Banco de Dados

```
CREATE TABLE livros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    ano_publicacao INTEGER,
    disponivel BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_cadastro DATE DEFAULT CURRENT_DATE
);

CREATE TABLE emprestimos (
    id SERIAL PRIMARY KEY,
    livro_id INTEGER NOT NULL REFERENCES livros(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    data_emprestimo DATE DEFAULT CURRENT_DATE,
    data_devolucao_prevista DATE,
    data_devolucao_real DATE,
    status VARCHAR(20) DEFAULT 'ATIVO'
);
```
---
## ⚙️ Funcionalidades Implementadas
### ✅ Gestão de Livros
- Cadastrar novos livros

- Listar todos os livros (ordenados por título)

- Buscar livros por título ou autor

- Listar livros disponíveis

- Atualizar informações dos livros

### ✅ Gestão de Usuários
- Cadastrar novos usuários

- Listar todos os usuários

- Buscar usuários por nome ou email

### ✅ Sistema de Empréstimos
- Realizar empréstimos com validações

- Registrar devoluções

- Listar empréstimos ativos

- Verificar empréstimos atrasados

- Controlar limite de empréstimos por usuário

### ✅ Relatórios
- Relatório de livros (totais, disponíveis, emprestados)

- Top livros mais emprestados

- Relatório de usuários mais ativos

- Relatório de empréstimos atrasados

---
## ⚙️ Configuração do Ambiente
### Pré-requisitos
- Java 21 ou superior

- PostgreSQL 12 ou superior

- Maven 3.6 ou superior

### Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/alissonricarte/Sistema-de-Biblioteca.git
   cd Sistema-de-Biblioteca
   ```
3. Configure o banco de dados:
   ```
   CREATE DATABASE biblioteca;
   ```
5. Configure as credenciais do banco:
   ```
   private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
   private static final String USER = "seu-usuario";
   private static final String PASSWORD = "sua-senha";
   ```
7. Execute o projeto:
   ```
   mvn compile
   mvn exec:java -Dexec.mainClass="com.biblioteca.Main"
   ```
---

## 🔧 Principais Classes
### LivroDAO
cadastrar(Livro livro) - Adiciona novo livro

- listarTodos() - Lista todos os livros ordenados

- buscarPorTitulo(String titulo) - Busca por título

- listarDisponiveis() - Lista livros disponíveis

### BibliotecaService
realizarEmprestimo(int livroId, int usuarioId) - Realiza empréstimo com validações

- registrarDevolucao(int emprestimoId) - Registra devolução

- consultarEmprestimosAtivos() - Lista empréstimos ativos

### RelatorioService
- gerarRelatorioLivros() - Estatísticas de livros

- gerarTopLivrosMaisEmprestados() - Ranking de livros

- gerarRelatorioAtrasos() - Empréstimos atrasados

---

## 👨‍💻 Autor
 Alisson Ricarte - <a href="https://github.com/alissonricarte"><b>GITHUB</b></a>
 
---
## 📞 Contato
Se tiver alguma dúvida ou sugestão sobre o Sistema de Biblioteca, sinta-se à vontade para entrar em contato!
<img width=100% src="https://capsule-render.vercel.app/api?type=waving&height=110&color=4B8BBE&section=footer&reversal=false"/>
  
   
   
