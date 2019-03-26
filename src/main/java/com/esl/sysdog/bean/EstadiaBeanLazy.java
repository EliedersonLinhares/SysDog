package com.esl.sysdog.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.esl.sysdog.dao.AnimalDAO;
import com.esl.sysdog.dao.EstadiaDAO;
import com.esl.sysdog.dao.LocalDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Animal;
import com.esl.sysdog.model.Estadia;
import com.esl.sysdog.model.Local;
import com.esl.sysdog.model.Usuario;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadiaBeanLazy implements Serializable {

	private Animal animal;
	
	private Estadia estadia;
	private List<Estadia> estadias;
	
    private List<Animal> animais;
	private List<Local> locais;
	private List<Usuario> usuarios;
	
	private ScheduleModel eventModel;
	
	 private LazyScheduleModel lazyEventModel;
	 
	 
	 public ScheduleModel getLazyEventModel() {
	        return lazyEventModel;
	    }
	
	public Estadia getEstadia() {
		return estadia;
	}
	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}
	public List<Estadia> getEstadias() {
		return estadias;
	}
	public void setEstadias(List<Estadia> estadias) {
		this.estadias = estadias;
	}
	public List<Animal> getAnimais() {
		return animais;
	}
	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
	
	public List<Local> getLocais() {
		return locais;
	}
	public void setLocais(List<Local> locais) {
		this.locais = locais;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	
	
	
	
	   public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	@PostConstruct
	    public void init() {
		   
		   lazyEventModel = new LazyScheduleModel() {
	             
	            @Override
	            public void loadEvents(Date start, Date end) {
		   
	            	List<DefaultScheduleEvent> scheduleEvents =
	            	          getEventos(start,end);
	            	for (DefaultScheduleEvent defaultScheduleEvent : scheduleEvents) {
	                    addEvent(defaultScheduleEvent);
	            	}
	            	
	            	
	            	
	            	//eventModel = new DefaultScheduleModel();//Carrega o modelo do schedule
	        estadia = new Estadia();//nova estadia
	       
	        
	        
	        try {
	        //	EstadiaDAO estadiaDAO = new EstadiaDAO();
	        //	estadias = estadiaDAO.listar();//lista todas as estadias
	        	
	        	// dao para popular os campos select com oas tabelas de chaves estrangeiras
	           AnimalDAO animalDAO = new AnimalDAO();
	           animais = animalDAO.listar();
	           
	           LocalDAO localDAO = new LocalDAO();
	           locais = localDAO.listar();
	           
	           UsuarioDAO usuarioFuncionarioDAO = new UsuarioDAO();
	           usuarios = usuarioFuncionarioDAO.listar();
	           
	           estadia.setDesconto(new BigDecimal("0"));
	           //
	           
	        }catch (RuntimeException erro) {
				Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo animal");
				erro.printStackTrace();
			}
	        
	        
	       
	            }   
	        
	        
	    };
		   }	            
	   
	   private List<DefaultScheduleEvent> getEventos(Date dataInicial, Date dataFinal) {
	   
		   //List<Estadia> listEstadia = estadias;
		   EstadiaDAO estadiaDAO = new EstadiaDAO();
		  

		   /*
		    * Seta data de exibição somente para cadastros efetuados 2 meses antes e 2 meses após a 
		    * data atual 
		    */
		     LocalDateTime hoje = LocalDateTime.now();
		    
		     LocalDateTime  ldt1 = hoje.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());	
			 Date dateI = Date.from(ldt1.atZone(ZoneId.systemDefault()).toInstant());
			
			LocalDateTime ldt2 = hoje.plusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
			Date dateF = Date.from(ldt2.atZone(ZoneId.systemDefault()).toInstant());
		   
		
		   try {
			  estadias = estadiaDAO.findDates(dateI, dateF);
			    } catch (Exception e) {
			      e.printStackTrace();
			    }
		   
		   
		   List<DefaultScheduleEvent> eventos = new ArrayList<DefaultScheduleEvent>();
		   
		   
		   for(Estadia ev : estadias) {//for para precher o calendario com o conteudo de estadia 
	        	DefaultScheduleEvent evento = new DefaultScheduleEvent( );//novo evento
	        	evento.setEndDate(ev.getDataSaida());
	        	evento.setStartDate(ev.getDataEntrada());
	            evento.setTitle(" - Estadia: "+ ev.getAnimal().getNome());
	        	evento.setData(ev.getCodigo());
	        	evento.setDescription(ev.getDescricao());
	        	evento.setAllDay(false);
	        	evento.setEditable(false);
	        
	        	
	        	if(ev.getStatus() == true) {
	        	evento.setStyleClass("event1");	
	        	}else if(ev.getStatus() == false) {
	        		evento.setStyleClass("event2");
	        	}
	        	lazyEventModel.addEvent(evento);
	        	
	        	
		        
	        }
		   
		   return eventos;
	   }
	   
	   
	   
	 
	   public void selecionado(SelectEvent selectEvent) {//mostrar os dados de uma estadia especifica
		   
		   ScheduleEvent auxEvent = (ScheduleEvent) selectEvent.getObject();
		   for(Estadia ev : estadias) {//for para mostrar o dados da estadia selecionada
			   if(ev.getCodigo() == (Long)auxEvent.getData()) {//compara o id da tabela com id do schedule
				   estadia = ev;
				   break;
			   }
		   }
	   }
	 
	   public void Novo(SelectEvent selectEvent) {//preencher os campos das datas quando clicado em uma data especifica
		   ScheduleEvent event = new DefaultScheduleEvent("",(Date)selectEvent.getObject(),(Date)selectEvent.getObject());//criando um novo schedulEvent com oas campos de inicio e fim
		   estadia = new Estadia();
		   estadia.setDataEntrada(new java.sql.Date(event.getStartDate().getTime()));//recupera a data clicada usando of formato que o schedule entenda, passando para o campo
		   estadia.setDataSaida(new java.sql.Date(event.getStartDate().getTime()));
        }
	   
	   public void salvar() {//metodo que persiste os dados no BD, diferente dos demais BEANS nesse é usado o salvar e o editar
		   
		   if(estadia.getCodigo() == null) {//verifica se o codigo é nulo, sendo então um novo cadastro
			   if(estadia.getDataEntrada().getTime() <= estadia.getDataSaida().getTime()) {//segunda condicional para verificar se a data de saida não é anterior a data de entrada
				   try {
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.salvar(estadia);
					   Messages.addGlobalInfo("Estadia salva com sucesso");
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar a estadia");
						erro.printStackTrace();
				   }
				   estadia = new Estadia();
			   }else {
				   Messages.addFlashGlobalError("A data entrada não pode ser anterior a data de saida");
					
			   }
			   
		   }else {//se o codigo estiver preenchido então é uma alteração...
			   
			   if(estadia.getDataEntrada().getTime() <= estadia.getDataSaida().getTime()) {
				   
				   try {
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.editar(estadia);
					   
					   Messages.addGlobalInfo("Estadia alterada com sucesso");
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar a estadia");
						erro.printStackTrace();
				   }
				   
			   }else {
				   Messages.addFlashGlobalError("A data entrada não pode ser anterior a data de saida");
					
			   }
		   }
		   }
	   
   public void salvar2() {//salva alterado para uso de um verificador se o animal já está hospedado 
		   
	   
	  
	   
	   if(estadia.getCodigo() == null) {//verifica se o codigo é nulo, sendo então um novo cadastro
		   
		   AnimalDAO animalDAO = new AnimalDAO();
		   Animal animal = animalDAO.buscar(estadia.getAnimal().getCodigo());
		  
		   if(animal.getAlocado() == true){//verifica se o animal já esta hospedado
			   
				Messages.addFlashGlobalError("O animal "+ animal.getNome()+" tem uma estadia aberta feche a primeiro antes de abrir uma nova estadia");
				return;
			}	
		   
		   
			   if(estadia.getDataEntrada().getTime() <= estadia.getDataSaida().getTime()) {//verifica se a data de saida não é anterior a data de entrada
				
				   try {
					   
					   estadia.setTotal(estadia.getValorCobrado().add(estadia.getGastoOutros()).add(estadia.getGastoRacao()).add(estadia.getGastoVacina()).subtract(estadia.getDesconto()));
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.merge(estadia);
					   
					  
					   animal.setAlocado(true);
					   animalDAO.merge(animal);
					   
					   Messages.addGlobalInfo("Estadia salva com sucesso");
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar a estadia");
						erro.printStackTrace();
				   }
				   estadia = new Estadia();
			   }else {
				   Messages.addFlashGlobalError("A data entrada não pode ser anterior a data de saida");
					
			   }
			   
		   }else {//se o codigo estiver preenchido então é uma alteração...
			   
			   AnimalDAO animalDAO = new AnimalDAO();
			   Animal animal = animalDAO.buscar(estadia.getAnimal().getCodigo());//pega o codigo do animal escolhido para hospedar
			   
	
			   if(estadia.getDataEntrada().getTime() <= estadia.getDataSaida().getTime()) {//verifica se a data de saida não é anterior a data de entrada
				   
				   try {
					   
					   estadia.setTotal(estadia.getValorCobrado().add(estadia.getGastoOutros()).add(estadia.getGastoRacao()).add(estadia.getGastoVacina()).subtract(estadia.getDesconto()));
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.merge(estadia);
					
					 if(estadia.getStatus() == false) {//verifica se durante a alteração a estadia foi fechada, se foi libera o animal para uma nova estadia
					   animal.setAlocado(false);
					   animalDAO.merge(animal);
					 }
					   
					   Messages.addGlobalInfo("Estadia alterada com sucesso");
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar a estadia");
						erro.printStackTrace();
				   }
				   
			   }else {
				   Messages.addFlashGlobalError("A data entrada não pode ser anterior a data de saida");
					
			   }
		   }
	
   }
	   
	   
	   
	   
	   public void mover(ScheduleEntryMoveEvent event) {
		   
		   for(Estadia ev : estadias ) {
			   if(ev.getCodigo() == (Long)event.getScheduleEvent().getData()) {
				   estadia = ev;
				   try {
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.editar(estadia);
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar mover a estadia para uma nova data");
						erro.printStackTrace();
				   }
			   }
			   break;
		   }
		   
	   }
	   
       public void redimensionar(ScheduleEntryResizeEvent event) {
		   
		   for(Estadia ev : estadias ) {
			   if(ev.getCodigo() == (Long)event.getScheduleEvent().getData()) {
				   estadia = ev;
				   try {
					   EstadiaDAO estadiaDAO = new EstadiaDAO();
					   estadiaDAO.editar(estadia);
					   init();
				   }catch(Exception erro) {
					   Messages.addFlashGlobalError("Ocorreu um erro ao tentar redimensionar a estadia");
						erro.printStackTrace();
				   }
			   }
			   break;
		   }
		   
	   }
       public void listar(){
    	EstadiaDAO estadiaDAO = new EstadiaDAO();
    	estadiaDAO.listar("dataEntrada");
       }
	   
    
	   
	   
}