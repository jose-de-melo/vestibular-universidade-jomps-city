package br.com.vestibular.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Sala implements Comparable<Sala> {
	
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
	
	@OneToMany(mappedBy="sala")
	private List<Candidato> candidatos;

	public Sala() {
		this.candidatos = new ArrayList<>();
		this.curso = new Curso();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		if(!(obj instanceof Sala)) return false;
		
		Sala sala = (Sala) obj;
		return this.codsala == sala.codsala;
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
	
	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	@Override
	public String toString() {
		return "Sala [codsala=" + codsala + ", capacidade=" + capacidade + ", curso=" + curso + "]";
	}

	@Override
	public int compareTo(Sala s) {
		if(codsala == null)
			return 1;
		
		if(s.codsala == null)
			return -1;
		
		
		return codsala.compareTo(s.codsala);
	}
	
	/**
	 * Ordena em ordem crescente da capacidade
	 */
	public static class ComparadorCapacidade implements Comparator<Sala>{

		@Override
		public int compare(Sala s1, Sala s2) {
			if(s1.capacidade == null)
				return 1;
			
			if(s2.capacidade == null)
				return -1;
			
			
			return s1.capacidade.compareTo(s2.capacidade);
		}
		
	}
	
}
