package com.esl.sysdog.util;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContexto implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//HibernateUtil.getFabricadeSessoes().close();
		JpaUtil.getEntityManager().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//HibernateUtil.getFabricadeSessoes();
		
		JpaUtil.getEntityManager();
		
	}

}
/*Classe usada para que o carregamento do hibernate seja feito na inicialização
 * do Tomcat e não no acesso do primeiro usuario, para isso é necessario
 * criar essa classe com a inicialização e destruição e:
 * 
 * -registrar o servlet 3.1 do repositorio maven no POM.xml
 * - adicionar o listener ao WEB.xml, usando o Listerner-class
 * 
 */
