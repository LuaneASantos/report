### Microsserviço Report 🍷


### 💻 Sobre o projeto
Com esse microsserviço será possivel consultar compras de clientes.

### ⚙️ Funcionalidades

- [x] Relatório:
  - [x] Listar compras ordenadas de forma crescente por valor;
  - [x] Retorna a maior compra do ano;
  - [x] Retorna o Top 3 clientes mais fieis;
  - [x] Adicionar endereço ao cliente;
  - [x] Retorna uma recomendação de vinho baseado nos tipos de vinho que o cliente mais compra;


### 🛠 Técnologias e padrão utilizadas

- Arquitetura padrão MVC;
- Spring Boot;
- Java 17;
- Maven;
- Lombok;
- Swagger;

### 🧭 Rodando a aplicação

#### Clonar o repositório do projeto

```sh
git clone https://github.com/LuaneASantos/report.git
```
Importar o projeto na IDE de sua preferência.

#### Dentro do diretório do projeto, buildar com o Maven
```sh
mvn clean install
```

O projeto baixará as dependências necessárias e buildará com sucesso. Caso não complete com sucesso, verifique o log do build para encontrar possíveis erros.

#### Documentação da API
A documentação da API é feita através do swagger, e quando a aplicação estiver rodando em ambiente local você pode acessá-la pelo [link](http://localhost:8080/swagger-ui/index.html#/)

### 🛠 Tecnologias

### ✅ Testes
Foram desenvolvidos testes unitários para os services e integrados para os controllers. Você pode executar diretamente através da sua IDE.
![image](https://github.com/LuaneASantos/report/assets/40871078/eacf22b2-e526-43e1-875d-93699546c3f6)
