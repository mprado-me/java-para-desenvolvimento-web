# Capítulo 3: O que é Java EE

* **Java EE:** Java Entrerprise Edition é um conjunto de especificações de como o software de serviços comuns a toda aplicação web (persistência em banco de dados,  transação, acesso remoto, web services, gerenciamento de  threads,  gerenciamento  de  conexões  HTTP,  cache  de  objetos,  gerenciamento  da  sessão  web, balanceamento de carga, entre outros - requisitos não funcionais) devem ser implementado. Dessa forma o desenvolvedor se concentra apenas em implementar as regras do negócio e a implementação dos requisitos não funcionais são importadas de empresas ou projetos open-source e podem ser trocadas a qualquer momento.
* O Java EE trabalha com APIs para diferentes funções:
	
	1. JavaServer  Pages  (JSP),  Java  Servlets,  Java  Server  Faces  (JSF)  (trabalhar  para  a Web,  onde  é focado este curso) 
	2. Enterprise  Javabeans Components  (EJB)  e  Java  Persistence API  (JPA).  (objetos  distribuídos, clusters, acesso remoto a objetos etc)
	3. Java  API for  XML Web  Services  (JAX-WS),  Java  API for  XML  Binding  (JAX-B)  (trabalhar com arquivos xml e webservices) 
	4. Java Autenthication and Authorization Service (JAAS) (API padrão do Java para segurança) 
	5. Java Transaction API (JTA) (controle de transação no contêiner) 
	6. Java Message Service (JMS) (troca de mensagens assíncronas) 
	7. Java Naming and Directory Interface (JNDI) (espaço de nomes e objetos) 
	8. Java  Management  Extensions  (JMX)  (administração  da  sua  aplicação  e  estatísticas  sobre  a mesma)
* **Download do Java EE:** O Java EE é apenas um PDF com especificações. Há várias implementações do Java EE disponíveis no mercado, conhecidos como servidores de aplicação:

	1. Oracle/Sun, GlassFish Server Open Source Edition 4.0, gratuito, Java EE 7; 	
	2. RedHat, JBoss Application Server 7.x, gratuito, Java EE 6; 
	3. Apache, Apache Geronimo, gratuito, Java EE 6 (não certificado); 
	4. Oracle/BEA, Oracle WebLogic Server 8.x, Java EE 6; 
	5. IBM, IBM WebSphere Application Server, Java EE 6; 
	6. SAP,  SAP  NetWeaver  Application  Server  ou  SAP Web  Application  Server,  Java  EE  6 Web Profile;
* **Servlet Container:** Implementação do Java EE apenas com as funcionalidade clássicas de aplicações web. O mais famoso deles é o Apache Tomcat.

### Observações:

* O código apenas cria um servidor baseado no Tomcat, contém apenas configurações, nenhuma implementação.
