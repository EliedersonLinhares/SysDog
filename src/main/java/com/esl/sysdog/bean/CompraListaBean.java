package com.esl.sysdog.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.CompraDAO;
import com.esl.sysdog.model.Cliente;
import com.esl.sysdog.model.Compra;
import com.esl.sysdog.model.ItemCompra;
import com.esl.sysdog.model.Produto;



/*
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
*/

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CompraListaBean implements Serializable{
	
	private Compra compra;
	
	private List<Produto> produtos;
	private List<ItemCompra> itensCompra;
	
	private List<Cliente> clientes;
	
	
	private List<Compra> compras;
	
	private CompraDAO compraDAO;
	
private LazyDataModel<Compra> dataModel;
	
	
	
	public LazyDataModel<Compra> getDataModel() {
		return dataModel;
	}
	
	
	

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}


  public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public List<ItemCompra> getItensCompra() {
		return itensCompra;
	}

	public void setItensCompra(List<ItemCompra> itensCompra) {
		this.itensCompra = itensCompra;
	}

	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}




public CompraListaBean() {
	  dataModel = new LazyDataModel<Compra>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Compra> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 compraDAO = new CompraDAO();
				 
				 this.setRowCount( compraDAO.getTotalRegistros().intValue());
			     
				 List<Compra> list =  compraDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          //otherwise it will still show all page links; pages at end will be empty
			          this.setRowCount( compraDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
}
/*
  public void imprimir(ActionEvent evento) {
		Venda venda =(Venda) evento.getComponent().getAttributes().get("vendaSelecionada");
			try {
				
				//arvore de componentes da aplicação, que procura pelo conponente do formulario e por ela a tabela
				//DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
				//extrair os filtros
				//Map<String , Object> filtros = tabela.getFilters();
				//capturando os filtros
				Long codigo = venda.getCodigo();
				//Long codigo = (long) filtros.get("codigo");
				
				
			//pegar o caminho do arquivo da memória
			String caminho = Faces.getRealPath("/relatorios/compvenda.jasper");
			
			//JasperCompileManager.compileReportToFile(caminho);
			
			Map<String, Object> parametros = new HashMap<>();//Guarda um nome e um valor, com uma estrutura de mapa
			
			
				parametros.put("Parameter",codigo );//o valor ' %% ' indica que a tabela toda sera impresa
			
			//parametros.put("PRODUTO_DESCRICAO", proDescricao);//setando os filtros do report com o filtro da tabela do primefaces
			//parametros.put("FORNECEDOR_NOME",fabDescricao);
			
			Connection conexao = HibernateUtil.getConexao();
			
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);//gera o relatorio populado
			
			String reportname = "venda " + venda.getCodigo();
			relatorio.setName("/" + reportname);//setando o nome default para o arquivo
			
			JasperPrintManager.printReport(relatorio, false);//imprime o relatorio
			
			/*Nota:(..., true) funciona no Tomcat mais não no JBoss WildFly pois aparentemente vc não pode mandar imprimir direto na maquina cliente,
			 * pode ser por questaõ de segurança.
			Quando setado para (...,false) funciona em ambos,mais ele vai abrir o painel para para efetuar o download do arquivo em PDF.
			Fonte: https://stackoverflow.com/questions/33209293/error-when-printing-jasper-report-using-jasperprintmanager-printreportprint-tr
			
			
		}catch(JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um relatório");
			erro.printStackTrace();
		}
		}
		
	*/
	


	
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
