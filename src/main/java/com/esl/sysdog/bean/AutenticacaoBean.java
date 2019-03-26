package com.esl.sysdog.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.esl.sysdog.dao.AvatarDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Avatar;
import com.esl.sysdog.model.Usuario;



@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {
 
     private Usuario usuario;	
     private  Usuario usuarioLogado;
     
     
     public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}



	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}



	@PostConstruct
     public void iniciar() {
    	 usuario = new Usuario();
		 
		
    	
     }
     
     
     /*
      * processo de autenticação do usuario
      */
     public void autenticar() {
    	 try {
			
    	
    		 UsuarioDAO usuarioDAO = new UsuarioDAO();

    		 usuarioLogado = usuarioDAO.autenticar(usuario.getLogin(), usuario.getSenha());
    		
    		 
    		 if(usuarioLogado == null) {
    			 Messages.addGlobalError("Login e/ou senha incorretos");
    			 return;
    		 }
    		 //Se o campo ativo esta setado para falso, uma vez que nao pode excluir um usuário pode bloquea-lo
    		 if(usuarioLogado.getAtivo() == false) {
    			 Messages.addGlobalError("Usuário bloqueado, contate o administrador");
    			 return;
    			 
    		 }
    		 if(usuarioLogado.getTipo() == 'A') {
    		 Faces.redirect("./pages/clientes.xhtml");
    		 }else if(usuarioLogado.getTipo() == 'F') {
    		 Faces.redirect("./pages/clientes.xhtml");
    		 }
    		 
		} catch (IOException erro) {
			//Messages.addGlobalError("Usuario não encontrado");
			erro.printStackTrace();
		}
     }
     
     /*
      * Finaliza a sessao redirecionando o usuario para a pagina de login
      */
     public void deslogar() {

 		HttpSession autentica = Faces.getSession();
 		autentica.invalidate();
 		Faces.navigate("/pages/login.xhtml");
 		
 	}
     
     /*Desloga o usuario caso fique inativo por 30 minutos, impedindo erros por sessão terminada
      * na pagina modelo5.xhtml é criado um componente p:idleMonitor com 30 minutos que chamara
      * por meio do ajax esse metodo
      * 
      */
     public void onIdle() {
    	deslogar();
        Messages.addGlobalError("Usuario inativo,logue-se novamente");
       
     }
     
     
     
     public boolean temPermissoes(List<String> permissoes) {
    	
    	 
    	 for(String permissao : permissoes) {//pega uma permisssão por ve da lista de permissoes
    		 if(usuarioLogado.getTipo() == permissao.charAt(0)) {//retorna o char com a permissao 
    			 return true;
    		 }
    		 
    	 }
    	 return false;
    	 
     }
     
     public void salvarPrimeiroUsuario() throws IOException {
 		
 		try {
 			
 			
 			UsuarioDAO usuarioDAO = new UsuarioDAO();
 			AvatarDAO avatarDAO = new AvatarDAO();
 			Avatar avatar1 = avatarDAO.buscar(1l);
 			
 			
 			usuario.setNome("MASTER");
 			usuario.setLogin("MASTER");
 			usuario.setAtivo(true);
 			usuario.setTipo('A');
 			usuario.setAvatar(avatar1);
 			usuario.setEmail("MASTER");
 			usuario.setSenhaSemCriptografia("sysdog");
 			Sha256Hash hash= new Sha256Hash("sha256",usuario.getSenhaSemCriptografia(),5000);
 			usuario.setSenha(hash.toHex());
 			usuarioDAO.merge(usuario);
 		
 			
 			
 			
 			Messages.addGlobalInfo("Primeiro Usuário salvo com sucesso, use o login e senha cadastrados");
 			
 			
 		}catch(RuntimeException erro) {
 			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o usuário");
 			erro.printStackTrace();
 		}
 	}
}
