package com.esl.sysdog.model;

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
@Table(name ="cliente")
public class Cliente  {
	
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(length = 50, nullable=false)
	private String nome;
	
	@Column(length = 14, nullable=false)
	private String cpf;
	
	@Column(length = 150, nullable=false)
	private String endereco;
	
	@Column(length = 30, nullable=false)
	private String bairro;
	
	@Column(length = 30, nullable=false)
	private String cidade;
	
	@Column(length = 2, nullable=false)
	private String estado;
	
	@Column(length = 10)
	private String cep;
	
	@Column(length = 13)
	private String telefone;
	
	@Column(length = 14, nullable=false)
	private String celular;
	
	@Column(length = 50)
	private String email;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(nullable=false)
	private Boolean liberado;
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getLiberado() {
		return liberado;
	}

	public void setLiberado(Boolean liberado) {
		this.liberado = liberado;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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
		Cliente other = (Cliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	

}
/* @Temporal é usado para manipulação de datas no hibernate, sendo 
 * DATE somente dadas TIME somente horas e TIMESTAMP data e horas
 * 
 * @OnetoOne é usado pois um cliente tem somente somente um cadastro como pessoa e pessoa so tem um cliente
 * isso impede cadastros duplicados.
 */
