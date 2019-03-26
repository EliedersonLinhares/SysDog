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

import com.esl.sysdog.dao.CompraDAO;
import com.esl.sysdog.dao.ProdutoDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Compra;
import com.esl.sysdog.model.ItemCompra;
import com.esl.sysdog.model.Produto;
import com.esl.sysdog.model.Usuario;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CompraBeanLazy implements Serializable{

	
	private Compra compra;
	
	private Produto produto;
	
	private List<Produto> produtos;
	private List<ItemCompra> itensCompra;
	private List<Usuario> usuarios;
	private List<Compra> compras;
	
	private ProdutoDAO produtoDAO;
	private CompraDAO compraDAO;
	private UsuarioDAO  usuarioDAO;
	
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
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public List<ItemCompra> getItensCompra() {
		return itensCompra;
	}
	public void setItensCompra(List<ItemCompra> itensCompra) {
		this.itensCompra = itensCompra;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}



	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

       public CompraBeanLazy() {
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
	
	public void listar() {
		
	try {	
		
		produtoDAO = new ProdutoDAO();
		compraDAO = new CompraDAO();
		usuarioDAO = new UsuarioDAO();
		
		compra = new Compra();
		compra.setPrecoTotal(new BigDecimal("0.00"));
		
		produtos = produtoDAO.listar();
		itensCompra = new ArrayList<>();
	} catch(RuntimeException erro) {
		Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de compras");
		erro.printStackTrace();
	}
	}
	
	
    public void calcular() {//A cada laço acumula um valor, necessario coloca-lo no final dos metodos Adicionar e Remover 
    	compra.setPrecoTotal(new BigDecimal("0.00"));
		
    	
    	for(int posicao = 0; posicao < itensCompra.size(); posicao++) {//criação de um for dentro do Arraylist de vendas
			ItemCompra itemCompra = itensCompra.get(posicao);//captura do item da venda corrente, a cada repetição
		    compra.setPrecoTotal(compra.getPrecoTotal().add(itemCompra.getPrecoParcial()));//preçototal + preço parcial
		}
	}
    
	public void adicionar(ActionEvent evento) {
		Produto produto =(Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
		
		int find = -1;
		for(int posicao = 0; posicao < itensCompra.size(); posicao++ ) {//for para percorrer o arraylist
			if(itensCompra.get(posicao).getProduto().equals(produto)) {//pegando o produto da linha corrente everificando se ele é igual ao produto que já tenho
			find = posicao;//se for igual é feita a marcação com o find, deixan do de ser -1 para ser igual a valor da posição onde deu TRUE
		    }
		}
		
		if(find < 0) {//teste da variavel find caso seja menor que 0
			
			ItemCompra itemCompra = new ItemCompra();//processa a adição
			itemCompra.setPrecoParcial(new BigDecimal("0.00"));
			itemCompra.setPrecoUnidade(new BigDecimal("0.00"));
			itemCompra.setDesconto(new BigDecimal("0.00"));
			itemCompra.setProduto(produto);
			itemCompra.setQuantidade(new Short("0"));
			
			itensCompra.add(itemCompra);
		}
		calcular();
	}
	
	
   public void remover(ActionEvent evento) {
	   ItemCompra itemCompra = (ItemCompra) evento.getComponent().getAttributes().get("itemSelecionado");
		
		int achou = -1;
		for(int posicao = 0; posicao < itensCompra.size(); posicao++){
			if(itensCompra.get(posicao).getProduto().equals(itemCompra.getProduto())){
				achou = posicao;
			}
		}
		
		if(achou > -1){
			itensCompra.remove(achou);
		}
		calcular();
	}
   
   public void calcular2(ActionEvent evento) {
	   ItemCompra itemCompra = (ItemCompra) evento.getComponent().getAttributes().get("itemSelecionado");
	   
	   if(itemCompra.getPrecoUnidade().compareTo(itemCompra.getDesconto()) < 0) {
		   Messages.addGlobalInfo("Numero negativo");
			return;
	   }else {
	   itemCompra.setPrecoParcial((itemCompra.getPrecoUnidade().subtract(itemCompra.getDesconto()).multiply(new BigDecimal(itemCompra.getQuantidade()))));
	  
	   calcular();
	   }
	}
   
	
   public void finalizar() {
	   try {
		   compra.setHorario(new Date());
		   compra.setUsuario(null);
		   
		   usuarios = usuarioDAO.listar();
	   }catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a compra");
			erro.printStackTrace();
		}
		
	}
	
   public void salvar() {
		try {
			//condicional para garantir que a compra tenha algun item 
			if(compra.getPrecoTotal().signum() == 0) {// o bigDecimal comparado com zero usa-se o signum() que considera todo numero negativo -1, positivo 1 e 0 como 0
			Messages.addGlobalError("Informe pelo menos um item para compra");
			return;
			}
			
			
			compraDAO.salvar(compra, itensCompra);
			
			listar();
			
			Messages.addGlobalInfo("Compra Realizada com sucesso");
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a compra");
			erro.printStackTrace();
			listar();
		}
		
		
	}
   
   
   
   
	public void listarnovo() {
		
	}
	
	public void calcular3() {
		   ItemCompra itemCompra = new ItemCompra();
		   
		   itemCompra.setPrecoParcial((itemCompra.getPrecoUnidade().subtract(itemCompra.getDesconto()).multiply(new BigDecimal(itemCompra.getQuantidade()))));
		   calcular();
		}
	
	
	
	
	
	


}


   

