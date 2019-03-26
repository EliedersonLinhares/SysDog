package com.esl.sysdog.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.esl.sysdog.bean.AutenticacaoBean;
import com.esl.sysdog.model.Usuario;



@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		String paginaAtual = Faces.getViewId();//pega a pagina atualmente vizualizada  para saber se ela é publica ou protegida
		
		
		boolean paginaDeAutenticacao = paginaAtual.contains("login.xhtml");//testa se a pagina atual é a pagina de login usando um boolean
		
		
		
		if(!paginaDeAutenticacao ) {// ! é igual 'not' , não é a pagina de login
			AutenticacaoBean autenticacaoBeanFuncionario = Faces.getSessionAttribute("autenticacaoBean");// pegando com o ominifaces o usuario autenticado criado no Auteticacaobean.java
		
			if(autenticacaoBeanFuncionario == null) {//se o bean ainda não foi criado(null) é redireciionado para  a pagina de login
				Faces.navigate("/pages/login.xhtml");
				return;
			}
				
			//Se o bean já foi criado
			Usuario usuarioFuncionario = autenticacaoBeanFuncionario.getUsuarioLogado();//pega o usuario logado
			if(usuarioFuncionario == null) {//se é 'null' é poque não conseguiu logar então é redirecionado para a pagina de login
				Faces.navigate("/pages/login.xhtml");
			}
			
		
			//adiciona a restrição de impedir o usuario sem permissao acessar paginas bloqueadas pelo rendered do primefaces diretamente pela html,
			boolean paginaDeAdministrador = paginaAtual.contains("adm");//testa se a pagina tem alguma parte escrita "adm" indicando ser de nivel administrador 
			if(paginaDeAdministrador == true) {//se for true
		
					
				Character  usuarioADM = autenticacaoBeanFuncionario.getUsuarioLogado().getTipo();//pega o dado do campo 'tipo' do usuario atualmente logado
				
					
				
				if(usuarioADM != 'A') {//se for diferente de 'A' de administrador
					Messages.addFlashGlobalError("Você não tem permissão para acessar essa página");
					Faces.navigate("/pages/clientes.xhtml");//redireciona ele de volta para a pagina principal
				}
				}
			}
		}
		

	@Override
	public void beforePhase(PhaseEvent arg0) {
		
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
