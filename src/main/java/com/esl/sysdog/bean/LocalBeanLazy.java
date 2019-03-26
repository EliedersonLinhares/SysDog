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

import com.esl.sysdog.dao.LocalDAO;
import com.esl.sysdog.model.Local;



@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class LocalBeanLazy implements Serializable{

	private Local local;
	private List<Local> locais;
	

	
	private LocalDAO localDAO;
	
    private LazyDataModel<Local> dataModel;
	
	public LazyDataModel<Local> getDataModel() {
		return dataModel;
	}
	
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
	public List<Local> getLocais() {
		return locais;
	}
	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}
	
	/*
	 * Parte do código onde é implementado a parte do datatable usando dados no modo "lazy"  usando metodos do DAO especifico
	 * usando uma classe anonima, sem precisar da anotação @Postconstruct
	 */
	public LocalBeanLazy() {
		dataModel = new LazyDataModel<Local>() {
				
				
				private static final long serialVersionUID = 1L;
				
				 @Override
				  public List<Local> load(int first, int pageSize, String sortField,
				                             SortOrder sortOrder, Map<String, Object> filters) {
					 
					 localDAO = new LocalDAO();
					 
					 this.setRowCount( localDAO.getTotalRegistros().intValue());
				     
					 List<Local> list =  localDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
				      if (filters != null && filters.size() > 0) {
				         
				          this.setRowCount( localDAO.getColunasFiltradas(filters));
				      }
				      return list;
				  }
				
			};
	  }
	
	
	
	
	public void Novo() {
		try {
			
		local = new Local();
		
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo local");
			erro.printStackTrace();
		}
		
	}
	
	public void Salvar() {
		
		try {
			local.setEstado(local.getEstado().toUpperCase());
			LocalDAO localDAO = new LocalDAO();
			localDAO.merge(local);
			
			locais = localDAO.listar();
			
			local = new Local();
			
			Messages.addGlobalInfo("Local salvo com sucesso");
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um local");
			erro.printStackTrace();
		}
		
		
	}
	public void editar(ActionEvent evento) {
		try {
			local = (Local) evento.getComponent().getAttributes().get("localSelecionado");
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar um local");
			erro.printStackTrace();
		}
		
	}
	
	/*
	 * Campos de Pesquisa que usa metodos nos DAOs para mostrar enquanto 
	 * o usuário digita opções já cadastradas, usando autocomplete no Primefaces  
	 */
     public List<String> pesquisarBairro(String bairro){
		
	   LocalDAO localDAO = new LocalDAO();
	   
		return localDAO.AutoBairro(bairro);
	}
	
     public List<String> pesquisarCidade(String cidade){
		
	   LocalDAO localDAO = new LocalDAO();
	
		return localDAO.AutoCidade(cidade);
	}

	
	
}
