package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Curso;

@ManagedBean
@ViewScoped
public class CursoMB {

	private Curso curso;
	private List<Curso> cursos;

	public CursoMB() {
		curso = new Curso();
		cursos = new ArrayList<Curso>();
	}

	public void salvar() {
		DAO<Curso> dao = new DAO<>(Curso.class);
		
		if(curso.getCodcurso() == null) {
			curso.setTotalinscritos(0);
			dao.adiciona(curso);
			Mensagem.msgInfo("Curso cadastrado com sucesso!");
		}else {
			dao.altera(curso);
			Mensagem.msgInfo("Curso alterado com sucesso!");
		}

		this.curso = new Curso();
		getCursos();
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
