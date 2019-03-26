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
import org.primefaces.model.SortOrder;

import com.esl.sysdog.model.Cliente;
import com.esl.sysdog.util.JpaUtil;




public class ClienteDAO extends GenericDAO<Cliente>{

	

/*Nas classes especificas ficaram os metodos que fazem a conexão com o uso do lazy
 * no primefaces
 * 
 * 	
 */
	
	
	/*
	 * Listar no modo lazy passando os parametros necessarios para a tabela do primefaces
	 * esse modelo tambem é possivel fazer o ordenamento e pesquisa em determinados campos
	 */
public List<Cliente> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Cliente> criteriaQuery = cb.createQuery(Cliente.class);
	      Root<Cliente> root = criteriaQuery.from(Cliente.class);
	      CriteriaQuery<Cliente> select = criteriaQuery.select(root);
	      
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
	     
	      TypedQuery<Cliente> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Cliente> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
/*
 * Metodo para pegar o total de registros no BD
 */
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Cliente.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	/*
	 * Metodo que devolve as colunas depois que o filtro é usado.
	 */
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Cliente> root = criteriaQuery.from(Cliente.class);
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
	
	
public List<String> AutoBairro(String bairro) {
		
		EntityManager manager = JpaUtil.getEntityManager();
		
		TypedQuery<String> query = manager.createQuery(
				"select distinct bairro from Cliente " + "where upper(bairro) like upper(:bairro)",
				String.class);
		query.setParameter("bairro", "%" + bairro + "%");
		return query.getResultList();
	}

public List<String> AutoCidade(String cidade) {
	
	EntityManager manager = JpaUtil.getEntityManager();
	
	TypedQuery<String> query = manager.createQuery(
			"select distinct cidade from Cliente " + "where upper(cidade) like upper(:cidade)",
			String.class);
	query.setParameter("cidade", "%" + cidade + "%");
	
	return query.getResultList();
}	
	
	

}
