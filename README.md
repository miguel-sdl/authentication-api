# Authentication API

## Descrição
Esse projeto é uma API desenvolvida em `Java` que implementa autenticação e autorização utilizando **Spring Security** e **JWT**. Foi utilizado **Docker** e **MySQL** para o banco de dados e **Maven** para gerenciamento de  dependências.
<br><br>
O sistema permite operações de **CRUD** para `Car` exigindo autenticação de **USER** ou de **ADMIN** dependendo da operação. O objetivo desse projeto é colocar em prática os conhecimentos adquiridos de `Spring` para construção de APIs.

## Endpoints
A API possui os seguintes endpoints:
 - `POST /car`: Cadastra um novo Carro.
   
 - `GET /car`: Retorna todos Carros cadastrados.
 - `GET /car/{id}`: Retorna o Carro com id especificado.
 - `GET /car/find?`:  Retorna os Carros com o nome passado como parâmetro.
 - `PUT /car`: Atualiza os dados de um Carro.
 - `DELETE /car/{id}`: Remove um Carro.
 - `POST /auth/register`: Cadastra um novo usuário.
 - `POST /auth/login`: Verifica as credenciais e caso sejam validas retornam um token JWT.


Os endpoints `/auth/register` e `/auth/login` não exigem autenticação, enquanto para os demais é necessário. Já para os endpoints com operação de **PUT** e **DELETE** é preciso estar autenticado como **ADMIN**.<br><br>
Após iniciar a aplicação é possível acessar http://localhost:8080/swagger-ui/index.html#/ para mais informações sobre os endpoints.

## Tecnologias Utilizadas
- *Java 21*
- *Spring Web*, *Spring Data*, *Spring Security* e *Spring Validation*
- *Maven*
- *Docker*
- *MySQL*

## Como Executar a Aplicação
**1. Clone o repositorio**
```
git clone https://github.com/miguel-sdl/authentication-api.git
```
**2. Inicialize o container docker**
```
docker-compose up -d
```
**3. Execute a aplicação**
```
mvn spring-boot:run
```

## Testes
Nesse projeto foram implementados testes **Unitarios** e de **Integração** que cobrem:
- Testes de operações CRUD.
- Testes de verificação de campos.
- Testes de tratamento de exceções.
- Testes de criação e validação de tokens JWT.
- Testes de autenticação e autorização nos endpoints da API.

Para executar os testes unitários:
```
mvn test
```
Para executar os testes de integração:
```
mvn test -P integration-tests
```
## Autor
 - Miguel Sousa Dela Libera


