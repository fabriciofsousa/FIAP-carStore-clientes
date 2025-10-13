# 👥 FIAP CarStore - Clientes

---

O **FIAP CarStore - Clientes** é um microsserviço responsável pelo gerenciamento das informações de clientes dentro do ecossistema **CarStore**.

Ele funciona em conjunto com o microsserviço **Veículo**, compartilhando o mesmo banco de dados **PostgreSQL**.  
Enquanto o serviço de veículos lida com o cadastro e controle de automóveis, o serviço de clientes centraliza os dados dos usuários da plataforma, permitindo futuras integrações entre clientes e seus respectivos veículos.

---

## ✅ Pré-requisitos

Antes de subir este projeto, você deve ter rodado o projeto [**veiculo**](https://github.com/fabriciofsousa/FIAP-carStore-veiculo.git), pois ele é o responsável por criar o banco de dados e a rede.

---

## ▶️ Para rodar localmente

> ⚠️ **Importante:** Este projeto **não cria o banco de dados**, apenas sobe a aplicação de clientes.  
> Ele se conecta ao **mesmo PostgreSQL** que já foi iniciado pelo projeto `veiculo`.

1. Certifique-se de que o projeto `veiculo` já está rodando:

```bash
cd ../FIAP-carStore-veiculo
docker compose up -d
```

2. Baixe a imagem da aplicação **clientes** do Docker Hub:

```bash
docker pull fabriciofsousa/fiap-carstore-clientes:latest
```

3. Suba os containers do projeto `clientes`:

```bash
docker compose up -d
```

Isso irá criar:
- Um container da aplicação **clientes**

A aplicação já estará conectada ao **Postgres existente**.

---

## 🌐 Rede compartilhada

Este projeto usa a rede **externa** `carstore-network`, criada no compose do `veiculo`.  
Assim, ambas as aplicações (`veiculo` e `clientes`) compartilham o mesmo banco `postgres_carstore`.

---

## 🧩 Arquitetura e Benefícios

Este módulo segue o mesmo padrão de arquitetura limpa (Clean Architecture) aplicado em todo o ecossistema CarStore:
- **Camada de domínio:** concentra as regras de negócio dos clientes.
- **Camada de infraestrutura:** implementa integrações com banco de dados PostgreSQL e serviços externos, como o AWS Cognito.
- **Camada de interface:** expõe as APIs REST e integrações com os outros microsserviços.

Essa separação garante **facilidade de manutenção, testes e escalabilidade**.

---

## 🔗 Endpoints

- Swagger Clientes: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

## 📦 Collection Postman

> * [Fiap-Carstore.postman_collection.json](src%2Fmain%2Fresources%2FFiap-Carstore.postman_collection.json)
 
> * [workspace.postman_globals.json](src%2Fmain%2Fresources%2Fworkspace.postman_globals.json)*

