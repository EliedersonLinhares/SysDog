package com.esl.sysdog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name ="usuario")
public class Usuario  {
	
	
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
	private String nome;
	
	@Column(length= 50, nullable = false)
	private String email;
	
	@Column(length = 70, nullable = false)
	private String senha;
	
	@Column(length = 32, nullable = false, unique=true)
	private String login;
	
	@Transient
	private String senhaSemCriptografia;
	
	@Column(nullable = false)
	private Character tipo;
	
	@Column(nullable = false)
	private Boolean ativo;
	
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Avatar avatar;
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Character getTipo() {
		return tipo;
	}
	
	/*retorna um tipo formatado de dado para exibição na tela, no BD continua
	 * sendo gravado os dados normais
	*/
	@Transient//Anotação do hibernate para informar que o campo não é um dado a ser gravado
	public String getTipoFormatado() {
		String tipoFormatado = null;
		
		if(tipo == 'A') {
			tipoFormatado = "Administrador";
		}else if(tipo == 'F') {
			tipoFormatado = "Funcionário";
		
		}
		
		return tipoFormatado;
	}
	//

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}
	
	@Transient
	public String getAtivoFormatado() {
		String ativoFormatado = "Não";
				if(ativo) {
					ativoFormatado = "Sim";
				}
				
				return ativoFormatado;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}


public String getSenhaSemCriptografia() {
	return senhaSemCriptografia;
}
public void setSenhaSemCriptografia(String senhaSemCriptografia) {
	this.senhaSemCriptografia = senhaSemCriptografia;
}


public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public Avatar getAvatar() {
	return avatar;
}
public void setAvatar(Avatar avatar) {
	this.avatar = avatar;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
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
	Usuario other = (Usuario) obj;
	if (codigo == null) {
		if (other.codigo != null)
			return false;
	} else if (!codigo.equals(other.codigo))
		return false;
	return true;
}


}
/*
 * O campo senha tem o tamanho de 32 pois o formato de encripitação da mesma será
 * o MD5 que usa os 32 caracteres
 * 
 */
