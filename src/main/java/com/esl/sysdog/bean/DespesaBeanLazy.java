package com.esl.sysdog.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.DespesaDAO;
import com.esl.sysdog.model.Despesa;







@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DespesaBeanLazy implements Serializable{

	private Despesa despesa;
	private List<Despesa> despesas;

	private DespesaDAO despesaDAO;

	private LazyDataModel<Despesa> dataModel;
	
	public LazyDataModel<Despesa> getDataModel() {
		return dataModel;
	}
	
  public Despesa getDespesa() {
		return despesa;
	}
	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}
	public List<Despesa> getDespesas() {
		return despesas;
	}
	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}





public DespesaBeanLazy() {
	dataModel = new LazyDataModel<Despesa>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Despesa> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 despesaDAO = new DespesaDAO();
				 
				 this.setRowCount( despesaDAO.getTotalRegistros().intValue());
			     
				 List<Despesa> list =  despesaDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          
			          this.setRowCount( despesaDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
  }
	
	

	public void novo() {
		despesa= new Despesa();
		despesa.setStatus("Aberto");
		despesa.setDataInclusao(new Date());
	}
	
	public void salvar() {
		try {
			
			DespesaDAO despesaDAO = new DespesaDAO();
	        despesaDAO.merge(despesa);
	         
             despesa = new Despesa();
             despesas = despesaDAO.listar();
             despesa.setStatus("Aberto");
             despesa.setDataInclusao(new Date());

	        Messages.addGlobalInfo("Despesa salva com Sucesso");
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao salvar a despesa");
			erro.printStackTrace();
		}
	}
	
	public void ConfirmarPagamento() {
		try {
			
			despesa.setStatus("Pago");
			DespesaDAO despesaDAO = new DespesaDAO();
	        despesaDAO.merge(despesa);
	         
             despesa = new Despesa();
             despesas = despesaDAO.listar();
             
         

	        Messages.addGlobalInfo("Despesa paga com Sucesso");
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao confimar o pagamento da despesa");
			erro.printStackTrace();
		}
	}
	
	
	
	public void editar(ActionEvent evento) {
		despesa = (Despesa) evento.getComponent().getAttributes().get("despesaSelecionada");
	}
	
	
}
