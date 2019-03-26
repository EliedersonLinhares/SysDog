package com.esl.sysdog.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.esl.sysdog.dao.AnimalDAO;
import com.esl.sysdog.dao.EstadiaDAO;
import com.esl.sysdog.dao.LocalDAO;
import com.esl.sysdog.dao.UsuarioDAO;
import com.esl.sysdog.model.Animal;
import com.esl.sysdog.model.Estadia;
import com.esl.sysdog.model.Local;
import com.esl.sysdog.model.Usuario;
import com.esl.sysdog.util.JpaUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadiaListaBeanLazy implements Serializable {

	private Estadia estadia;
	private List<Estadia> estadias;
	
    private List<Animal> animais;
	private List<Local> locais;
	private List<Usuario> usuarios;
	
	
	private EstadiaDAO estadiaDAO;
	
   private LazyDataModel<Estadia> dataModel;
	
	
	
	public LazyDataModel<Estadia> getDataModel() {
		return dataModel;
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


	public EstadiaListaBeanLazy() {
		 dataModel = new LazyDataModel<Estadia>() {
				
				
				private static final long serialVersionUID = 1L;
				
				 @Override
				  public List<Estadia> load(int first, int pageSize, String sortField,
				                             SortOrder sortOrder, Map<String, Object> filters) {
					 
					 estadiaDAO = new EstadiaDAO();
					 
					 this.setRowCount( estadiaDAO.getTotalRegistros().intValue());
				     
					 List<Estadia> list =  estadiaDAO.listarLazy(first, pageSize, filters,sortField, sortOrder);
				      if (filters != null && filters.size() > 0) {
				          //otherwise it will still show all page links; pages at end will be empty
				          this.setRowCount( estadiaDAO.getColunasFiltradas(filters));
				      }
				      return list;
				  }
				
			};
	}
	
	public void salvar() {
		try {
			
			
			
	EstadiaDAO estadiaDAO = new EstadiaDAO();
	estadiaDAO.merge(estadia);
			
	estadia = new Estadia();//limpa o cliente
			
			
		AnimalDAO animalDAO = new AnimalDAO();
		animais = animalDAO.listar();
		
		LocalDAO localDAO = new LocalDAO();
		locais = localDAO.listar();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarios = usuarioDAO.listar();
				
			
			estadias = estadiaDAO.listar();
		    
		    Messages.addGlobalInfo("Estadia salva com sucesso");	
		}catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova estadia");
			erro.printStackTrace();
	  }
	}
	
	
	public void salvar2() {
		
			
	 AnimalDAO animalDAO = new AnimalDAO();	
	 Animal animal = animalDAO.buscar(estadia.getAnimal().getCodigo());
			
	 
	  
	  if(estadia.getDataEntrada().getTime() <= estadia.getDataSaida().getTime()) {//verifica se a data de saida não é anterior a data de entrada
	  
		  if(animal.getAlocado() == true){
				Messages.addFlashGlobalError("O animal "+ animal.getNome()+" tem uma estadia aberta feche a primeiro antes de abrir uma nova estadia");
				return;
			}	
		  
		  
		try {	
			
			
			estadia.setTotal(estadia.getValorCobrado().add(estadia.getGastoOutros()).add(estadia.getGastoRacao()).add(estadia.getGastoVacina()).subtract(estadia.getDesconto()));
			EstadiaDAO estadiaDAO = new EstadiaDAO();
			estadiaDAO.merge(estadia);
			
			
	    if(estadia.getStatus() == false) {//verifica se durante a alteração a estadia foi fechada, se foi libera o animal para uma nova estadia
				   
	    	animal.setAlocado(false);
		    animalDAO.merge(animal);
		    
		 }else if(estadia.getStatus() == true) {
			
			animal.setAlocado(true);
			animalDAO.merge(animal);
		 }	
			
			
		estadia = new Estadia();//limpa o cliente
			
			
		
		animais = animalDAO.listar();
		
		LocalDAO localDAO = new LocalDAO();
		locais = localDAO.listar();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarios = usuarioDAO.listar();
				
			
			estadias = estadiaDAO.listar();
		    
		    Messages.addGlobalInfo("Estadia salva com sucesso");	
		}catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova estadia");
			erro.printStackTrace();
	  }
	  }else {
		   Messages.addFlashGlobalError("A data entrada não pode ser anterior a data de saida");
			
	   }
	}
	
	
	
	
	public void editar(ActionEvent evento) {
		try {
			estadia = (Estadia)evento.getComponent().getAttributes().get("estadiaSelecionada");
			
			
			AnimalDAO animalDAO = new AnimalDAO();
			animais = animalDAO.listar();
			
			LocalDAO localDAO = new LocalDAO();
			locais = localDAO.listar();
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar();
			
			
		}catch(RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar uma estadia");
		}
	
	} 
	public void excluir(ActionEvent evento) {
		 
		try {
			estadia = (Estadia)evento.getComponent().getAttributes().get("estadiaSelecionada");
			
			
			 if(estadia.getStatus() == true) {
				 Messages.addGlobalInfo("Feche a estadia antes de exclui-la");
				 return;
				 }	
			
			
			EstadiaDAO estadiaDAO = new EstadiaDAO();
			estadiaDAO.excluir(estadia);
			
		
					
			

			estadias = estadiaDAO.listar();

			Messages.addGlobalInfo("Estadia removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover esta estadia");
			erro.printStackTrace();
		}
	} 
	
	public void imprimir2(ActionEvent evento) throws IOException {
		estadia = (Estadia)evento.getComponent().getAttributes().get("estadiaSelecionada");
			try {
				
		     Long codigo = estadia.getCodigo();
			//pegar o caminho do arquivo da memória
			String caminho = Faces.getRealPath("/relatorios/compestadia.jasper");

			Map<String, Object> parametros = new HashMap<>();//Guarda um nome e um valor, com uma estrutura de mapa
		
		    parametros.put("CODIGO_ESTADIA",codigo );//o valor ' %% ' indica que a tabela toda sera impresa
			
			Connection conexao = JpaUtil.getConexao();
			
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);//gera o relatorio populado
			
			LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			String agoraFormatado = agora.format(formatter);
			
			String reportname = "estadia " + estadia.getCodigo() +" " + agoraFormatado;
			
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			  response.reset();
			  response.setContentType("application/pdf");
			//  response.setHeader("Content-disposition", "attachment; filename=\"report.pdf\"");
			  response.setHeader("Content-disposition", "attachment; filename=\""+ reportname +".pdf\"");
			  ServletOutputStream stream = response.getOutputStream();
			  JasperExportManager.exportReportToPdfStream(relatorio, stream);
			FacesContext.getCurrentInstance().responseComplete();
			
		}catch(JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um relatório");
			erro.printStackTrace();
		}
		}
	
	
	
}