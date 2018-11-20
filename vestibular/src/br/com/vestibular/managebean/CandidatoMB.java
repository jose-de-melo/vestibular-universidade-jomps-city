package br.com.vestibular.managebean;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Candidato;

public class CandidatoMB {
	
	private Candidato candidato;

	public CandidatoMB() {
		candidato = new Candidato();
	}
	
	public void salvar() {
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		
		if(candidato.getId() != null) {
			/** VER SE É NECESSÁRIO PEGAR A REFERENCIA DO CURSO NO BD ANTES DE SALVAR
			Curso curso = new DAO<>(Curso.class).litaPorId(candidato.getCurso().getId());
			candidato.setCurso(curso);**/
			
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
