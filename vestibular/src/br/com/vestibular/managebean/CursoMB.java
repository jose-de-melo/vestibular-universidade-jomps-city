package br.com.vestibular.managebean;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Curso;

public class CursoMB {
	
	private Curso curso;
	
	public CursoMB() {
		curso = new Curso();
	}

	public void salvar() {
		DAO<Curso> dao = new DAO<>(Curso.class);
		
		if(curso.getId() != null)
			dao.adiciona(curso);
		else
			dao.altera(curso);
		
		curso = new Curso();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
