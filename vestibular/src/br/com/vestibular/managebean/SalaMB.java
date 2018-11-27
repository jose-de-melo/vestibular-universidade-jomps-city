package br.com.vestibular.managebean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Sala;


@ViewScoped
@ManagedBean
public class SalaMB {
	
	private Sala sala;

	public SalaMB() {
		sala = new Sala();
	}
	
	public void salvar() {
		DAO<Sala> dao = new DAO<>(Sala.class);
		
		if(sala.getCodsala() != null) {
			/**  
			 * VER SE É NECESSARIO PERGAR A REFERENCIA DO CURSO NO BD.
			 * Obtém o curso no BD pelo codigo forneciso no cadastro.
			 * 
			Curso curso = new DAO<>(Curso.class).listaPorPK(sala.getCurso().getCodcurso());
			sala.setCurso(curso); */

			dao.adiciona(sala);
		}else
			dao.altera(sala);
		
		sala = new Sala();
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

}
