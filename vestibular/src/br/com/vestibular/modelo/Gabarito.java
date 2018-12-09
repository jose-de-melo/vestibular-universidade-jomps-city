package br.com.vestibular.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Gabarito {

	@Id
	@SequenceGenerator(
			name="gabarito_id", 
			sequenceName="gabarito_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gabarito_id")
	private Integer numeroquestao;
	private Character reposta;
	
	public Gabarito() {
		
	}

	public Integer getNumeroquestao() {
		return numeroquestao;
	}

	public void setNumeroquestao(Integer numeroquestao) {
		this.numeroquestao = numeroquestao;
	}

	public Character getReposta() {
		return reposta;
	}

	public void setReposta(Character reposta) {
		this.reposta = reposta;
	}

	@Override
	public String toString() {
		return "Gabarito [numeroquestao=" + numeroquestao + ", reposta=" + reposta + "]";
	}

}
