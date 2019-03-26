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


import com.esl.sysdog.model.Despesa;
import com.esl.sysdog.util.JpaUtil;




public class DespesaDAO extends GenericDAO<Despesa> {
	
	
public List<Despesa> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Despesa> criteriaQuery = cb.createQuery(Despesa.class);
	      Root<Despesa> root = criteriaQuery.from(Despesa.class);
	      CriteriaQuery<Despesa> select = criteriaQuery.select(root);
	      criteriaQuery.orderBy(cb.asc(root.get("dataVencimento")));
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
	     
	      TypedQuery<Despesa> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Despesa> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Despesa.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Despesa> root = criteriaQuery.from(Despesa.class);
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
