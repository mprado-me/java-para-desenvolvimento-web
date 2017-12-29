# Capítulo 7: Usando Taglibs

* **Taglibs:** Da necessidade de tornar os arquivos .jsp mais legíveis, a Sun criou tags que representavam código em um arquivo .jsp
* **Tag para instanciar objetos Javabeans:**
```
<jsp:useBean id="contato" class="br.com.caelum.agenda.modelo.Contato"/>
${contato.nome}
```
* **JSTL:** JSTL significa JavaServer Pages Standard Tag Library. É uma API  que  encapsulou  em  tags  simples  toda  a funcionalidade  que  diversas  páginas Web precisam, como controle de laços ( fors ), controle de fluxo do tipo  if else , manipulação de dados XML e a internacionalização de sua aplicação.
* **Cabeçalho para JSTL core:** 
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
```
* **Foreach, if, import e formatação de data com JSTL:**
```
<html>
<body>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<c:import url="cabecalho.jsp" />

	<!-- cria o DAO -->
	<jsp:useBean id="dao" class="br.com.caelum.jdbc.ContatoDao" />
	<table>
		<!-- percorre contatos montando as linhas da tabela -->
		<c:forEach var="contato" items="${dao.lista}">
			<tr>
				<td>${contato.nome}</td>
				<td>
					<c:if test="${not empty contato.email}">
						<a href="mailto:${contato.email}">${contato.email}</a>
					</c:if> 
					<c:if test="${empty contato.email}"> 
						E-mail não informado
					</c:if>
				</td>
				<td>${contato.endereco}</td>
				<td>
					<fmt:formatDate value="${contato.dataNascimento.time}" pattern="dd/MM/yyyy" />
				</td>
			</tr>
		</c:forEach>
	</table>

	<c:import url="rodape.jsp" />
</body>
</html>
```
* **Utilizando uma tag para especificar o caminho absoluto até um recurso:**
```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<img src="<c:url value="/imagens/caelum.png"/>" />
```
* **Outras tags:**
	* c:catch - bloco do tipo try/catch 
	* c:forTokens - for em tokens (ex: "a,b,c" separados por vírgula) 
	* c:out - saída 
	* c:param - parâmetro 
	* c:redirect - redirecionamento 
	* c:remove - remoção de variável 
	* c:set - criação de variável

### Observações:

* Em uma aplicação Java Web, os arquivos .jar devem ficar na pasta lib dentro do diretório WEB-INF