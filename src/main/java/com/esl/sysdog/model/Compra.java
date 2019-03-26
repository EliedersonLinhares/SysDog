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
@Table(name ="compra")
public class Compra {
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horario;
	
	@Column(nullable=false, precision= 7, scale= 2)
	private BigDecimal precoTotal;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Usuario usuario;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "compra")
	@Fetch(FetchMode.SELECT)//1
	private List<ItemCompra> itensCompra;

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



public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

public List<ItemCompra> getItensCompra() {
	return itensCompra;
}

public void setItensCompra(List<ItemCompra> itensCompra) {
	this.itensCompra = itensCompra;
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
	Compra other = (Compra) obj;
	if (codigo == null) {
		if (other.codigo != null)
			return false;
	} else if (!codigo.equals(other.codigo))
		return false;
	return true;
}

	
}

