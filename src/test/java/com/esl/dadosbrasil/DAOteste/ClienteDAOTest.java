package com.esl.dadosbrasil.DAOteste;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Ignore;
import org.junit.Test;

import com.esl.sysdog.dao.ClienteDAO;
import com.esl.sysdog.model.Cliente;



public class ClienteDAOTest {

	
	@Test
	
	public void salvar() throws ParseException{
		
		Cliente cliente1 = new Cliente();
		cliente1.setNome("Reginaldo Monteiro");
		cliente1.setCpf("111.111.111-11");
		cliente1.setEndereco("Rua das Almodengas, nº21");
		cliente1.setBairro("Copacabana");
		cliente1.setCidade("Rio de Janeiro");
		cliente1.setEstado("RJ");
		cliente1.setCep("22200-000");
		cliente1.setTelefone("(21)2222-2222");
		cliente1.setCelular("(21)22222-2222");
		cliente1.setEmail("reginaldo@uol.com.br");
		cliente1.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("09/06/2018"));
		cliente1.setLiberado(true);
		
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.merge(cliente1);
		
	}
	
	@Test
	@Ignore
	public void editar() {//ok
		
		Long codigo = 1l;
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.buscar(codigo);
		
		if(cliente == null) {
			System.out.println("Nenhum resgistro encontrado");
		}else {
			
			cliente.setNome("Reginaldo Magalhães");
			clienteDAO.merge(cliente);
			System.out.println("Resgistro editado: - Depois:");
			System.out.println("Nome: " + cliente.getNome());
		}
	}
}
