package com.esl.sysdog.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name ="despesa")
public class Despesa {
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	@Column(length =40, nullable=false)
	private String tipo;
	
	@Column(length = 80, nullable=false)
	private String descricao;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;
	
	@Column(nullable=false, precision= 7, scale= 2)
	private BigDecimal valor;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;
	
	
	@Column(length = 20, nullable=false)
	private String status;
	
	
	

	public Long getCodigo() {
		return codigo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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
		Despesa other = (Despesa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}

