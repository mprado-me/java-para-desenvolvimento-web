# Capítulo 5: Servlets

* **Servlets:** Gerador de páginas dinâmicas para a linguagem java. Cada  servlet  é um  objeto  Java  que  recebe requisições HTTP  (request)  e  produz  algo (response), como uma página HTML dinamicamente gerada:
```
public class OiMundo extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log("Iniciando a servlet");
	}
	public void destroy() {
		super.destroy();
		log("Destruindo a servlet");
	}

	protected void service (
			HttpServletRequest request,
			HttpServletResponse response
		) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();         
		
		// escreve o texto         
		out.println("<html>");         
		out.println("<body>");         
		out.println("Primeira servlet");         
		out.println("</body>");         
		out.println("</html>");     
	}

	// O método service aceita get e post, para trabalhar apenas com get ou post, devemos utilizar no lugar do método service os seguintes métodos:
	// void	doGet(HttpServletRequest req, HttpServletResponse res);
	// void	doPost(HttpServletRequest req, HttpServletResponse res);
}
```

### Observações:

* Para o servidor Tomcat ser capaz de trabalhar com uma Servlet, o arquivo web.xml da pasta WEB-INF deve registrar o Servlet e mapeá-lo à url:
```
<servlet>         
	<servlet-name>primeiraServlet</servlet-name>
	<servlet-class>br.com.caelum.servlet.OiMundo</servlet-class>     
</servlet>

<servlet-mapping>
	<servlet-name>primeiraServlet</servlet-name>
	<url-pattern>/oi</url-pattern>
	<!-- 
	ou 
	<url-pattern>/oi/*</url-pattern>
	ou
	<url-pattern>*.php</url-pattern>
	-->
</servlet-mapping>
```
* A partir da especificação Servlet 3.0 / Java EE 6, o mapeamento da url de um Servlet pode ser feito com o uso de annotations:
```
@WebServlet("/oi")         
public class OiServlet3 extends HttpServlet {             
	...         
}

@WebServlet(name = "MinhaServlet3", urlPatterns = {"/oi", "/ola"})         
public class OiServlet3 extends HttpServlet{            
	...         
}
```
* De acordo  com a  especificação  de Servlets,  por  padrão,  existe  uma  única instância  de  cada Servlet declarada. Ao chegar uma requisição para a Servlet, uma nova  Thread  é aberta sobre aquela instância que já existe. Dessa forma, deve-se evitar o uso de variáveis de um Servlet que será compartlhado entre diferentes requests / threads ou então tornar o método service / get / post do servlet sync (gera problemas de escalabilidade).
