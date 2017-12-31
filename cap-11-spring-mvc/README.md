# Capítulo 11: Spring MVC

* **Porque usar uma framework:** Com o uso de frameworks, o desenvolvedor se concentra apenas na lógica do negócio e não em tarefas de infraestrura e compatibilidade como, passar as datas do formato texto para o formato data do Java, criar um objeto Javabean a partir dos parâmtros no request HTTP etc.
* **XML específico do Spring:** O framework Spring possui sua própria configuração XML. O Spring, por ser muito mais do que um controlador MVC, poderia ser utilizado em ambientes não Web, ou seja nem sempre o Spring pode se basear no  web.xml, para tanto o arquivo spring-context.xml deve ficar na pasta WEB-INF. Nesse arquivo estabelecemos que os arquivos .jsp devem ficar na pasta WEB-INF/views. Nesse arquivo também é definido o pacote onde estarão os controllers.:
```
<?xml version="1.0" encoding="UTF-8"?> 
<beans xmlns="http://www.springframework.org/schema/beans"     										xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 											xmlns:context="http://www.springframework.org/schema/context"     								xmlns:mvc="http://www.springframework.org/schema/mvc"     										xsi:schemaLocation="http://www.springframework.org/schema/mvc          							http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd         							http://www.springframework.org/schema/beans         											http://www.springframework.org/schema/beans/spring-beans-3.0.xsd         						http://www.springframework.org/schema/context          											http://www.springframework.org/schema/context/spring-context-3.0.xsd">     

	<context:component-scan base-package="br.com.caelum.tarefas" />
	<mvc:annotation-driven />     

	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">    
		<property name="prefix" value="/WEB-INF/views/"/>         
		<property name="suffix" value=".jsp"/>     
	</bean> 
</beans>
```
* **Exemplo de um Controller do Spring que renderiza o arquivo ok.jsp na rota /olaMundoSpring:**
```
@Controller 
public class OlaMundoController {     
	@RequestMapping("/olaMundoSpring")     
	public String execute() {         
		System.out.println("Executando a lógica com Spring MVC");         
		return "ok";     
	} 
}
```
* **Construção automática de um Javabean com Spring:** Baseado no nome do input no formulário e no nome do parâmtro no objeto Javabean, o Spring é capaz de automaticamente criar um objeto Javabean com os parâmetros fornecidos pelo formulário. Nesse caso a classe Tarefa possui o campo descricao:
```
<html>
<body>
	<h3>Adicionar tarefas</h3>
	<form action="adicionaTarefa" method="post">
		Descrição: <br />
		<textarea name="descricao" rows="5" cols="100"></textarea>
		<br /> <input type="submit" value="Adicionar">
	</form>
</body>
</html>
```
```
@RequestMapping("adicionaTarefa")
public String adiciona(Tarefa tarefa) {
	JdbcTarefaDao dao = new JdbcTarefaDao();
	dao.adiciona(tarefa);
	return "tarefa/adicionada";
}
```
* **Mostrando erros de validação com uma taglib do Spring:**
```
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<body>
	<h3>Adicionar	tarefas</h3>
	<form	action="adicionaTarefa" method="post">
		Descrição:<br/>
		<textarea rows="5" cols="100" name="descricao"></textarea><br/>	
		<form:errors path="tarefa.descricao" cssStyle="color:red"/><br/>
		<input type="submit" value="Adicionar"/>
	</form>
</body>
</html>
```
* **Mensagens internacionalizadas com Spring:** Para centralizar as mensagens e facilitar modificações e internacionalização, cria-se o arquivo messages.properties com as mensagens da aplicação. Deve-se registrar o arquivo nas configurações do Spring (spring-context.xml):
```
<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
	<property name="basename" value="/WEB-INF/messages"/>
</bean>
```
```
tarefa.adicionada.com.sucesso=Tarefa adicionada com sucesso!
tarefa.descricao.pequena=Descrição deve conter pelo menos {min} caracteres
```
```
<fmt:message key="tarefa.adicionada.com.sucesso"/>
```
```
public class Tarefa {     
	private Long id;

	@Size(min=5, message="{tarefa.descricao.pequena}")     
	private String descricao; 
}
```
* **Passando um objeto do controller para o .jsp:**
	* Opção 1:
	```
	@RequestMapping("listaTarefas")
	public ModelAndView lista() {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		List<Tarefa> tarefas = dao.lista();	
		ModelAndView mv = new ModelAndView("tarefa/lista");
		mv.addObject("tarefas", tarefas);
		return	mv;
	}
	```
	* opção 2:
	```
	@RequestMapping("listaTarefas")     
	public String lista(Model model) {         
		JdbcTarefaDao dao = new JdbcTarefaDao();         
		List<Tarefa> tarefas = dao.lista();          
		model.addAttribute("tarefas", tarefas);         
		return "tarefa/lista";     
	}
	```
* **Redirecioanmento:**
	* No servidor:
	```
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.remove(tarefa);
		return "forward:listaTarefas";
	}
	```
	* No cliente:
	```
	@RequestMapping("removeTarefa")
	public String remove(Tarefa tarefa) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.remove(tarefa);
		return "redirect:listaTarefas";
	}
	```
* **Método de resposta do servidor para uma requisição AJAX:** @ResponseBody retorna status 200 caso nenhuma exceção ocorra:
```
@ResponseBody 
@RequestMapping("finalizaTarefa") 
public void finaliza(Long id) {   
	JdbcTarefaDao dao = new JdbcTarefaDao();   
	dao.finaliza(id); 
}
```
* **Para que os controllers do Spring lidem apenas com lógica e não com recursos:** Deve-se adicionar a seguinte linha ao arquivo spring-context.xml:
```
<mvc:default-servlet-handler/>
```

### Observações:

* Um mesmo controller pode possuir vários métodos de renderização, basta que eles possuam a annotation @RequestMapping("/rota")
* A partir do Java EE 6 é possível validar Javabeans com annotations:
```
public class Tarefa {
	private Long id;
	@Size(min=5)
	private	String descricao;
	private boolean finalizado;
	private	Calendar dataFinalizacao;
	//...
}
```
```
@RequestMapping("adicionaTarefa")
public String adiciona(@Valid Tarefa tarefa) {
	JdbcTarefaDao dao = new JdbcTarefaDao();
	dao.adiciona(tarefa);
	return "tarefa/adicionada";
}
```
* Para não exibir uma mensagem de erro para o cliente ao ocorrer um erro em um dos campos, deve-se usar um objeto do tipo BindResult:
```
@RequestMapping("adicionaTarefa")
public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
	if(result.hasFieldErrors("descricao"))	{
		return "tarefa/formulario";
	}
	// Pode-se verificar erros em qualquer um dos campos:
	// if(result.hasErrors()) {
	//  	return "tarefa/formulario";
	// }
	JdbcTarefaDao dao = new JdbcTarefaDao();
	dao.adiciona(tarefa);
	return "tarefa/adicionada";
}
```
* AJAX significa Asynchronous Javascript and XML.
