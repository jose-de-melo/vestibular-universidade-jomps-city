package br.com.vestibular.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Nota {
	
	@Id
	@SequenceGenerator(
			name="candidato_id", 
			sequenceName="candidato_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="candidato_id")
	private Long id;
	
	@OneToOne
	private Candidato candidato;
	private Integer acertos;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Candidato getCandidato() {
		return candidato;
	}
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	public Integer getAcertos() {
		return acertos;
	}
	public void setAcertos(Integer acertos) {
		this.acertos = acertos;
	}
	
}
