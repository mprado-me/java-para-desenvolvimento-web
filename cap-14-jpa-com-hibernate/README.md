# Capítulo 14: JPA com Hibernate

* **Hibernate:** O Hibernate é uma ferramenta ORM (Object Relational Model) open-source e a líder do mercado, sendo a inspiração para a Jvaa Persistence API (JPA). Uma ORM resolve os seguintes problemas:
    * Trabalho ao mapear um objeto java (classes, atributos, heranças, polimorfismo etc) em uma tabela em um banco de dados (chave primária, chave secundária etc);
    * Programador tem que ficar lidando com banco de dados;
    * SQL ligeiramente diferente para diferentes DBs.
* **Java annotation:** Anotação  é  um  recurso  do  Java  que  permite  inserir  metadados  em relação  a  nossa  classe,  atributos  e métodos. Essas anotações depois poderão ser lidas por frameworks e bibliotecas, para que eles tomem decisões baseadas nessas pequenas configurações.
* **Anotações para o Hibernate construir as tabelas a partir do objeto:**
    ```
    @Entity
    public class Tarefa {
        @Id
        @GeneratedValue
        private Long id;
        private String descricao;
        private boolean finalizado;
        @Temporal(TemporalType.DATE)
        private Calendar dataFinalizacao;
        // métodos...
    }
    ```
    * **@Entity:** Indica que objetos dessa classe se tornem "persistível" no banco de dados;
    * **@Id:** O atributo id é a chave primária na tabela desse objeto;
    * **@GeneratedValue:** A chave é populada pelo banco de dados;
    * **@Temporal(TemporalType.DATE):** Com @Temporal configuramos como mapear um Calendar para o banco, aqui usamos apenas a data (sem hora), mas poderíamos ter usado apenas a hora (TemporalType.TIME) ou timestamp (TemporalType.TIMESTAMP).
* **Convenções de nomenclatura do Hibernate:** O Hibernate usa convenções para nomear as tabelas, colunas etc, porém é possível declarar de forma explicita os nomes desejados:
```
@Column(name = "data_finalizado", nullable = true)
private Calendar dataFinalizacao;
```
```
@Entity 
@Table(name="tarefas")
public class Tarefa {
```
* **O hibernate deve ler algumas configurações do banco de dados e da tabela a partir do arquivo persistence.xml (localizado na pasta META-INF dentro de src!!!):**
```
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
        http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
    version="2.0">
    <persistence-unit name="tarefas">
        <!-- provedor/implementacao do JPA -->
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <!-- entidade mapeada -->
        <class>br.com.caelum.tarefas.modelo.Tarefa</class>
        <properties>
            <!-- dados da conexao -->
            <property name="javax.persistence.jdbc.driver" 
                    value="com.mysql.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" 
                    value="jdbc:mysql://localhost/fj21" />
            <property name="javax.persistence.jdbc.user" 
                    value="root" />
            <property name="javax.persistence.jdbc.password" 
                    value="<SENHA DO BANCO AQUI>" />
            <!--  propriedades do hibernate -->
            <property name="hibernate.dialect" 
                    value="org.hibernate.dialect.MySQL5InnoDBDialect" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <!--  atualiza o banco, gera as tabelas se for preciso -->
            <property name="hibernate.hbm2ddl.auto" value="update" />
        </properties>
    </persistence-unit>
</persistence>
```
* **Download dos jars:** Os jars do Hibernate e do JPA podem ser baixados em www.hibernate.org
* **Usando o JPA:** Deve-se criar um classe (Persistence) para ler o arquivo persistence.xml e as anotações dos objetos java a serem mapeados. A classe Persistence retornará uma EntityManagerFactory:
```
EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
```
* **Criação das tabelas pelo Hibernate:** No arquivo persistence.xml, a propriedade hibernate.hbm2ddl.auto determina se o hibernate irá ou não gerar novas tabelas se for preciso:
```
<!--  atualiza o banco, gera as tabelas se for preciso -->
<property name="hibernate.hbm2ddl.auto" value="update" />
```
* **Método para gerar as tabelas automaticamente:**
```
package br.com.caelum.tarefas.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeraTabelas {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("tarefas");
        factory.close();
    }
}
```
* **Persistindo novos objetos:**
```
Tarefa tarefa = new Tarefa();
tarefa.setDescricao("Estudar JPA");
tarefa.setFinalizado(true);
tarefa.setDataFinalizacao(Calendar.getInstance());

EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
EntityManager manager = factory.createEntityManager();

manager.getTransaction().begin();        
manager.persist(tarefa);
manager.getTransaction().commit();    

System.out.println("ID da tarefa: " + tarefa.getId());

manager.close();
```
* **Buscando um objeto por sua chave primária (ID):**
```
EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
EntityManager manager = factory.createEntityManager();

Tarefa encontrada = manager.find(Tarefa.class, 1L);

System.out.println(encontrada.getDescricao());
```
* **Removendo uma entidade do banco de dados:**
```
EntityManager manager = //abrir um EntityManager

Tarefa encontrada = manager.find(Tarefa.class, 1L);

manager.getTransaction().begin();
manager.remove(encontrada);
manager.getTransaction().commit();
```
* **Atualizando uma entidade do banco de dados:**
```
Tarefa tarefa = new Tarefa();
tarefa.setId(2); //esse id já existe no banco
tarefa.setDescricao("Estudar JPA e Hibernate");
tarefa.setFinalizado(false);
tarefa.setDataFinalizacao(null);

EntityManager manager = //abrir um EntityManager

manager.getTransaction().begin();
manager.merge(tarefa);
manager.getTransaction().commit();
```
* **Buscando com uma cláusula where:**
    * Sem parâmetro:
    ```
    EntityManager manager = //abrir um EntityManager
    List<Tarefa> lista = manager
        .createQuery("select t from Tarefa as t where t.finalizado = false")
        .getResultList();

    for (Tarefa tarefa : lista) {
        System.out.println(tarefa.getDescricao());
    }
    ```
    * Com parâmetro:
    ```
    EntityManager manager = //abrir um EntityManager
        Query query = manager
                .createQuery("select t from Tarefa as t "+
                        "where t.finalizado = :paramFinalizado");
        query.setParameter("paramFinalizado", false);
        List<Tarefa> lista = query.getResultList();
    ```

