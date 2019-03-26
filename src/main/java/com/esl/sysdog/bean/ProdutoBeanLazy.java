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
import com.esl.sysdog.dao.ProdutoDAO;
import com.esl.sysdog.model.Fornecedor;
import com.esl.sysdog.model.Produto;





/*
import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
*/

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBeanLazy implements Serializable {

	private Produto produto;
	private List<Produto> produtos;
	private List<Fornecedor> fornecedores;// usado para a chave estrangeira

	private ProdutoDAO produtoDAO;

	private LazyDataModel<Produto> dataModel;
	
	public LazyDataModel<Produto> getDataModel() {
		return dataModel;
	}

	

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	

	public ProdutoBeanLazy() {
		dataModel = new LazyDataModel<Produto>() {
			
			
			private static final long serialVersionUID = 1L;
			
			 @Override
			  public List<Produto> load(int first, int pageSize, String sortField,
			                             SortOrder sortOrder, Map<String, Object> filters) {
				 
				 produtoDAO = new ProdutoDAO();
				 
				 this.setRowCount( produtoDAO.getTotalRegistros().intValue());
			     
				 List<Produto> list =  produtoDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
			      if (filters != null && filters.size() > 0) {
			          //otherwise it will still show all page links; pages at end will be empty
			          this.setRowCount( produtoDAO.getColunasFiltradas(filters));
			      }
			      return list;
			  }
			
		};
	}
	
	
	public void novo() {

		try {

			produto = new Produto();
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();
			produto.setQuantidade(new Short("0"));
			

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo produto");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
		

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto); 
			
			
			
            produto = new Produto();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			produtos = produtoDAO.listar();
			produto.setQuantidade(new Short("0"));

			Messages.addGlobalInfo("Produto salvo com sucesso");
			} catch (RuntimeException erro) {// | IOException  erro) {//incluido o IOException para o upload
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo produto");
			erro.printStackTrace();
		}
		
	}

	

	public void editar(ActionEvent evento) {
		try {

			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao editar um produto");
			erro.printStackTrace();
		}

	}
	
	/*
	public void imprimir() {
		
		try {
			
			//arvore de componentes da aplicação, que procura pelo conponente do formulario e por ela a tabela
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			//extrair os filtros
			Map<String , Object> filtros = tabela.getFilters();
			//capturando os filtros
			String proDescricao = (String) filtros.get("descricao");//necessita do cast pois são objects,mude o tipo int,short... de acordo o campo da tabela
			String fabDescricao = (String) filtros.get("fornecedor.nomeF");
			
		//pegar o caminho do arquivo da memória
		String caminho = Faces.getRealPath("/relatorios/Produtos.jasper");
		
		//JasperCompileManager.compileReportToFile(caminho);
		
		Map<String, Object> parametros = new HashMap<>();//Guarda um nome e um valor, com uma estrutura de mapa
		
		//condicionais para pegar os valores filtrados na tabela do primefaces
		if(proDescricao == null) {//nada digitado na tela
			parametros.put("PRODUTO_DESCRICAO", "%%");//o valor ' %% ' indica que a tabela toda sera impresa
		}else {
			parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");// Equivalente a '%tab%' por exemplo, como filtro do sql
		}
		
		if(fabDescricao == null) {//nada digitado na tela
			parametros.put("FORNECEDOR_NOME", "%%");//o valor ' %% ' indica que a tabela toda sera impresa
		}else {
			parametros.put("FORNECEDOR_NOME", "%" + fabDescricao + "%");// Equivalente a '%tab%' por exemplo, como filtro do sql
		}
		//parametros.put("PRODUTO_DESCRICAO", proDescricao);//setando os filtros do report com o filtro da tabela do primefaces
		//parametros.put("FORNECEDOR_NOME",fabDescricao);
		
		Connection conexao = HibernateUtil.getConexao();
		
		JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);//gera o relatorio populado
		
		String reportname = "produtos";
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
	
	public List<String> pesquisarDescricoesTipo(String tipo){
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		return produtoDAO.descricoesQueContemTipo(tipo);	
		}
}
