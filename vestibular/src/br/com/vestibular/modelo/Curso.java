package br.com.vestibular.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Entity
public class Curso {
	
	@Id
	@SequenceGenerator(
			name="curso_cod", 
			sequenceName="curso_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="curso_cod")
	private Integer codcurso;
	
	@Size(min=1, message="O nome não pode ser vazio.")
	private String nome;
	
	@Size(min=3,message="A sigla do curso não pode ser vazia.")
	private String siglacurso;
	private Integer numvagas;
	private Integer totalinscritos;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="curso")
	private List<Candidato> candidatos;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="curso")
	private List<Sala> salas;

	public Curso() {
		candidatos = new ArrayList<>();
		salas = new ArrayList<>();
	}
	
	@Override
	public int hashCode() {
		if(codcurso != null && nome != null)
			return codcurso.hashCode() + nome.hashCode();
		
		return 0;
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
		this.siglacurso = (siglacurso != null)? siglacurso.toUpperCase() : null;
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


}