### Observações:
* Algumas boas práticas ao usar o Hibernate: 
    * **Connection Pool** – Usar o pool de conexões embutido com o Hibernate é um erro comum, e a própria documentação diz que você não deve usa-lo em produção! Pode acontecer até connections leak!
    A Caelum teve ótimas experiências com o C3P0, e é muito fácil configurá-lo como Provider para o Hibernate.
    * **Second Level Cache** – Todos já passamos por situações em que precisamos criar caches para as linhas de banco de dados mais acessadas. Aqui temos diversos problemas: sincronismo, gasto de memória, memory leak, tamanho do cache, política de prioridade da fila (LFU, LRU, FIFO, etc), tempo de expiração e modos de invalidar o cache. Escrever um cache eficiente e seguro é um grande trabalho, imagine ainda dar suporte a um cache distribuído e que possa se aproveitar do disco rígido para não gastar tanta memória? Esse é o papel do second level cache. Você pode usá-lo com diversos providers, sendo o EhCache um dos mais conhecidos.
    * **Query Cache** – Um recurso fantástico do Hibernate. No caso de você ter queries que são executadas inúmeras vezes, você pode pedir para o Hibernate fazer o cache do resultado desta query. O interessante é que ele não vai armazenar todos os objetos resultantes, e sim apenas suas primary keys: no momento que ele precisar executar novamente aquela query, ele já tem todos os IDs resultantes, e através destes ele consulta o second level cache, sem fazer um único hit ao banco de dados! Esse cache será invalidado quando alguma das tabelas envolvidas nesta query for atualizada, ou um determinado tempo passar.
    * **Controle do Lazy** – Algumas pessoas costumam reclamar do lazy loading, dizendo que em alguns casos teria sido melhor ele carregar tudo em uma única query. Você sempre pode redefinir o comportamento desses relacionamentos quando fizer uma query, através de um eager fetch.
    * **Stateless Session** – Algumas vezes precisamos fazer um processamento em batch de objetos, ou mesmo inserir uma quantidade grande deles na base de dados. Em muitos casos uma bulk operation é o suficiente, mas se quisermos manter a Orientação a Objetos, devemos tomar cuidado com a grande quantidade de objetos que ficarão armazenados no first level cache. A StatelessSession resolve esse problema: simplesmente não há first level cache e nenhum objeto se comportará como managed, tendo praticamente o mesmo efeito que chamar entityManager.clear() a cada operação.
    * **Open Session in View** – Na arquitetura MVC, muitas vezes renderizamos em nossa view diversas entidades do nosso modelo, e essas podem ter sido carregas pelo Hibernate. Se essas entidades possuem relacionamentos lazy, precisamos que a sessão esteja aberta no momento da renderização da View, caso contrário teremos uma LazyInitializaionException ou algum código macarrônico para carregar relacionamentos que nem sempre precisamos. Para isso devemos manter a session aberta através de um filtro, interceptador ou algum outro mecanismo. Isso resulta no pattern Open Session in View e também se aplica ao EntityManager.
    O mesmo efeito pode ser obtido através de inversão de controle e injeção de dependências através da anotação @PersistenceContext, que é tratada por containers EJB3 e também por muitos frameworks web, como o Spring. O EJB3 ainda possui o conceito de um contexto de persistência extendido, quem é interessante em casos de conversações longas: o EntityManager usado será o mesmo enquanto aquele stateful session bean não for removido.
    * **Evitando número de queries excessivas (n+1)** – Se uma NotaFiscal possui muitos Items, e essa coleção é lazy, gastaremos duas queries para buscar a NotaFiscal e seus respectivos Itens. Mas se temos uma lista de NotaFiscal resultante de uma query, para cada NotaFiscal teremos uma nova query executada para todo getItems invocados. 1 query para listar NotaFiscal, N queries para pegar os relacionamentos: é o problema das n+1 queries. Você deve usar as configurações de batch-size e fetch-size para pedir ao Hibernate carregar as entidades/relacionamentos em blocos em vez de um em um. Você também pode utilizar o second level cache nesses relacionamentos, diminuindo consideravelmente o número de queries disparada.
* Uma confusão que pode ser feita a primeira vista é pensar que o JPA com Hibernate é lento, pois, ele precisa gerar as nossas queries, ler objetos e suas anotações e assim por diante. Na verdade, o Hibernate faz uma série de otimizações internamente que fazem com que o impacto dessas tarefas seja próximo a nada. Portanto, o Hibernate é, sim, performático, e hoje em dia pode ser utilizado em qualquer projeto que se trabalha com banco de dados.
