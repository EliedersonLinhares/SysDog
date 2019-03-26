package com.esl.sysdog.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

public class JpaUtil {
	
	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence.createEntityManagerFactory("SysDog");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	
	
	  public static Connection getConexao() {
      
		EntityManager manager = JpaUtil.getEntityManager();
			Session sessao = manager.unwrap(Session.class);
		  //Session sessao = fabricadeSessoes.openSession();
      	
      	Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {//2
			@Override
			public Connection execute(Connection conn) throws SQLException {
			
				return conn;//conexao recebe conn
			}
      	
      	});
      	
      	return conexao;
      }
}



/*
 * Criamos um bloco estático para inicializar a fábrica de Entity Manager. Isso ocorrerá
apenas uma vez, no carregamento da classe. Agora, sempre que precisarmos de uma
EntityManager, podemos chamar:
EntityManager manager = JpaUtil.getEntityManager();
 * */
 