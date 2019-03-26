package com.esl.sysdog.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.AvatarDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Avatar;
import com.esl.sysdog.model.Usuario;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBeanLazy implements Serializable {
	
	private Usuario usuario;
	private List<Usuario> usuarios;

	
    private List<Avatar> avatares;
    
    private UsuarioDAO usuarioDAO;
    
private LazyDataModel<Usuario> dataModel;
	
	public LazyDataModel<Usuario> getDataModel() {
		return dataModel;
	}
    
    
    
    
    public List<Avatar> getAvatares() {
		return avatares;
	}
    public void setAvatares(List<Avatar> avatares) {
		this.avatares = avatares;
	}
	
	

    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setDataModel(LazyDataModel<Usuario> dataModel) {
		this.dataModel = dataModel;
	}




	public UsuarioBeanLazy() {
            dataModel = new LazyDataModel<Usuario>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Usuario> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 usuarioDAO = new UsuarioDAO();
				 
				 this.setRowCount( usuarioDAO.getTotalRegistros().intValue());
			     
				 List<Usuario> list =  usuarioDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          //otherwise it will still show all page links; pages at end will be empty
			          this.setRowCount( usuarioDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
    }
	
	
	
	public void novo() {
		try {
			usuario = new Usuario();
		
			
			
			AvatarDAO avatarDAO = new AvatarDAO();
			avatares = avatarDAO.listar();
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo usuário");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		
		try {
			
			
			UsuarioDAO usuarioFuncionarioDAO = new UsuarioDAO();
			
			
			usuarioFuncionarioDAO.merge(usuario);
			
			usuario = new Usuario();
			usuarios = usuarioDAO.listar("tipo");
			
		
			
			AvatarDAO avatarDAO = new AvatarDAO();
			avatares = avatarDAO.listar();
			
			Messages.addGlobalInfo("Usuário salvo com sucesso");
			
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o usuário");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			
			usuario = (Usuario)evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			AvatarDAO avatarDAO = new AvatarDAO();
			avatares = avatarDAO.listar();
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o usuário");
			erro.printStackTrace();
		}
	}
	public void excluir(ActionEvent evento) {

		try {
			usuario = (Usuario)evento.getComponent().getAttributes().get("usuarioSelecionado");
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);

			usuarios = usuarioDAO.listar();

			Messages.addGlobalInfo("Estadia removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover esta estadia");
			erro.printStackTrace();
		}
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
			usuario.setSenhaSemCriptografia("clubdog");
			Sha256Hash hash= new Sha256Hash("sha256",usuario.getSenhaSemCriptografia(),5000);
			usuario.setSenha(hash.toHex());
			usuarioDAO.merge(usuario);
		
			
			
			
			Messages.addGlobalInfo("Primeiro Usuário salvo com sucesso, use o login e senha cadastrados");
			
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o usuário");
			erro.printStackTrace();
		}
	}

public void salvarsenha() {
	
	try {
		
		
		UsuarioDAO usuarioFuncionarioDAO = new UsuarioDAO();
		
		Sha256Hash hash= new Sha256Hash("sha256",usuario.getSenha(),5000);
		usuario.setSenha(hash.toHex());
		usuarioFuncionarioDAO.merge(usuario);
		
		usuario = new Usuario();
		usuarios = usuarioDAO.listar("tipo");
		
	
		
		AvatarDAO avatarDAO = new AvatarDAO();
		avatares = avatarDAO.listar();
		
		Messages.addGlobalInfo("Senha alterada com sucesso");
		
		
	}catch(RuntimeException erro) {
		Messages.addGlobalError("Ocorreu um erro ao tentar salvar o usuário");
		erro.printStackTrace();
	}
}
}