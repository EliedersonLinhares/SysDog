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

import com.esl.sysdog.dao.AnimalDAO;
import com.esl.sysdog.dao.AvatardogDAO;
import com.esl.sysdog.dao.ClienteDAO;
import com.esl.sysdog.model.Animal;
import com.esl.sysdog.model.Avatardog;
import com.esl.sysdog.model.Cliente;




@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AnimalBeanLazy implements Serializable{
	
	private Animal animal;
	private List<Animal> animais;
	
	private List<Cliente> clientes;
	
	
	private List<Avatardog> avatardogs;
	
	
	private AnimalDAO animalDAO;
	
	private LazyDataModel<Animal> dataModel;
	
	public LazyDataModel<Animal> getDataModel() {
		return dataModel;
	}
	
	
	
	public List<Avatardog> getAvatardogs() {
		return avatardogs;
	}
	public void setAvatardogs(List<Avatardog> avatardogs) {
		this.avatardogs = avatardogs;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public List<Animal> getAnimais() {
		return animais;
	}
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	/*
	 * Parte do código onde é implementado a parte do datatable usando dados no modo "lazy"  com metodos do DAO especifico
	 *e  usando uma classe anonima, sem precisar da anotação @Postconstruct
	 */
	public AnimalBeanLazy() {
		  dataModel = new LazyDataModel<Animal>() {
				
				
				private static final long serialVersionUID = 1L;
				
				 @Override
				  public List<Animal> load(int first, int pageSize, String sortField,
				                             SortOrder sortOrder, Map<String, Object> filters) {
					 
					 animalDAO = new AnimalDAO();
					 
					 this.setRowCount( animalDAO.getTotalRegistros().intValue());
				     
					 List<Animal> list =  animalDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
				      if (filters != null && filters.size() > 0) {
				         
				          this.setRowCount( animalDAO.getColunasFiltradas(filters));
				      }
				      return list;
				  }
				
			};
	}

	
	
	public void novo() {
		
		try {
		animal = new Animal();
		
		ClienteDAO clienteDAO = new ClienteDAO();
		clientes = clienteDAO.listar();
		
		AvatardogDAO avatardogDAO = new AvatardogDAO();
		avatardogs = avatardogDAO.listar();
		
	   animal.setAlocado(false);//seta que o animal esta disponivel para estadia
		
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo animal");
			erro.printStackTrace();
		}
		
	}
	
	public void salvar() {
		
		try { 
			
			
			AnimalDAO animalDAO = new AnimalDAO();
			animalDAO.merge(animal);
			
			
			animal = new Animal();
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
			
			AvatardogDAO avatardogDAO = new AvatardogDAO();
			avatardogs = avatardogDAO.listar();
			
			animais = animalDAO.listar();
			
			Messages.addGlobalInfo("Animal salvo com sucesso");	
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao salvar um novo animal");
			erro.printStackTrace();
		}
		
	}
	
	public void editar(ActionEvent evento) {
		
	try {
		animal = (Animal) evento.getComponent().getAttributes().get("animalSelecionado");
		
		
		ClienteDAO clienteDAO = new ClienteDAO();
		clientes = clienteDAO.listar();
		
		AvatardogDAO avatardogDAO = new AvatardogDAO();
		avatardogs = avatardogDAO.listar();
		
	}catch(RuntimeException erro) {
		Messages.addGlobalError("Ocorreu um erro ao tentar editar um animal");
	}
	}
	
	
	/*
	 * Campos de Pesquisa que usa metodos nos DAOs para mostrar enquanto 
	 * o usuário digita opções já cadastradas, usando autocomplete no Primefaces  
	 */
	
	public List<String> pesquisarTipo(String tipo){
		
		AnimalDAO animalDAO = new AnimalDAO();
		
		return animalDAO.AutoTipo(tipo);

}
	
    public List<String> pesquisarRaca(String raca){
		
		AnimalDAO animalDAO = new AnimalDAO();
		
		return animalDAO.AutoRaca(raca);

}
	
}