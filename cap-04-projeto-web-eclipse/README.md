# Capítulo 4: Projeto web no eclipse

* **Estrutura dos arquivos de uma aplicação web dinâmica:**
	* src - código fonte Java ( .java ) 
	* build - onde o Eclipse compila as classes ( .class ) 
	* WebContent - content directory (páginas, imagens, css etc vão aqui) 
	* WebContent/WEB-INF/ - pasta oculta com configurações e recursos do projeto 
	* WebContent/WEB-INF/lib/ - bibliotecas  .jar  
	* WebContent/WEB-INF/classes/ - arquivos compilados são copiados para cá

### Observações:

* Apenas os arquivos na pasta WebContent, com exceção dos arquivos e pastas em WEB-INF, estão visiveis para quem acessa o servidor;
* O servidor Tomcat pode conter vários projetos web, cada projeto web pode ser acessado com uma url a partir do nome do projeto. No nosso caso, o projeto web fj21-agenda pode ser acessado em [http://localhost:8080/fj21-agenda/](http://localhost:8080/fj21-agenda/)
