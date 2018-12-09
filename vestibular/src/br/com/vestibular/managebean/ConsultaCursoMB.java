package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Curso;

@ViewScoped
@ManagedBean
public class ConsultaCursoMB {
	private Curso curso;
	private Integer codCurso;
	private List<Curso> cursos;
	
	
	public void consultarCurso() {
		DAO<Curso> dao = new DAO<>(Curso.class);
		curso = dao.listaPorPK(codCurso);
		cursos = new ArrayList<Curso>();
		
		if(curso == null) {
			Mensagem.msgErro("Nenhum curso encontrado para o código fornecido!");
		}else {
			cursos.add(curso);
		}
		
		codCurso = null;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getCodCurso() {
		return codCurso;
	}

	public void setCodCurso(Integer codCurso) {
		this.codCurso = codCurso;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
}