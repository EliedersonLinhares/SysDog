package com.esl.sysdog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="fornecedor")
public class Fornecedor{
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	@Column(length = 50, nullable=false)
	private String nomeF;
	
	@Column(length = 13, nullable=false)
	private String telefone;
	
	@Column(length = 50)
	private String contato;

	@Column(length = 14)
	private String celular;
	
	@Column(length = 50)
	private String email;
	
	@Column(length = 80)
	private String descricaoF;



	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}



	public String getNomeF() {
		return nomeF;
	}

	public void setNomeF(String nomeF) {
		this.nomeF = nomeF;
	}

	public String getDescricaoF() {
		return descricaoF;
	}

	public void setDescricaoF(String descricaoF) {
		this.descricaoF = descricaoF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		Fornecedor other = (Fornecedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	

}
