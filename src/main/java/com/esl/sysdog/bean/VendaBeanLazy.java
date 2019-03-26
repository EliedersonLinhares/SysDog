package com.esl.sysdog.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.ClienteDAO;
import com.esl.sysdog.dao.ProdutoDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.dao.VendaDAO;
import com.esl.sysdog.model.Cliente;
import com.esl.sysdog.model.ItemVenda;
import com.esl.sysdog.model.Produto;
import com.esl.sysdog.model.Usuario;
import com.esl.sysdog.model.Venda;


/*
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
*/

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBeanLazy implements Serializable{
	
	private Venda venda;
	
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	
	private List<Cliente> clientes;
	private List<Usuario> usuarios;
	private List<Venda> vendas;
	
	private ProdutoDAO produtoDAO;
	private VendaDAO vendaDAO;
	private UsuarioDAO  usuarioDAO; 
	private ClienteDAO clienteDAO;
	
private LazyDataModel<Produto> dataModel;
	
	
	
	public LazyDataModel<Produto> getDataModel() {
		return dataModel;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	
	
	
	
	public  VendaBeanLazy() {
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
	
	
	
	

	
	
	
	
	public void listar(){
		try {
			
			produtoDAO = new ProdutoDAO();
			vendaDAO = new VendaDAO();
			usuarioDAO = new UsuarioDAO();
			clienteDAO = new ClienteDAO();
			
			
			venda = new Venda();
			venda.setPrecoParcial(new BigDecimal("0.00"));
			venda.setDesconto(new BigDecimal("0.00"));
			
			produtos = produtoDAO.listar();
			
			itensVenda = new ArrayList<>();
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}
	
	public void listarNovo() {
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listar("horario");
	}
	
	
	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");
		
		int achou = -1;
		for(int posicao = 0; posicao < itensVenda.size(); posicao++){
			if(itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())){
				achou = posicao;
			}
		}
		
		if(achou > -1){
			itensVenda.remove(achou);
		}
		calcular();
	}
	
	public void calcular() {//A cada laço acumula um valor, necessario coloca-lo no final dos metodos Adicionar e Remover 
		venda.setPrecoParcial(new BigDecimal("0.00"));;//zera o valor
		
		for(int posicao = 0; posicao < itensVenda.size(); posicao++) {//criação de um for dentro do Arraylist de vendas
			ItemVenda itemVenda = itensVenda.get(posicao);//captura do item da venda corrente, a cada repetição
		    venda.setPrecoParcial(venda.getPrecoParcial().add(itemVenda.getPrecoParcial()));//preçototal + preço parcial
		}
		
	}
	public void finalizar(){
		try {
		
			venda.setPrecoTotal(venda.getPrecoParcial().subtract(venda.getDesconto()));
			venda.setHorario(new Date());
			venda.setCliente(null);
			venda.setUsuario(null);
			
			
			usuarios = usuarioDAO.listar();
              
			
             
              clientes = clienteDAO.listar();
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a venda");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			//condicional para garantir que a compra tenha algun item 
			if(venda.getPrecoTotal().signum() == 0) {// o bigDecimal comparado com zero usa-se o signum() que considera todo numero negativo -1, positivo 1 e 0 como 0
			Messages.addGlobalError("Informe pelo menos um item para venda");
			return;
			}
			
			
			vendaDAO.salvar(venda, itensVenda);
			
			listar();
			
			Messages.addGlobalInfo("Venda Realizada com sucesso");
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda");
			erro.printStackTrace();
			listar();
		}
	}
	
	
	
	
	public void adicionar(ActionEvent evento) {
		Produto produto =(Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
		
		int find = -1;
		for(int posicao = 0; posicao < itensVenda.size(); posicao++ ) {//for para percorrer o arraylist
			if(itensVenda.get(posicao).getProduto().equals(produto)) {//pegando o produto da linha corrente everificando se ele é igual ao produto que já tenho
			find = posicao;//se for igual é feita a marcação com o find, deixan do de ser -1 para ser igual a valor da posição onde deu TRUE
		    }
		}
		
		if(find < 0) {//teste da variavel find caso seja menor que 0
		
		ItemVenda itemVenda = new ItemVenda();//processa a adição
		itemVenda.setPrecoParcial(produto.getPreco());
		itemVenda.setProduto(produto);
		itemVenda.setQuantidade(new Short("1"));
		
		itensVenda.add(itemVenda);
		}else {
			ItemVenda itemVenda = itensVenda.get(find);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));//1
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		
		calcular();
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
