package com.esl.sysdog.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.model.Estadia;
import com.esl.sysdog.util.JpaUtil;



public class EstadiaDAO extends GenericDAO<Estadia>{
	
	
	/*
	 * Listar para uso no Schedule em modo lazy, onde limitamos a busca no BD somente a um itervalo de datas
	 */
	public List<Estadia> listarEventos(Date datainicial, Date datafinal){
    	
		EntityManager manager = JpaUtil.getEntityManager();
		
		
    		TypedQuery<Estadia> query = manager.createQuery("select u from Estadia u where  u.dataEntrada>=  :date1 AND  u.dataEntrada<= :date2", Estadia.class);
    			    query.setParameter("date1", datainicial);
    			    query.setParameter("date2", datafinal);
    			    List<Estadia> layoutsSelected = query.getResultList();
    			   
    			    return layoutsSelected;		
	
    }
	
	
	public static Date getPrimeiraDia(Date inicio){
        Calendar cal = Calendar.getInstance();
        cal.setTime(inicio);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
   
	public static Date getUltimoDia(Date ultima){
        Calendar cal = Calendar.getInstance();
        cal.setTime(ultima);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


	
	
	public List<Estadia> findDates(Date date1, Date date2) {//funcionando teste do Junit
        
		 
		EntityManager manager = JpaUtil.getEntityManager();
	     
	
		
	       TypedQuery<Estadia> query = manager.createQuery("select u from Estadia u where u.dataEntrada>= :date1 AND u.dataEntrada<= :date2", 
	               Estadia.class);
	       query.setParameter("date1", date1, TemporalType.TIMESTAMP);
	       query.setParameter("date2", date2, TemporalType.TIMESTAMP);
	       List<Estadia> layoutsSelected = query.getResultList();
	 
	      
	 
	       return layoutsSelected;
	   }

	
	
	
public List<Estadia> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Estadia> criteriaQuery = cb.createQuery(Estadia.class);
	      Root<Estadia> root = criteriaQuery.from(Estadia.class);
	      CriteriaQuery<Estadia> select = criteriaQuery.select(root);
	      criteriaQuery.orderBy(cb.desc(root.get("dataEntrada")));
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
	     
	      TypedQuery<Estadia> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Estadia> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Estadia.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Estadia> root = criteriaQuery.from(Estadia.class);
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
	

	public List<Estadia> ListarArea(Date data1, Date data2){
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		TypedQuery<Estadia> query = manager.createQuery("select u from Estadia u where  u.dataEntrada>=  :date1 AND  u.dataEntrada<= :date2", Estadia.class);
			    query.setParameter("date1", data1);
			    query.setParameter("date2", data2);
			    List<Estadia> layoutsSelected = query.getResultList();
			   
			    return layoutsSelected;		
	}
}			
