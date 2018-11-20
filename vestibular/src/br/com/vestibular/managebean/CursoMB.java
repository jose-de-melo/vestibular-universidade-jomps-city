package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Curso;

public class CursoMB {
	
	private Curso curso;
	private List<Curso> cursos;
	
	public CursoMB() {
		curso = new Curso();
		cursos = new ArrayList<Curso>();
	}

	public void salvar() {
		DAO<Curso> dao = new DAO<>(Curso.class);
		
		if(curso.getCodcurso() != null)
			dao.adiciona(curso);
		else
			dao.altera(curso);
		
		curso = new Curso();
	}
	
	public List<Curso> getCursos() {
		DAO<Curso> dao = new DAO<>(Curso.class);
		cursos = dao.listaTodos();		
		return cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
