package com.esl.dadosbrasil.DAOteste;

import org.junit.Test;

import com.esl.sysdog.dao.AnimalDAO;
import com.esl.sysdog.dao.AvatardogDAO;
import com.esl.sysdog.dao.ClienteDAO;
import com.esl.sysdog.model.Animal;
import com.esl.sysdog.model.Avatardog;
import com.esl.sysdog.model.Cliente;



public class AnimalDAOtest {

	@Test
	public void salvar() {
		
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.buscar(4L);
		
		AvatardogDAO avatardogDAO = new AvatardogDAO();
		Avatardog avatardog = avatardogDAO.buscar(11l);
		
		
		Animal animal = new Animal();
		
		
		animal.setCliente(cliente);
		animal.setAgressividade("baixa");
		animal.setIdade(new Short("1"));
		animal.setNome("Itamar");
		animal.setSexo("Macho");
		animal.setTamanho("medio");
		animal.setTipo("Cachorro");
		animal.setRaca("Labrador");
		animal.setAvatardog(avatardog);
		animal.setAlocado(false);
		
		AnimalDAO animalDAO = new AnimalDAO();
		animalDAO.merge(animal);
	}
}
