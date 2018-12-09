package br.com.vestibular.managebean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Curso;


@SessionScoped
@ManagedBean
public class CandidatoMB {
	
	private Candidato candidato;
	private List<Candidato> candidatos;
	private Integer codCurso;

	public CandidatoMB() {
		candidato = new Candidato();
	}
	
	public void salvar() {
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		
		if(candidato.getNumInscricao() == null) {
			// Obtém o curso no BD pelo codigo forneciso no cadastro.
			Curso curso = new DAO<>(Curso.class).listaPorPK(codCurso);
			candidato.setCurso(curso);
			
			// Obtém a nova inscrição para o curso.
			Integer proxInscricao = curso.getCandidatos().size() + 1;
			String inscricao = String.format("%s%05d", curso.getSiglacurso(), proxInscricao);
			candidato.setNumInscricao(inscricao);
			
			dao.adiciona(candidato);
			Mensagem.msgInfo("Candidato cadastrado com sucesso!");
		}else {
			dao.altera(candidato);
			Mensagem.msgInfo("Candidato alterado com sucesso!");
		}
		
		candidato = new Candidato();
	}
	
	public void remove(Candidato candidato) {
		System.out.println("Candidato 2 será removido: " + candidato.getNumInscricao() + "\t" + candidato.getNome());		
	}
	

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Integer getCodCurso() {
		return codCurso;
	}

	public void setCodCurso(Integer codCurso) {
		this.codCurso = codCurso;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
}
