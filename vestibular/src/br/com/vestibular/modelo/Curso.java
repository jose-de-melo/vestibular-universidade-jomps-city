package br.com.vestibular.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Curso {
	
	@Id
	@SequenceGenerator(
			name="curso_id", 
			sequenceName="curso_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="curso_id")
	private Long id;
	
	@Column(unique=true)
	private Integer codcurso;
	private String nome;
	private String siglacurso;
	private Integer numvagas;
	private Integer totalinscritos;
	
	@OneToMany(mappedBy="curso")
	private List<Candidato> candidatos;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="curso")
	private List<Sala> salas;

	public Curso() {
		candidatos = new ArrayList<>();
		salas = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodcurso() {
		return codcurso;
	}

	public void setCodcurso(Integer codcurso) {
		this.codcurso = codcurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSiglacurso() {
		return siglacurso;
	}

	public void setSiglacurso(String siglacurso) {
		this.siglacurso = siglacurso;
	}

	public Integer getNumvagas() {
		return numvagas;
	}

	public void setNumvagas(Integer numvagas) {
		this.numvagas = numvagas;
	}

	public Integer getTotalinscritos() {
		return totalinscritos;
	}

	public void setTotalinscritos(Integer totalinscritos) {
		this.totalinscritos = totalinscritos;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	@Override
	public String toString() {
		return "Curso [codcurso=" + codcurso + ", nome=" + nome + ", siglacurso=" + siglacurso + ", numvagas="
				+ numvagas + ", totalinscritos=" + totalinscritos + ", candidatos=" + candidatos + ", salas=" + salas
				+ "]";
	}

}
