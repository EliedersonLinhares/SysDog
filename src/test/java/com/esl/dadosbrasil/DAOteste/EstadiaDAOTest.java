package com.esl.dadosbrasil.DAOteste;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.esl.sysdog.dao.AnimalDAO;
import com.esl.sysdog.dao.EstadiaDAO;
import com.esl.sysdog.dao.LocalDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Animal;
import com.esl.sysdog.model.Estadia;
import com.esl.sysdog.model.Local;
import com.esl.sysdog.model.Usuario;



public class EstadiaDAOTest {

	
	@Test
	
	public void salvar() throws ParseException {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.buscar(3L);
		
		AnimalDAO animalDAO = new AnimalDAO();
		Animal animal = animalDAO.buscar(2L);
		
		LocalDAO localDAO = new LocalDAO();
		Local local = localDAO.buscar(1L);
		
		Estadia estadia = new Estadia();
		
		estadia.setLocal(local);
		estadia.setUsuario(usuario);
		estadia.setAnimal(animal);
		
		if(animal.getAlocado() == true){
			System.out.println("O animal "+ animal.getNome()+" tem uma estadia aberta feche a primeiro antes de abrir uma nova estadia");
			return;
		}else {
		
		estadia.setDataEntrada(new SimpleDateFormat("dd/MM/yyyy").parse("25/05/2018"));
		estadia.setDataSaida(new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2018"));
		estadia.setDescricao("Teste de cadastro2");
		estadia.setStatus(true);
		estadia.setValorCobrado(new BigDecimal("46.70"));
		estadia.setDesconto(new BigDecimal("0"));

		
		
		EstadiaDAO estadiaDAO = new EstadiaDAO();
		estadiaDAO.merge(estadia);
		
		animal.setAlocado(true);
		animalDAO.merge(animal);
		}
	}
	
	
	@Test
	@Ignore
	public void testardatas() {
		 
		
		/*
		Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar cal2 = Calendar.getInstance();
         cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
     
         Date date1 =  cal1.getTime();
		Date date2 = cal2.getTime();
		*/
		
		
		LocalDateTime date1 = LocalDateTime.now();
		date1 = date1.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
		LocalDateTime date2 = date1.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
		
		
		System.out.println("Primeira data do mes atual: " + date1);
		System.out.println("Ultima data do mes atual: " + date2);
		
		
		LocalDateTime ldt1 = LocalDateTime.now();
		ldt1 = ldt1.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
		
		Instant instant1 = ldt1.toInstant(ZoneOffset.UTC);
		Date date3 = Date.from(instant1);
		
		LocalDateTime ldt2 = ldt1.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
		
		Instant instant2 = ldt2.toInstant(ZoneOffset.UTC);
		Date date4 = Date.from(instant2);
		
		System.out.println("Primeira data do mes atual: " + date3);
		System.out.println("Ultima data do mes atual: " + date4);
		
		
		
		LocalDateTime ldt4 = LocalDateTime.now();
		ldt4 = ldt4.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
		
		
		Date date5 = Date.from(ldt4.atZone(ZoneId.systemDefault()).toInstant());
		
		LocalDateTime ldt5 = ldt4.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
		
		
		Date date6 = Date.from(ldt5.atZone(ZoneId.systemDefault()).toInstant());
		
		System.out.println("Primeira data do mes atual: " + date5);
		System.out.println("Ultima data do mes atual: " + date6);
	}
	
	@Test
	@Ignore
	public void Listar() throws ParseException {//Funcionando

		/* SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
		    String dateString = "11/01/2018 01:00:00";
		    Date date = sdf.parse(dateString);
		    SimpleDateFormat sdf2 = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
		    String dateString2 = "11/30/2015 23:59:59";
		    Date date2 = sdf2.parse(dateString2);
		    Timestamp timestamp1 = new Timestamp(date.getTime());
		 
		    Timestamp timestamp2 = new Timestamp(date2.getTime());
		
		*/
		
		Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMinimum(Calendar.DAY_OF_MONTH));
       
        
        Calendar cal2 = Calendar.getInstance();
         cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
         
		
         Date date1 =  cal1.getTime();
		Date date2 = cal2.getTime();
		
		EstadiaDAO estadiaDAO = new EstadiaDAO();
		List<Estadia> resultado = estadiaDAO.findDates(date1, date2);
		
		for(Estadia estadia : resultado) {
			
			System.out.println("Código da Estadia: " + estadia.getCodigo());
			System.out.println("Descrição : " + estadia.getDescricao());
			System.out.println("Animal : " + estadia.getAnimal().getNome());
			System.out.println("Data Entrada : " + estadia.getDataEntrada());
			System.out.println("Data Saida : " + estadia.getDataSaida());
		}
	}
	
	@Test
	@Ignore
	public void Listar2() throws ParseException {
		
		
		
		//Date data1 = new Date(2018-11-01);
		//Date data2 = new Date(2018-12-30);
		
		EstadiaDAO estadiaDAO = new EstadiaDAO();
		List<Estadia> resultado = estadiaDAO.listar();
		
		for(Estadia estadia : resultado) {
			
			System.out.println("Código da Estadia: " + estadia.getCodigo());
			System.out.println("Descrição : " + estadia.getDescricao());
			System.out.println("Animal : " + estadia.getAnimal().getNome());
			System.out.println("Descrição : " + estadia.getDataEntrada());
			System.out.println("Descrição : " + estadia.getDataSaida());
		}
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
	
	
	@Test
	@Ignore
	public void Listar3() throws ParseException {
		
		
		
		Date date1 = new Date(2018-11-01);
		Date date2 = new Date(2018-12-30);
		
		EstadiaDAO estadiaDAO = new EstadiaDAO();
		List<Estadia> resultado = estadiaDAO.findDates(date1, date2);
		
		for(Estadia estadia : resultado) {
			
			System.out.println("Código da Estadia: " + estadia.getCodigo());
			System.out.println("Descrição : " + estadia.getDescricao());
			System.out.println("Animal : " + estadia.getAnimal().getNome());
			System.out.println("Descrição : " + estadia.getDataEntrada());
			System.out.println("Descrição : " + estadia.getDataSaida());
		}
	}
}
