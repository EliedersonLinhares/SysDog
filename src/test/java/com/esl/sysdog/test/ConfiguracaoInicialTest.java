package com.esl.sysdog.test;

import javax.persistence.Persistence;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

import com.esl.sysdog.dao.AvatarDAO;
import com.esl.sysdog.dao.AvatardogDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Avatar;
import com.esl.sysdog.model.Avatardog;
import com.esl.sysdog.model.Usuario;

public class ConfiguracaoInicialTest {
	
	@Test
	public void executarConfiguracoesIniciais() {
		Persistence.createEntityManagerFactory("SysDog");
		
	    salvarAvatares();
		
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
	
	
	
public void salvarAvatares() {
		
	    Avatar av1 = new Avatar();
		av1.setName("av1");
		Avatar av2 = new Avatar();
		av2.setName("av2");
		Avatar av3 = new Avatar();
		av3.setName("av3");
		Avatar av4 = new Avatar();
		av4.setName("av4");
		Avatar av5 = new Avatar();
		av5.setName("av5");
		Avatar av6 = new Avatar();
		av6.setName("av6");
		Avatar av7 = new Avatar();
		av7.setName("av7");
		Avatar av8 = new Avatar();
		av8.setName("av8");
		Avatar av9 = new Avatar();
		av9.setName("av9");
		Avatar av10 = new Avatar();
		av10.setName("av10");
		Avatar av11 = new Avatar();
		av11.setName("av11");
		Avatar av12 = new Avatar();
		av12.setName("av12");
		Avatar av13 = new Avatar();
		av13.setName("av13");
		Avatar av14 = new Avatar();
		av14.setName("av14");
		Avatar av15= new Avatar();
		av15.setName("av15");
		Avatar av16 = new Avatar();
		av16.setName("av16");
		
		
		AvatarDAO avatarDAO = new AvatarDAO();
		avatarDAO.salvar(av1);
		avatarDAO.salvar(av2);
		avatarDAO.salvar(av3);
		avatarDAO.salvar(av4);
		avatarDAO.salvar(av5);
		avatarDAO.salvar(av6);
		avatarDAO.salvar(av7);
		avatarDAO.salvar(av8);
		avatarDAO.salvar(av9);
		avatarDAO.salvar(av10);
		avatarDAO.salvar(av11);
		avatarDAO.salvar(av12);
		avatarDAO.salvar(av13);
		avatarDAO.salvar(av14);
		avatarDAO.salvar(av15);
		avatarDAO.salvar(av16);
		
		
		Avatardog dg1 = new Avatardog();
		dg1.setName("dg1");
		Avatardog dg2 = new Avatardog();
		dg2.setName("dg2");
		Avatardog dg3 = new Avatardog();
		dg3.setName("dg3");
		Avatardog dg4 = new Avatardog();
		dg4.setName("dg4");
		Avatardog dg5 = new Avatardog();
		dg5.setName("dg5");
		Avatardog dg6 = new Avatardog();
		dg6.setName("dg6"); 
		Avatardog dg7 = new Avatardog();
	    dg7.setName("dg7");
	    Avatardog dg8 = new Avatardog();
	    dg8.setName("dg8");
	    Avatardog dg9 = new Avatardog();
	    dg9.setName("dg9");
	    Avatardog dg10 = new Avatardog();
	    dg10.setName("dg10");
	   
	    Avatardog dg11 = new Avatardog();
		dg11.setName("dg11");
		Avatardog dg12 = new Avatardog();
		dg12.setName("dg12");
		Avatardog dg13 = new Avatardog();
		dg13.setName("dg13");
		Avatardog dg14 = new Avatardog();
		dg14.setName("dg14");
		Avatardog dg15 = new Avatardog();
		dg15.setName("dg15");
		Avatardog dg16 = new Avatardog();
		dg16.setName("dg16"); 
		Avatardog dg17 = new Avatardog();
	    dg17.setName("dg17");
	    Avatardog dg18 = new Avatardog();
	    dg18.setName("dg18");
	    Avatardog dg19 = new Avatardog();
	    dg19.setName("dg19");
	    Avatardog dg20 = new Avatardog();
	    dg20.setName("dg20");
		
	    Avatardog dg21 = new Avatardog();
		dg21.setName("dg21");
		Avatardog dg22 = new Avatardog();
		dg22.setName("dg22");
		Avatardog dg23 = new Avatardog();
		dg23.setName("dg23");
		Avatardog dg24 = new Avatardog();
		dg24.setName("dg24");
		Avatardog dg25 = new Avatardog();
		dg25.setName("dg25");
		Avatardog dg26 = new Avatardog();
		dg26.setName("dg26"); 
		Avatardog dg27 = new Avatardog();
	    dg27.setName("dg27");
	    Avatardog dg28 = new Avatardog();
	    dg28.setName("dg28");
	    Avatardog dg29 = new Avatardog();
	    dg29.setName("dg29");
	    Avatardog dg30 = new Avatardog();
	    dg30.setName("dg30");
	    
	    Avatardog dg31 = new Avatardog();
		dg31.setName("dg31");
		Avatardog dg32 = new Avatardog();
		dg32.setName("dg32");
		Avatardog dg33 = new Avatardog();
		dg33.setName("dg33");
		Avatardog dg34 = new Avatardog();
		dg34.setName("dg34");
		Avatardog dg35 = new Avatardog();
		dg35.setName("dg35");
		Avatardog dg36 = new Avatardog();
		dg36.setName("dg36"); 
		Avatardog dg37 = new Avatardog();
	    dg37.setName("dg37");
	    Avatardog dg38 = new Avatardog();
	    dg38.setName("dg38");
	    Avatardog dg39 = new Avatardog();
	    dg39.setName("dg39");
	    Avatardog dg40 = new Avatardog();
	    dg40.setName("dg40");
	    
	    Avatardog dg41 = new Avatardog();
		dg41.setName("dg41");
		
		AvatardogDAO avdao = new AvatardogDAO();
		avdao.salvar(dg1);
		avdao.salvar(dg2);
		avdao.salvar(dg3);
		avdao.salvar(dg4);
		avdao.salvar(dg5);
		avdao.salvar(dg6);
		avdao.salvar(dg7);
		avdao.salvar(dg8);
		avdao.salvar(dg9);
		avdao.salvar(dg10);
		
		avdao.salvar(dg11);
		avdao.salvar(dg12);
		avdao.salvar(dg13);
		avdao.salvar(dg14);
		avdao.salvar(dg15);
		avdao.salvar(dg16);
		avdao.salvar(dg17);
		avdao.salvar(dg18);
		avdao.salvar(dg19);
		avdao.salvar(dg20);
		
		avdao.salvar(dg21);
		avdao.salvar(dg22);
		avdao.salvar(dg23);
		avdao.salvar(dg24);
		avdao.salvar(dg25);
		avdao.salvar(dg26);
		avdao.salvar(dg27);
		avdao.salvar(dg28);
		avdao.salvar(dg29);
		avdao.salvar(dg30);
		
		avdao.salvar(dg31);
		avdao.salvar(dg32);
		avdao.salvar(dg33);
		avdao.salvar(dg34);
		avdao.salvar(dg35);
		avdao.salvar(dg36);
		avdao.salvar(dg37);
		avdao.salvar(dg38);
		avdao.salvar(dg39);
		avdao.salvar(dg40);
		
		avdao.salvar(dg41);
		
	
}
}
