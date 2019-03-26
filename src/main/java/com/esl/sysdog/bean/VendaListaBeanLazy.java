package com.esl.sysdog.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.VendaDAO;
import com.esl.sysdog.model.Cliente;
import com.esl.sysdog.model.ItemVenda;
import com.esl.sysdog.model.Produto;
import com.esl.sysdog.model.Venda;
import com.esl.sysdog.util.JpaUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaListaBeanLazy implements Serializable{
	
	private Venda venda;
	
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	
	private List<Cliente> clientes;
	
	
	private List<Venda> vendas;
	
	private VendaDAO vendaDAO;
	
private LazyDataModel<Venda> dataModel;
	
	
	
	public LazyDataModel<Venda> getDataModel() {
		return dataModel;
	}
	
	
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	
	public List<Venda> getVendas() {
		return vendas;
	}
	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}



  public VendaListaBeanLazy() {
	  dataModel = new LazyDataModel<Venda>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Venda> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 vendaDAO = new VendaDAO();
				 
				 this.setRowCount( vendaDAO.getTotalRegistros().intValue());
			     
				 List<Venda> list =  vendaDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          //otherwise it will still show all page links; pages at end will be empty
			          this.setRowCount( vendaDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
}


		
	
  public void imprimir2(ActionEvent evento) throws IOException {
		Venda venda =(Venda) evento.getComponent().getAttributes().get("vendaSelecionada");
			try {
				
		     Long codigo = venda.getCodigo();
			//pegar o caminho do arquivo da memória
			String caminho = Faces.getRealPath("/relatorios/compvenda.jasper");

			Map<String, Object> parametros = new HashMap<>();//Guarda um nome e um valor, com uma estrutura de mapa
		
		    parametros.put("CODIGO_VENDA",codigo );//o valor ' %% ' indica que a tabela toda sera impresa
			
			Connection conexao = JpaUtil.getConexao();
			
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);//gera o relatorio populado
			
			LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String agoraFormatado = agora.format(formatter);
			
			String reportname = "venda " + venda.getCodigo() +" " + agoraFormatado;
			
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			  response.reset();
			  response.setContentType("application/pdf");
			//  response.setHeader("Content-disposition", "attachment; filename=\"report.pdf\"");
			  response.setHeader("Content-disposition", "attachment; filename=\""+ reportname +".pdf\"");
			  ServletOutputStream stream = response.getOutputStream();
			  JasperExportManager.exportReportToPdfStream(relatorio, stream);
			FacesContext.getCurrentInstance().responseComplete();
			
		}catch(JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um relatório");
			erro.printStackTrace();
		}
		}


	
			
}
/*
 * 1 - Campos Short quando somados entre si ou com inteiro resultam em um inteiro(INT) que não cabe em um short
 * por é necessario somar, sendo necessario fazer o Cast dele para short  
 *           (new Short(itemVenda.getQuantidade() + 1 + "")
 *                              =
 *           new Short(quantidadeAntiga + 1 + String)
 *           new Short(String)
 *                short  
 */

/* 2 - Campos BigDecimal não aceita operadores + / - * , sendo necessario usar suas proprias operações
 * e somente com outo campo BigDecimal, sendo preciso usar um cast com New
 */
