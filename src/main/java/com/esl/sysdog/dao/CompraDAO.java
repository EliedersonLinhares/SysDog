package com.esl.sysdog.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.model.Compra;
import com.esl.sysdog.model.ItemCompra;
import com.esl.sysdog.model.Produto;
import com.esl.sysdog.util.JpaUtil;



public class CompraDAO extends GenericDAO<Compra> {
	
	
	
	public void salvar(Compra compra, List<ItemCompra> itensCompra) {
		
		EntityManager manager = JpaUtil.getEntityManager();
	    Session sessao = manager.unwrap(Session.class);
	    
		
		
    	Transaction transacao = null;//inicia ela como nula
    	
    	try {
    		transacao = sessao.beginTransaction();//inicia uma transação
    		
    		sessao.save(compra);
    		
    		 for(int posicao = 0; posicao < itensCompra.size(); posicao++) {//for para o itemVenda, que grava os itens no carrinho no BD um a um
    	    		ItemCompra itemCompra = itensCompra.get(posicao);//capturando um item da lista
    	    		itemCompra.setCompra(compra);//seta a venda
    	    		
    	    		sessao.save(itemCompra);//salva o item no bd
    	    		
    	    		Produto produto = itemCompra.getProduto();//captura o produto
    	    		int quantidade = produto.getQuantidade() + itemCompra.getQuantidade();
    	    		produto.setQuantidade(new Short(quantidade + ""));
        			sessao.update(produto);
    	}
    		 transacao.commit();//confirma	 
	}catch(RuntimeException erro){//tratamento de erro
		if (transacao != null) {//se transação for diferente de nulo apos o erro
			transacao.rollback();//desfaz toda a transação
		}
		throw erro;//mostra o erro 
	}finally {
		sessao.close();//fecha a sessão em qualquer caso
	}

}
	
public List<Compra> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Compra> criteriaQuery = cb.createQuery(Compra.class);
	      Root<Compra> root = criteriaQuery.from(Compra.class);
	      CriteriaQuery<Compra> select = criteriaQuery.select(root);
	      criteriaQuery.orderBy(cb.desc(root.get("horario")));
	      if (sortField != null) {
	    	  criteriaQuery.orderBy(sortOrder == SortOrder.DESCENDING ?
	                  cb.asc(root.get(sortField)) : cb.desc(root.get(sortField)));
	      }

	      if (filters != null && filters.size() > 0) {
	          List<Predicate> predicates = new ArrayList<>();
	          for (Map.Entry<String, Object> entry : filters.entrySet()) {
	              String field = entry.getKey();
	              Object value = entry.getValue();
	              if (value == null) {
	                  continue;
	              }

	              Expression<String> expr = root.get(field).as(String.class);
	              Predicate p = cb.like(cb.lower(expr),
	                      "%" + value.toString().toLowerCase() + "%");
	              predicates.add(p);
	          }
	          if (predicates.size() > 0) {
	              criteriaQuery.where(cb.and(predicates.toArray
	                      (new Predicate[predicates.size()])));
	          }
	      }
	     
	      TypedQuery<Compra> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Compra> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Compra.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Compra> root = criteriaQuery.from(Compra.class);
	      CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

	      if (filters != null && filters.size() > 0) {
	          List<Predicate> predicates = new ArrayList<>();
	          for (Map.Entry<String, Object> entry : filters.entrySet()) {
	              String field = entry.getKey();
	              Object value = entry.getValue();
	              if (value == null) {
	                  continue;
	              }

	              Expression<String> expr = root.get(field).as(String.class);
	              Predicate p = cb.like(cb.lower(expr),
	                      "%" + value.toString().toLowerCase() + "%");
	              predicates.add(p);
	          }
	          if (predicates.size() > 0) {
	              criteriaQuery.where(cb.and(predicates.toArray
	                      (new Predicate[predicates.size()])));
	          }
	      }
	      Long count = manager.createQuery(select).getSingleResult();
	      return count.intValue();
	  }
	
	
	
}
