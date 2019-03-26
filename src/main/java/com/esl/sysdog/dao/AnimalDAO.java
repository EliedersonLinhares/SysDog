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

import com.esl.sysdog.model.Animal;
import com.esl.sysdog.util.JpaUtil;



public class AnimalDAO extends GenericDAO<Animal>{

	
public List<Animal> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Animal> criteriaQuery = cb.createQuery(Animal.class);
	      Root<Animal> root = criteriaQuery.from(Animal.class);
	      CriteriaQuery<Animal> select = criteriaQuery.select(root);
	     
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
	     
	      TypedQuery<Animal> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Animal> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Animal.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Animal> root = criteriaQuery.from(Animal.class);
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
	
	
public List<String> AutoTipo(String tipo) {
		
		EntityManager manager = JpaUtil.getEntityManager();
		
		TypedQuery<String> query = manager.createQuery(
				"select distinct tipo from Animal " + "where upper(tipo) like upper(:tipo)",
				String.class);
		query.setParameter("tipo", "%" + tipo + "%");
		return query.getResultList();
	}

public List<String> AutoRaca(String raca) {
	
	EntityManager manager = JpaUtil.getEntityManager();
	
	TypedQuery<String> query = manager.createQuery(
			"select distinct raca from Animal " + "where upper(raca) like upper(:raca)",
			String.class);
	query.setParameter("raca", "%" + raca + "%");
	return query.getResultList();
}	
	
	
}
