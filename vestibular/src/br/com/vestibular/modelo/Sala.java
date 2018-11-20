package br.com.vestibular.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Sala {
	
	@Id
	@SequenceGenerator(
			name="sala_cod", 
			sequenceName="sala_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sala_cod")
	private Integer codsala;
	private Integer capacidade;

	@ManyToOne
	private Curso curso;

	public Sala() {
		
	}

	public Integer getCodsala() {
		return codsala;
	}

	public void setCodsala(Integer codsala) {
		this.codsala = codsala;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Sala [codsala=" + codsala + ", capacidade=" + capacidade + ", curso=" + curso + "]";
	}
	
}
