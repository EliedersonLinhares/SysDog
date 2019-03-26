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

import com.esl.sysdog.dao.FornecedorDAO;
import com.esl.sysdog.model.Fornecedor;





@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FornecedorBeanLazy implements Serializable{

	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;

	private FornecedorDAO fornecedorDAO;

	private LazyDataModel<Fornecedor> dataModel;
	
	public LazyDataModel<Fornecedor> getDataModel() {
		return dataModel;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}



  public FornecedorBeanLazy() {
	dataModel = new LazyDataModel<Fornecedor>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Fornecedor> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 fornecedorDAO = new FornecedorDAO();
				 
				 this.setRowCount( fornecedorDAO.getTotalRegistros().intValue());
			     
				 List<Fornecedor> list =  fornecedorDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          //otherwise it will still show all page links; pages at end will be empty
			          this.setRowCount( fornecedorDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
  }
	
	

	public void novo() {
		fornecedor = new Fornecedor();
	}
	
	public void salvar() {
		try {
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
	        fornecedorDAO.merge(fornecedor);
	         
             fornecedor = new Fornecedor();
             fornecedores = fornecedorDAO.listar();

	        Messages.addGlobalInfo("Fornecedor Salvo com Sucesso");
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao salvar o fornecedor");
			erro.printStackTrace();
		}
	}
	
	
	public void editar(ActionEvent evento) {
		fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");
	}
	
}
