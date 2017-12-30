package br.com.caelum.mvc.logica;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.jdbc.Contato;
import br.com.caelum.jdbc.ContatoDao;

public class ListaContatosLogic implements Logica {             
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception { 
		// Monta a lista de contatos                     
		List<Contato> contatos = new ContatoDao().getLista();                     
		// Guarda a lista no request                     
		
		req.setAttribute("contatos", contatos);                     
		
		return "/WEB-INF/jsp/lista-contatos.jsp";             
	}         
}
