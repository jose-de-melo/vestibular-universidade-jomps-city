package br.com.vestibular.managebean;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Curso;

public class CandidatoMB {
	
	private Candidato candidato;

	public CandidatoMB() {
		candidato = new Candidato();
	}
	
	public void salvar() {
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		
		if(candidato.getNumInscricao() != null) {
			// Obtém o curso no BD pelo codigo forneciso no cadastro.
			Curso curso = new DAO<>(Curso.class).listaPorPK(candidato.getCurso().getCodcurso());
			candidato.setCurso(curso);
			
			// Obtém a nova inscrição para o curso.
			Integer proxInscricao = curso.getCandidatos().size() + 1;
			String inscricao = String.format("%s-%05d", curso.getSiglacurso(), proxInscricao);
			candidato.setNumInscricao(inscricao);
			
			dao.adiciona(candidato);
		}else
			dao.altera(candidato);
		
		candidato = new Candidato();
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

}
