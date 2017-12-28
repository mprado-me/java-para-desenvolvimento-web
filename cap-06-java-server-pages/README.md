# Capítulo 6: Java Server Pages

* **JSP:** Linguagem que mistura html e java para renderização de templates html:
```
<html>
	<body>
		<%-- comentário em JSP aqui: nossa primeira página JSP --%>
		<%
			String mensagem	= "Bem vindo ao sistema de agenda do FJ-21!";
		%>
		<%	out.println(mensagem);	%>
		<br/>
		<%
			String desenvolvido = "Desenvolvido por	(SEU NOME AQUI)";
		%>																
		<%= desenvolvido %>
		<br/>
		<%
			System.out.println("Tudo foi executado!");
		%>
	</body>
</html>
```
* Import e for com JSP:
```
<%@	page import="java.util.*, br.com.caelum.jdbc.*"%>
<html>
<body>
	<table>
		<%
		ContatoDao dao = new ContatoDao();                       
		List<Contato> contatos = dao.getLista();                       
		for (Contato contato : contatos ) {                       
		%>
			<tr>
				<td><%=contato.getNome() %></td>
				<td><%=contato.getEmail() %></td>
				<td><%=contato.getEndereco() %></td>
				<td><%=contato.getDataNascimento().getTime() %></td>
			</tr>
		<%
		}                       
		%>
	</table>
</body>
</html>
```
* Exibindo um parâmetro enviado no request com JSP:
```
<html>
<body>
	Testando seus parâmetros:
	<br /> A idade é ${param.idade}.
</body>
</html>
```
* **Scriptlet:** Código java dentro de um arquivo .jsp

### Observações:

* Os arquivos .jsp não são compilados no Eclipse, mas em um compilador embutido no Tomcat, que transforma cada arquivo .jsp em um Servlet.