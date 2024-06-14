# Projeto 2
# Gestão de Atletas e Treinadores em Ginásio

Este projeto foi desenvolvido como parte da Unidade Curricular de Projeto 2 do Instituto Politécnico de Viana do Castelo. O objetivo é criar um sistema de gestão para atletas e treinadores num ginásio, disponível em versões desktop e web.

## Tecnologias Utilizadas

- PostgreSQL
- Java
- Java Persistence API (JPA)
- JavaFX
- Spring Web MVC e Thymeleaf

## Estrutura do Projeto

### Base de Dados

O primeiro passo foi criar a base de dados no PostgreSQL. A estrutura da base de dados foi concebida para armazenar informações sobre atletas, treinadores, aulas e as associações entre eles.

### Entidades e Persistência

Utilizando o Java Persistence API (JPA), criámos as entidades que representam as tabelas da base de dados. Estas entidades foram então mapeadas para classes Java que facilitam a interação com a base de dados.



### Versão Desktop

A versão desktop foi desenvolvida utilizando JavaFX para criar uma interface gráfica rica e interativa. A aplicação desktop permite aos utilizadores:

- Gerir informações de atletas e treinadores
- Agendar, editar e cancelar aulas
- Visualizar horários e disponibilidade

### Versão Web

A versão web foi desenvolvida utilizando Spring Web MVC e Thymeleaf. Esta versão oferece funcionalidades semelhantes à versão desktop, permitindo aos utilizadores gerir informações e horários através de um navegador web.

## Funcionalidades

### Aulas

- **Agendar Aulas**: Permite aos treinadores agendar novas aulas, especificando o nome, data, hora, duração e local.
- **Gerir Aulas**: Permite editar e cancelar aulas já agendadas.
- **Visualizar Aulas**: Exibição de uma lista de aulas com detalhes como data, hora, modalidade, local, tipo de aula, sócio participante e estado.

### Treinadores e Atletas

- **Gestão de Treinadores**: Adição, edição e remoção de informações de treinadores.
- **Gestão de Atletas**: Adição, edição e remoção de informações de atletas.
- **Associação de Aulas**: Associar atletas a aulas individuais ou em grupo.

Desenvolvido por: Francisco Santos [@FranciscoSantos1](https://github.com/FranciscoSantos1), João Araújo [jlimaaraujo](https://github.com/jlimaaraujo)

Instituto Politécnico de Viana do Castelo (IPVC) - 2024
