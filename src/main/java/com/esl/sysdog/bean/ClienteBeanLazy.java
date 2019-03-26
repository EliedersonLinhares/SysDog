package com.esl.sysdog.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.ClienteDAO;
import com.esl.sysdog.model.Cliente;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBeanLazy  implements Serializable{
	
	private Cliente cliente;
	private List<Cliente> clientes;
	
	
	private ClienteDAO clienteDAO;
	
private LazyDataModel<Cliente> dataModel;
	
	
	
	public LazyDataModel<Cliente> getDataModel() {
		return dataModel;
	}
	
	
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/*
	 * Parte do código onde é implementado a parte do datatable usando dados no modo "lazy"  usando metodos do DAO especifico
	 * usando uma classe anonima, sem precisar da anotação @Postconstruct
	 */
	
	public ClienteBeanLazy() {
              dataModel = new LazyDataModel<Cliente>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Cliente> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 clienteDAO = new ClienteDAO();
				 
				 this.setRowCount( clienteDAO.getTotalRegistros().intValue());
			     
				 List<Cliente> list =  clienteDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			        
			          this.setRowCount( clienteDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
	}
	
	
	
	
	public void novo() {
		try {
			cliente = new Cliente();
			
			
		}catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
	  }
	}
	public void salvar() {
		try {
			
		    cliente.setEstado(cliente.getEstado().toUpperCase());
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			
			cliente = new Cliente();//limpa o cliente
			
			clientes = clienteDAO.listar("dataCadastro");
		    
		    Messages.addGlobalInfo("Cliente salvo com sucesso");	
		}catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo cliente");
			erro.printStackTrace();
	  }
		
	}
	
	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente)evento.getComponent().getAttributes().get("clienteSelecionado");
			
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar um cliente");
		}
	
	}
	
	/*
	 * Campos de Pesquisa que usa metodos nos DAOs para mostrar enquanto 
	 * o usuário digita opções já cadastradas, usando autocomplete no Primefaces  
	 */
	public List<String> pesquisarBairro(String bairro){
		
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.AutoBairro(bairro);
	}
	
public List<String> pesquisarCidade(String cidade){
		
		ClienteDAO clienteDAO = new ClienteDAO();
		
		return clienteDAO.AutoCidade(cidade);
	}
	
}
