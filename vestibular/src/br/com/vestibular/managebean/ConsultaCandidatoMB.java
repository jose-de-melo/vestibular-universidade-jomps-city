package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.CandidatoDAO;
import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Candidato;

@ViewScoped
@ManagedBean
public class ConsultaCandidatoMB {
	private Candidato candidato;
	private List<Candidato> candidatos;
	
	
	public ConsultaCandidatoMB() {
		candidato = new Candidato();
		candidatos = new ArrayList<Candidato>();
	}


	public void consultarInscricao() {	
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		Candidato c = dao.listaPorPK(candidato.getNumInscricao());
		
		if(c == null) {
			Mensagem.msgWarning("Candidato não encontrado.");
			this.candidatos = new ArrayList<>();
		}else {
			this.candidatos.add(c);
		}
	}
	
	public void consultarCurso() {
		this.candidatos = new CandidatoDAO().pesquisaPorCurso(candidato.getCurso().getCodcurso());
		
		if(this.candidatos.isEmpty())
			Mensagem.msgWarning("Nenhum candidato encontrado.");
	}

	public String redirectAlterar() {
		return  "candidato?faces-redirect=true";
	}

	public Candidato getCandidato() {
		return candidato;
	}


	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}


	public List<Candidato> getCandidatos() {
		return candidatos;
	}


	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
}
