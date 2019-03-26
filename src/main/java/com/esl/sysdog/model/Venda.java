package com.esl.sysdog.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name ="venda")
public class Venda  {

	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public BigDecimal getPrecoParcial() {
		return precoParcial;
	}

	public void setPrecoParcial(BigDecimal precoParcial) {
		this.precoParcial = precoParcial;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horario;
	
	@Column(nullable=false, precision= 7, scale= 2)
	private BigDecimal precoTotal;
	
	@Column(nullable=false, precision= 7, scale= 2)
	private BigDecimal desconto;
	
	@Column(nullable=false, precision= 7, scale= 2)
	private BigDecimal precoParcial;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(nullable=false)
	private Usuario usuario;
	
	
	//Um venda e varios itens vendas, carrega os filhos com base no pai
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "venda")
	@Fetch(FetchMode.SELECT)//1
	private List<ItemVenda> itensVenda;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
  
	public List<ItemVenda> getItensVenda() {
	return itensVenda;
    } 
   public void setItensVenda(List<ItemVenda> itensVenda) {
	this.itensVenda = itensVenda;
   }
   public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
   
   @Override
	public String toString() {
		return String.format("%s[codigo=%d]", getClass().getSimpleName(), getCodigo());
		//uso do Ominifaces para conversão de dados de caixas de seleção
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
/*
 * um cliente pode participar de varias vendas, mas uma venda só pode ter 
 * um cliente = ManytoOne
 * 
 * Implementação de venda a vista com cliente null para cliente sem cadastro,
 * podendo exister uma venda sem cliente então é uma agregação de venda para cliente
 * 
 * um funcionario pode participar de varias vendas, mas uma venda só pode ter um cliente
 * = ManytoOne
 * 
 * 1 - Em relacionamentos ToMany é necessario usar "@Fetch(FetchMode.SELECT)" para evitar dados duplicados se houver mais de um dado na
 * subtabela
 * Explicaçao em ingles:
 * https://stackoverflow.com/questions/11038234/pagination-with-hibernate-criteria-and-distinct-root-entity
 * 
 * Portugues
 * https://pt.stackoverflow.com/questions/137063/criteria-trazendo-dados-duplicados-do-hibernate-ao-jsf
 * 
 */
