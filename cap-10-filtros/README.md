# Capítulo 10: Filtros

* **Ideia do MVC no Java:** Separar a lógica do negócio (Servlet) da apresentação (JSP): 

![alt text](https://i.imgur.com/cF9s42P.png)

* **Servlet centralizador (Controller):** Para se evitra o uso de múltiplos Servlets (mpultiplas portas), utilzia-se um Servelet principal:
```
@WebServlet("/sistema")
public class ControllerServlet extends HttpServlet {
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		String parametro = request.getParameter("logica");
		String nomeDaClasse = "br.com.caelum.mvc.logica." + parametro;
		try {
			Class<?> classe = Class.forName(nomeDaClasse);
			Logica logica = (Logica) classe.newInstance();
			// Recebe o String após a execução da lógica
			String pagina = logica.executa(request, response);
			// Faz o forward para a página JSP
			request.getRequestDispatcher(pagina).forward(request, response);
		} catch (Exception e) {
			throw new ServletException("A lógica de negócios causou uma exceção", e);
		}
	}
}
```
* **Passando um parâmetro do Servlet para o JSP:**
```
public class ListaContatosLogic implements Logica {             
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception { 
		// Monta a lista de contatos                     
		List<Contato> contatos = new ContatoDao().getLista();                     
		// Guarda a lista no request                     
		
		req.setAttribute("contatos", contatos);                     
		
		return "lista-contatos.jsp";             
	}
}
```

### Observações:

* Cada classe que implementa uma ação e é instanciada pelo Servlet central, deve implementar a interface Logica e deve retornar a string com o nome do arquivo .jsp que o cliente receberá:
```
public interface Logica {
	String executa(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
```
* Os arquivos .jsp que devem passar por um Servlet (para terem os dados) devem ser escondidos na pasta /WEB-INF/jsp.
