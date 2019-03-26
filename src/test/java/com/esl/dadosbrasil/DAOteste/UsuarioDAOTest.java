package com.esl.dadosbrasil.DAOteste;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Ignore;
import org.junit.Test;

import com.esl.sysdog.dao.AvatarDAO;
import com.esl.sysdog.dao.AvatardogDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Avatar;
import com.esl.sysdog.model.Avatardog;
import com.esl.sysdog.model.Usuario;



public class UsuarioDAOTest {
	
	@Test

	public void salvar() {
		
		
		
		
		AvatarDAO avatarDAO = new AvatarDAO();
		Avatar avatar1 = avatarDAO.buscar(1l);
		Avatar avatar3 = avatarDAO.buscar(3l);
		
		
		Usuario usuario1 = new Usuario();
		usuario1.setNome("Anderson Martins");
		usuario1.setEmail("anderson@hotmail.com");
		usuario1.setAtivo(true);
		usuario1.setSenhaSemCriptografia("aaa");
		usuario1.setLogin("adr");
		Sha256Hash hash= new Sha256Hash("sha256",usuario1.getSenhaSemCriptografia(),5000);
		usuario1.setSenha(hash.toHex());
		usuario1.setTipo('F');
		usuario1.setAvatar(avatar1);
		
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Carlos Cabral");
		usuario2.setEmail("carloscabral@globo.com");
		usuario2.setAtivo(true);
		usuario2.setSenhaSemCriptografia("ccc");
		usuario2.setLogin("maj");
	   Sha256Hash hash2= new Sha256Hash("sha256",usuario2.getSenhaSemCriptografia(),5000);
		usuario2.setSenha(hash2.toHex());
		usuario2.setTipo('A');
		usuario2.setAvatar(avatar3);
		
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario1);
		usuarioDAO.salvar(usuario2);
		
		
		
	}

	@Test
	@Ignore
	public void autenticar() {
		String login = "maj";
		String senha = "ccc";
		
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario  usuario = usuarioDAO.autenticar(login, senha);
		
		
		System.out.println(usuario.getNome());
			
		}
	
	@Test
	@Ignore
	public void Quantidadecadastros() {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Long  total = usuarioDAO.getTotalRegistros();
		
		System.out.println(total);
	}
	
	
	
    
	
}
