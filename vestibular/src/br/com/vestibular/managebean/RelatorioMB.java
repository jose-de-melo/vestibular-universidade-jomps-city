package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.CandidatoDAO;
import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Curso;


@ViewScoped
@ManagedBean
public class RelatorioMB {
	
	private List<Resultado> resultados;
	

	public RelatorioMB() {
		resultados = new ArrayList<>();
		obterAprovados();
	}
	
	public void obterAprovados() {
		DAO<Curso> daoCurso = new DAO<>(Curso.class);
		
		CandidatoDAO daoCand = new CandidatoDAO();
		
		List<Curso> cursos = daoCurso.listaTodos();
		for(Curso c : cursos) {
			Resultado r = new Resultado();
			r.setCandidatos(daoCand.pesquisaPorCurso(c.getCodcurso()));
			r.setCurso(c);
			resultados.add(r);
		}
		
	}

	
	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}




	public class Resultado{
		
		private Curso curso;
		private List<Candidato> candidatos;
		
		
		public Resultado() {
			curso = new Curso();
			candidatos = new ArrayList<>();
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
		
	}
	
	
}
