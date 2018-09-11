# WebServiceUser

API REST de CRUD de usuários desenvolvido utilizando Java 1.8, Spring Boot e MySQL

**Geração do WAR**

Siga os seguintes passos para geração do WAR:

1. No Eclipse, selecione a opção para importar o projeto em "File > Import...". Selecione "Existing Maven Projects"

<img width="1364" alt="screen shot 2018-09-11 at 08 14 28" src="https://user-images.githubusercontent.com/10779649/45358066-a6a39a00-b59e-11e8-9da3-62f1dcdb8da9.png">

2. Selecione o diretório onde realizou o download do código fonte

<img width="1364" alt="screen shot 2018-09-11 at 08 14 49" src="https://user-images.githubusercontent.com/10779649/45358067-a6a39a00-b59e-11e8-84ff-8651203a10e4.png">

3. Ajuste o arquivo "application.properies" caso seja necessário.
Perceba que a propriedade "ddl-auto" está configurada com valor "create" e por isso, sempre que a aplicação for reiniciada, as tabelas do banco de dados serão recriados.

<img width="1392" alt="screen shot 2018-09-11 at 08 17 43" src="https://user-images.githubusercontent.com/10779649/45358068-a6a39a00-b59e-11e8-92c2-f8acab2efde5.png">

4. Clique em "Run > Run As > Maven Install" para que o WAR seja gerado

<img width="328" alt="screen shot 2018-09-11 at 08 22 00" src="https://user-images.githubusercontent.com/10779649/45358069-a73c3080-b59e-11e8-8198-8703d12f7f5d.png">

5. No término do processo, copiei o arquivo ".WAR" gerado no diretório "target" para o diretório "/webapps/" do TOMCAT

<img width="1392" alt="screen shot 2018-09-11 at 08 23 29" src="https://user-images.githubusercontent.com/10779649/45358070-a73c3080-b59e-11e8-9a5a-17485de31f56.png">

6. Acesse o gerenciador de aplicações do TOMCAT para verificar se a aplicação foi instalada

`http://localhost:8080/manager`

<img width="1392" alt="screen shot 2018-09-11 at 08 27 04" src="https://user-images.githubusercontent.com/10779649/45358073-a7d4c700-b59e-11e8-9ebe-864d1a579acc.png">

**Banco de Dados**

Siga os seguintes passos para configuração do banco de dados da aplicação:

1. Acessar banco de dados

`mysql -u root -p;`

2. Criar banco de dados "cruduser"

`create database cruduser;`

3. Indicar qual banco quer usar

`use cruduser;`

4. Criar usuário "app", senha "app123"

`GRANT ALL PRIVILEGES ON *.* TO 'app'@'localhost' IDENTIFIED BY 'app123';`

Alterar "localhost" pelo IP ou * caso o banco de dados e a aplicação forem instalados em servidores diferentes

**Testes dos Endpoints**

> POST /user/

<img width="1052" alt="screen shot 2018-09-11 at 08 29 23" src="https://user-images.githubusercontent.com/10779649/45358074-a7d4c700-b59e-11e8-89cb-14d692ff2b91.png">

> GET /user/:id

<img width="1052" alt="screen shot 2018-09-11 at 08 29 48" src="https://user-images.githubusercontent.com/10779649/45358075-a86d5d80-b59e-11e8-88e1-64e04edde4bf.png">

> PATCH /user/:id

<img width="1052" alt="screen shot 2018-09-11 at 08 30 18" src="https://user-images.githubusercontent.com/10779649/45358076-a86d5d80-b59e-11e8-9874-f266a0221394.png">

> DELETE /user/:id

<img width="1052" alt="screen shot 2018-09-11 at 08 30 33" src="https://user-images.githubusercontent.com/10779649/45358077-a86d5d80-b59e-11e8-846e-1d6d455bfa1c.png">
