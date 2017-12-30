# Capítulo 8: Tags Customizadas com Tagfiles

* **Tags customizadas:** Podem ser utilizadas para representar uma combinação de tags com uma única tag ou então para encapsular código javascript dentro de uma tag.
* **Exemplo do uso de tags customizadas:**
```
<!-- Sem tag customizada: -->
<input id="dataNascimento" type="text">     
<script>        
	$("#dataNascimento").datepicker();     
</script>
```
```
<!-- Com tag customizada: -->
<campoData id="dataNascimento" />
```
* **Tagfiles:** Tagfiles  nada  mais  são  do  que  pedaços  de  JSP,  com  a  extensão   .tag ,  contendo  o  código  que queremos que a nossa tag gere.
* **Importando e utilizando tags customizadas:**
```
<%@taglib tagdir="/WEB-INF/tags" prefix="caelum" %>

<caelum:campoData id="dataNascimento"/><br/>
```
* **Tag customizada com parâmetro:**
```
<%@ attribute name="id" required="true" %>     

<input id="${id}" name="${id}" type="text">     

<script>         
	$("#${id}").datepicker();     
</script>
```

### Observações:

* As tagfiles devem ficar em WEB-INF/tags/
