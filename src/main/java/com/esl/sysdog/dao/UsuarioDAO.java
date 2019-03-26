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

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.model.Usuario;
import com.esl.sysdog.util.JpaUtil;





public class UsuarioDAO extends GenericDAO<Usuario> {
	
	
	final RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    final ByteSource salt = rng.nextBytes();
	
	
	public void salvarCriptografia(Usuario entidade) {
    	
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		Transaction transacao = null;//inicia ela como nula
    	
    	try {
    		
    		
    		transacao = sessao.beginTransaction();//inicia uma transação
    		sessao.merge(entidade);//salva a entidade
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
	
	public Usuario autenticar(String login, String senha) {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
    	try {
    		// Define the CriteriaQuery
    		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
    		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
    		Root<Usuario> root = criteriaQuery.from(Usuario.class);
    		criteriaQuery.select(root).where( criteriaBuilder.and( criteriaBuilder.equal(root.get("login"), login)));
    		Sha256Hash hash = new Sha256Hash("sha256", senha,5000);
    		criteriaQuery.select(root).where( criteriaBuilder.and( criteriaBuilder.equal(root.get("senha"), hash.toHex())));
    		
    		 
    		// Execute query with pagination
    		Usuario resultado = (Usuario) sessao.createQuery(criteriaQuery).uniqueResult();
    	
    		
    		return resultado;
    	}catch(RuntimeException erro) {
    		throw erro;
    	}finally {
    		sessao.close();
    	}
	}
	
	

	
	
	
public List<Usuario> listarLazy(int first, int pageSize,Map<String, Object> filters, String sortField, SortOrder sortOrder){
        
		EntityManager manager = JpaUtil.getEntityManager();
		
		
		
	      CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Usuario> criteriaQuery = cb.createQuery(Usuario.class);
	      Root<Usuario> root = criteriaQuery.from(Usuario.class);
	      CriteriaQuery<Usuario> select = criteriaQuery.select(root);
	      
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
	     
	      TypedQuery<Usuario> query = manager.createQuery(select);
	      query.setFirstResult(first);
	      query.setMaxResults(pageSize);
	      List<Usuario> list = query.getResultList();
	      return list;
	      
	      
	     
	}
	
	public Long getTotalRegistros() {
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Usuario.class)));
		 
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public int getColunasFiltradas(Map<String, Object> filters) {
	     
		EntityManager manager = JpaUtil.getEntityManager();
	      
		CriteriaBuilder cb = manager.getCriteriaBuilder();
	      CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
	      Root<Usuario> root = criteriaQuery.from(Usuario.class);
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
