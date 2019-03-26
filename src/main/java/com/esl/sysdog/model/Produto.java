package com.esl.sysdog.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name ="produto")
public class Produto{
	
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@Column(length = 50, nullable = false)
	private String tipo;
	
	@Column(length = 10, nullable = false)
	private BigDecimal peso;

	@Column(length = 120, nullable = false)
	private String descricao;
	
	@Column(nullable = true)
	private Short quantidade;
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal preco;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;
	



	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}



	

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}

/*
 * O atributo "Short" precisa ser escrito com o "S" maiusculo pois o mesmo usa null como 
 * atributo inicial, já o "short" usa "0" como atrinuto inicial o que pode causar problemas 
 * com indentificação de dados digitados pelo usuário
 * 
 * No campo preço o uso do BigDecimal é indicado por ser o melhor para arrendondamento de valores
 * 
 * O @Column do campo preço irá conter ,alem do nullbale, dois novos atributos o "precision" que seta o numero de casas a serem usadas
 * para 1300,00 precision 6 para 13000,00 precision 7, o "scale" definie quantas casas depois da virgula
*/
