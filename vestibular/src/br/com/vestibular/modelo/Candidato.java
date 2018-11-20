package br.com.vestibular.modelo;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Candidato {
	
	@Id
	@Column(nullable = false)
	private String numInscricao;
	
	@Column(unique=true)
	private String CPF;
	private String nome;
	private String telefone;
	@Temporal(TemporalType.DATE)
	private Calendar datanascimento;
	
	@OneToOne
	private Curso curso;
	
	/* Endere�o */
	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String CEP;
	
	
	/* Dados prova */
	@OneToOne(cascade=CascadeType.PERSIST, mappedBy="candidato")
	private Nota nota;
	private String respostaprova;
	private Integer totalpontos;
	
	
	public Candidato() {
		datanascimento = Calendar.getInstance();
	}
	
	
	public String getNumInscricao() {
		return numInscricao;
	}
	public void setNumInscricao(String numInscricao) {
		this.numInscricao = numInscricao;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public Calendar getDatanascimento() {
		return datanascimento;
	}
	public void setDatanascimento(Calendar datanascimento) {
		this.datanascimento = datanascimento;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
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
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
	public Nota getNota() {
		return nota;
	}
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	public String getRespostaprova() {
		return respostaprova;
	}
	public void setRespostaprova(String respostaprova) {
		this.respostaprova = respostaprova;
	}
	public Integer getTotalpontos() {
		return totalpontos;
	}
	public void setTotalpontos(Integer totalpontos) {
		this.totalpontos = totalpontos;
	}


	@Override
	public String toString() {
		return "Candidato [numInscricao=" + numInscricao + ", CPF=" + CPF + ", nome=" + nome + ", telefone=" + telefone
				+ ", datanascimento=" + datanascimento + ", curso=" + curso + ", rua=" + rua + ", numero=" + numero
				+ ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ ", CEP=" + CEP + ", nota=" + nota + ", respostaprova=" + respostaprova + ", totalpontos="
				+ totalpontos + "]";
	}
	
}
