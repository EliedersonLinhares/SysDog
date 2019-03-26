package com.esl.sysdog.test;


import javax.persistence.Persistence;


import org.junit.Test;



public class JPAUtilTeste {
  
	@Test
	public void executar() {
		Persistence.createEntityManagerFactory("SysDog");
		
      }
}
