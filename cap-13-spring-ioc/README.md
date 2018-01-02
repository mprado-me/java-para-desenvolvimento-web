# Capítulo 13: Spring IoC e Deploy de Aplicação

* **Injeção de Depêndencia:** A classe A depende (deve possuir uma instância) de um objeto da classe B. A injeção de depêndencia ocorre quando removemos a responsabilidade de A instanciar um objeto da classe B. A apenas recebe o objeto da classe B que deve utilizar.
* **Container de Injeção de Dependências:** Gerencia as depêndencias (instância os objetos, na ordem correta, e os envia para os construtores) e amarra tudo. O Spring também é um container de injeção de dependências, por isso também é conhecido como Container IoC (Inversion of Control) ou Container DI.
* **Indicando para o Spring que ele precisa injetar a dependência:** Usa-se a anotação @Autowired:
    * No construtor (preferível):
    ```
    @Controller
    public class TarefasController {
        private JdbcTarefaDao dao;
        
        @Autowired
        public TarefasController(JdbcTarefaDao dao) {
            this.dao = dao;  
        }
        //métodos omitidos
    }
    ```
    * No parâmetro:
    ```
    @Controller
    public class TarefasController {
         @Autowired
    private JdbcTarefaDao dao;
        //métodos omitidos
    }
    ```
    * Em um método específico:
    ```
    @Controller
    public class TarefasController {
        private JdbcTarefaDao dao;

        //sem construtor
        
        @Autowired
        public void setTarefaDao(JdbcTarefaDao dao) {
            this.dao = dao;
        }
    }
    ```
* **Indicando para o Spring que ele deve instanciar objetos de determinada classe:**
```
@Repository
public class JdbcTarefaDao {
    private final Connection connection;
    @Autowired
    public JdbcTarefaDao(Connection connection) {
        this.connection = connection;
    }
    //métodos omitidos
}
```
* **Indicando para o Spring com instanciar o objeto de conexão com o banco de dados:** 
    * Adicionar em spring-context.xml:
    ```
    <bean id="mysqlDataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost/fj21"/>
        <property name="username" value="root"/>
        <property name="password" value="<SENHA DO BANCO AQUI>"/>
    </bean>
    ```
    * Na classe que usa a conexão:
    ```
    @Repository
    public class JdbcTarefaDao {
        private final Connection connection;
        @Autowired
        public JdbcTarefaDao(DataSource dataSource) {
            try {
                this.connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //métodos omitidos
    }
    ```
* **Usando a notação padronizada de injeção (JSR-330):** Nesse caso, é necessário adicionar o JAR da especificação (java.inject) no classpath. Dessa forma pode-se substituir a anotação @Autorwired com @Inject:
```
@Controller
public class TarefasController {
    private JdbcTarefaDao dao;
    @Inject
    public TarefasController(JdbcTarefaDao dao) {
        this.dao = dao;
    }
    //métodos omitidos
}
```
* **Deploy do projeto:** Para fazer o deploy do projeto basta exportar o arquivo .war (Clique com botão direito no projeto -> Export -> WAR file) e colar o arquivo .war gerado na pasta webapps do Tomcat.
