# Capítulo 16: Integração do Spring com JPA

* **Usando o Spring para gerenciar o EntityManager, as transações e os fechamentos:** O Spring pode receber a responsabilidade de se gerenciar a criação do objeto EntityManager e também gerenciar as transações e os fechamentos necessários para se lidar com uma operação com JPA. Dessa forma, pode-se utilizar o Spring para simplificar o código abaixo:
```
EntityManagerFactory factory = Persistence.createEntityManagerFactory("tarefas");
EntityManager manager = factory.createEntityManager();

manager.getTransaction().begin();   

//aqui usa o EntityManager

manager.getTransaction().commit();    
manager.close();
``` 
* **Configurando o JPA no Spring:** Deve-se adicinar ao arquivo spring-context.xml o seguinte bean:
```
<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
    <property name="dataSource" ref="mysqlDataSource" />     
    <property name="jpaVendorAdapter">         
        <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>     
    </property> 
</bean>
```
* **Criando um DAO com Spring e JPA (injeção do Entity Manager):** Para se injetar o EntityManager não se deve usar a marcação @Autowired ou @Inject, mas a marcação @PersistenceContext, que não pode estar no construtor. Dessa forma nossa classe DAO fica muito mais simples.
```
@Repository
public class JpaTarefaDao{
    @PersistenceContext
    private EntityManager manager;

    //sem construtor

    public void adiciona(Tarefa tarefa) {
        manager.persist(tarefa);
    }

    public void altera(Tarefa tarefa) {
        manager.merge(tarefa);
    }

    public List<Tarefa> lista() {
        return manager.createQuery("select t from Tarefa t").getResultList();
    }

    public Tarefa buscaPorId(Long id) {
        return manager.find(Tarefa.class, id);
    }

    public void remove(Tarefa tarefa) {
        Tarefa tarefaARemover = buscaPorId(tarefa.getId());
        manager.remove(tarefaARemover);
    }

    public void finaliza(Long id) {
        Tarefa tarefa = buscaPorId(id);
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(Calendar.getInstance());
        manager.merge(tarefa);
    }
}
```
* **Uso de interfaces para o DAO:** TarefasController poderia usar a implementação JdbcTarefaDao ou JpaTarefaDao, dessa forma é mais interessante TarefasController lidar com a interface TarefaDao:
```
public interface TarefaDao {
    Tarefa buscaPorId(Long id);
    List<Tarefa> lista();
    void adiciona(Tarefa t);
    void altera(Tarefa t);
    void remove(Tarefa t);
    void finaliza(Long id);
}
```
* **Indicando para o Spring qual das implementações deve ser instanciada pelo gerenciador de depêndencias:**
```
@Controller
public class TarefasController {
    private TarefaDao dao; //usa apenas a interface!

    @Autowired
    @Qualifier("jpaTarefaDao")
    public TarefasController(TarefaDao dao) {
        this.dao = dao; 
    }

    //métodos omitidos
}
```
* **Gerenciando a transação:** 
    * Indicando para o Spring (spring-context.xml) que será utilizado o JpaTransactionManager e uso de anotações:
    ```
    <bean id="transactionManager"
                class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <tx:annotation-driven/>
    ```
    * Indicando a transação em um método:
    ```
    @Transactional
    @RequestMapping("adicionaTarefa")
    public String adiciona(@Valid Tarefa tarefa, BindingResult result) {

        if(result.hasFieldErrors("descricao")) {
            return "tarefa/formulario";
        }
        
        dao.adiciona(tarefa);
        return "redirect:listaTarefas";
    }
    ```
    * Indicando a transação na classe (todos os métodos abrem e fecham uma transação):
    ```
    @Transactional
    @Controller
    public class TarefasController {
    ```

### Observações:
* Há um problema ainda, usando o gerenciamento de transação pelo Spring exige a presença do
construtor padrão sem parâmetros, deve-se injetar o dao pelo atributo:
```
package br.com.caelum.tarefas.controller;

//imports

@Controller
@Transactional
public class TarefasController {

    @Autowired
    TarefaDao dao;

    @RequestMapping("novaTarefa")
    public String form() {
.
        return "tarefa/formulario";
    }

    @RequestMapping("adicionaTarefa")
    public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
        if (result.hasFieldErrors("descricao")) {
            return "tarefa/formulario";
        }
        dao.adiciona(tarefa);
        return "tarefa/adicionada";
    }

    @RequestMapping("listaTarefas")
    public String lista(Model model) {
        model.addAttribute("tarefas", dao.lista());
        return "tarefa/lista";
    }

    @RequestMapping("removeTarefa")
    public String remove(Tarefa tarefa) {
        dao.remove(tarefa);
        return "redirect:listaTarefas";
    }

    @RequestMapping("mostraTarefa")
    public String mostra(Long id, Model model) {
        model.addAttribute("tarefa", dao.buscaPorId(id));
        return "tarefa/mostra";
    }

    @RequestMapping("alteraTarefa")
    public String altera(Tarefa tarefa) {
        dao.altera(tarefa);
        return "redirect:listaTarefas";
    }

    @RequestMapping("finalizaTarefa")
    public String finaliza(Long id, Model model) {
        dao.finaliza(id);
        model.addAttribute("tarefa", dao.buscaPorId(id));
        return "tarefa/finalizada";
    }
}
```