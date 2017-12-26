# Banco de Dados e JDBC

### Conceitos chaves

* **JDBC:** JDBC significa Java DataBase Connection. É uma biblioteca em Java que provê interfaces para que programas em Java não lidem diretamente com os protocolos e peculiaridades de cada banco de dados.
* **Driver:** No contexto do JDBC, drivers são os pacotes que implementam as interfaces do JDBC (classes concretas) para cada banco de dados específico.
* **Javabeans:** Classes com construtor sem argumentos e métodos de acesso do tipo get e set.
* **Fechar conexão:** Ao abrir uma conexão com um banco de dados, deve-se sempre encerrar a conexão aberta, caso contrário recursos computacionais poderam ser disperdiçados e falhas de segurança poderão ser geradas. Deve-se tomar cuidado para encerrar a conexão mesmo quando ocorre exceções no código. Para tanto é recomendado encerrar a conexão no bloco finally de uma instrução try, catch e finally. A partir do Java 7, é possível utilizar a instrução try-with-resources, que fecha os objetos instanciados no try e que implementam AutoCloseable.
* **ORM's Java:** Utilizar statements SQL para trabalhar com banco de dados em Java é trabalhaso e de díficl manutenção. Para facilitar esse processo, utiliza-se as ORM's Java. As mais famosas são JPA e Hibernate.
* **DAO:** DAO significa Data Acess Object. É o nome dado às classes que emcapsulam o acesso ao banco de dados. O cliente de uma classe DAO não interage com instruções SQL do banco de dados, interage apenas com o objeto (classe) que representa uma tabela no banco de dados.
