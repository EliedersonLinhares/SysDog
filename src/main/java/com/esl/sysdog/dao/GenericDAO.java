package com.esl.sysdog.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.esl.sysdog.util.JpaUtil;





/*
 * Dao generico que irá conter as ações do banco de dados a serem feitas, todas seguem o mesmo
 * ordem usando o JPA e o hibernate.
 * 
 *  - Abre uma coneção com o EntityManager e uma sessao pelo unwrap
 *  
 *  - inicia a transação como nula
 *  
 *  - coloca todo o processo dentro de um try/cath 
 *  
 *   - inicia a transação
 *   
 *   - faz o ato relacionado(salvar,excluir,editar, listar...)
 *   
 *   - confirma com o commit
 *   
 *   -Se algo der errado vai para o catch
 *   
 *   - e desfaz a ação com o roolback
 *   
 *   -   fecha a sessão ao final do processo com erro ou não
 */




public class GenericDAO<Entidade> {
	
	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];//utulizado na parte de busca de dados
	}

	
    public void salvar(Entidade entidade) {
    	EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
    	
    	Transaction transacao = null;
    	
    	try {
    		transacao = sessao.beginTransaction();
    		sessao.save(entidade);
    		transacao.commit();
    	}catch(RuntimeException erro){
    		if (transacao != null) {
    			transacao.rollback();
    		}
    		throw erro; 
    	}finally {
    		sessao.close();
    	}
    }
    

    

    
    @SuppressWarnings("unchecked")
    public void excluir(Entidade entidade) {
    	EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
    	
    	Transaction transacao = null;
    	
    	try {
    		transacao = sessao.beginTransaction();
    		Entidade retorno = (Entidade)sessao.merge(entidade);
    		sessao.delete(retorno);
    		transacao.commit();
    	}catch(RuntimeException erro){
    		if (transacao != null) {
    			transacao.rollback();
    		}
    		throw erro;
    	}finally {
    		sessao.close();
    	}
    }
    
    
    public void editar(Entidade entidade) {
    	
    	EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
		Transaction transacao = null;
    	
    	try {
    		transacao = sessao.beginTransaction();
    		sessao.update(entidade);
    		transacao.commit();
    	}catch(RuntimeException erro){
    		if (transacao != null) {
    			transacao.rollback();
    		}
    		throw erro;
    	}finally {
    		sessao.close();
    	}
    }
    
    @SuppressWarnings("unchecked")
	public Entidade merge(Entidade entidade) {
    	
    	EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
    	
    	Transaction transacao = null;
    	
    	try {
    		transacao = sessao.beginTransaction();
    		Entidade retorno = (Entidade)sessao.merge(entidade);
    		transacao.commit();
    		return retorno;
    	}catch(RuntimeException erro){
    		if (transacao != null) {
    			transacao.rollback();
    		}
    		throw erro;
    	}finally {
    		sessao.close();
    	}
    }
    
    /*
     * Nos campos listar e listar ordenado(Overdloading) foi usado o CriteriaQuery  e CriteriaBuilder para 
     * realizar as ações.
     */

	public List<Entidade> listar(){
    	
		EntityManager manager = JpaUtil.getEntityManager();
		Session sessao = manager.unwrap(Session.class);
		
    	try {
    	
            CriteriaQuery<Entidade> criteriaQuery = sessao.getCriteriaBuilder().createQuery(classe);
            criteriaQuery.from(classe);
            List<Entidade> resultado = sessao.createQuery(criteriaQuery).getResultList();
    		
    		
            return resultado;
    	}catch(RuntimeException erro) {
    		throw erro;
    	}finally {
    		sessao.close();
    	}
	
    }
	
	
	
		public List<Entidade> listar(String campoOrdenacao){
	    	
			EntityManager manager = JpaUtil.getEntityManager();
			Session sessao = manager.unwrap(Session.class);
			
	    	try {
	    		
	    		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
	    		CriteriaQuery<Entidade> criteriaQuery = criteriaBuilder.createQuery(classe);
	    		Root<Entidade> root = criteriaQuery.from(classe);
	    		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(campoOrdenacao)));
	    		 
	    		
	    		List<Entidade> resultado = sessao.createQuery(criteriaQuery).getResultList();
	    		return resultado;
	    	}catch(RuntimeException erro) {
	    		throw erro;
	    	}finally {
	    		sessao.close();
	    	}
		
	    }
		
		
		public Entidade buscar(Long codigo){
	    	
			EntityManager manager = JpaUtil.getEntityManager();
			Session sessao = manager.unwrap(Session.class);
			
	    	try {
	    		
	    		
	    		CriteriaBuilder criteriaBuilder = sessao.getCriteriaBuilder();
	    		CriteriaQuery<Entidade> criteriaQuery = criteriaBuilder.createQuery(classe);
	    		Root<Entidade> root = criteriaQuery.from(classe);
	    		criteriaQuery.select(root).where( criteriaBuilder.and( criteriaBuilder.equal(root.get("codigo"), codigo)));
	    		Entidade resultado = (Entidade) sessao.createQuery(criteriaQuery).uniqueResult();
	    		
	  
	    		return resultado;
	    	}catch(RuntimeException erro) {
	    		throw erro;
	    	}finally {
	    		sessao.close();
	    	}
	}
	
			
	
			
			
			
		
}