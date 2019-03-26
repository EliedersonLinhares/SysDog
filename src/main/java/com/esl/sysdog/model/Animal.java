package com.esl.sysdog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name ="animal")
public class Animal {
	
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	@Column(length =12, nullable=false)
	private String tipo;
	
	@Column(length = 50, nullable=false)
	private String nome;
	
	@Column(length = 5, nullable=false)
	private String sexo;
	
	@Column(nullable=false )
	private Short idade;
	
	@Column(length = 8, nullable=false)
	private String tamanho;

	@Column(length = 8, nullable=false)
	private String agressividade;

	@Column(length = 50, nullable=false)
	private String raca;

	@Column(nullable = false)
	private Boolean alocado;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Avatardog avatardog;
	
	
	@Column(nullable = false)
	private Boolean castrado;
	
	@Column(nullable = false)
	private Boolean vacinaemdia;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date vencimentoVacina;
	
	@Column(nullable = false)
	private Boolean carteiraVacinacao;
	
	@Column(nullable = false)
	private Boolean vermifugos;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataVermifugos;
	
	@Column(nullable = false)
	private Boolean pulgacarrapatos;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dataPulgaCarrapato;
	
	@Column(nullable = false)
	private Boolean doencaPrevia;
	
	@Column(length = 100)
	private String descricaoDoenca;
	
	@Column(nullable = false)
	private Boolean medicacao;
	
	@Column(length = 100)
	private String descricaoMedicacao;
	
	@Column(length = 80)
	private String receitaHorarios;
	
	@Column(length = 100)
	private String rotina;
	
	@Column(length = 50)
	private String doutorE;
	
	@Column(length = 13)
	private String telefoneE;
	
	@Column(length = 14)
	private String celularE;

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getAlocado() {
		return alocado;
	}
	public void setAlocado(Boolean alocado) {
		this.alocado = alocado;
	}



	public Short getIdade() {
		return idade;
	}

	public void setIdade(Short idade) {
		this.idade = idade;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getAgressividade() {
		return agressividade;
	}
	public void setAgressividade(String agressividade) {
		this.agressividade = agressividade;
	}


	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public Avatardog getAvatardog() {
		return avatardog;
	}

	public void setAvatardog(Avatardog avatardog) {
		this.avatardog = avatardog;
	}
	
	
	
	
	public Boolean getCastrado() {
		return castrado;
	}
	public void setCastrado(Boolean castrado) {
		this.castrado = castrado;
	}
	public Boolean getVacinaemdia() {
		return vacinaemdia;
	}
	public void setVacinaemdia(Boolean vacinaemdia) {
		this.vacinaemdia = vacinaemdia;
	}
	public Date getVencimentoVacina() {
		return vencimentoVacina;
	}
	public void setVencimentoVacina(Date vencimentoVacina) {
		this.vencimentoVacina = vencimentoVacina;
	}
	public Boolean getCarteiraVacinacao() {
		return carteiraVacinacao;
	}
	public void setCarteiraVacinacao(Boolean carteiraVacinacao) {
		this.carteiraVacinacao = carteiraVacinacao;
	}
	public Boolean getVermifugos() {
		return vermifugos;
	}
	public void setVermifugos(Boolean vermifugos) {
		this.vermifugos = vermifugos;
	}
	public Date getDataVermifugos() {
		return dataVermifugos;
	}
	public void setDataVermifugos(Date dataVermifugos) {
		this.dataVermifugos = dataVermifugos;
	}
	public Boolean getPulgacarrapatos() {
		return pulgacarrapatos;
	}
	public void setPulgacarrapatos(Boolean pulgacarrapatos) {
		this.pulgacarrapatos = pulgacarrapatos;
	}
	public Date getDataPulgaCarrapato() {
		return dataPulgaCarrapato;
	}
	public void setDataPulgaCarrapato(Date dataPulgaCarrapato) {
		this.dataPulgaCarrapato = dataPulgaCarrapato;
	}
	public Boolean getDoencaPrevia() {
		return doencaPrevia;
	}
	public void setDoencaPrevia(Boolean doencaPrevia) {
		this.doencaPrevia = doencaPrevia;
	}
	public String getDescricaoDoenca() {
		return descricaoDoenca;
	}
	public void setDescricaoDoenca(String descricaoDoenca) {
		this.descricaoDoenca = descricaoDoenca;
	}
	public Boolean getMedicacao() {
		return medicacao;
	}
	public void setMedicacao(Boolean medicacao) {
		this.medicacao = medicacao;
	}
	public String getDescricaoMedicacao() {
		return descricaoMedicacao;
	}
	public void setDescricaoMedicacao(String descricaoMedicacao) {
		this.descricaoMedicacao = descricaoMedicacao;
	}
	public String getReceitaHorarios() {
		return receitaHorarios;
	}
	public void setReceitaHorarios(String receitaHorarios) {
		this.receitaHorarios = receitaHorarios;
	}
	public String getRotina() {
		return rotina;
	}
	public void setRotina(String rotina) {
		this.rotina = rotina;
	}
	public String getDoutorE() {
		return doutorE;
	}
	public void setDoutorE(String doutorE) {
		this.doutorE = doutorE;
	}
	public String getTelefoneE() {
		return telefoneE;
	}
	public void setTelefoneE(String telefoneE) {
		this.telefoneE = telefoneE;
	}
	public String getCelularE() {
		return celularE;
	}
	public void setCelularE(String celularE) {
		this.celularE = celularE;
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
		Animal other = (Animal) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	

}
