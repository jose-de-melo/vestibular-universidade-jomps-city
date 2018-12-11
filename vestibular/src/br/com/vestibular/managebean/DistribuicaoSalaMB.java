package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.CandidatoDAO;
import br.com.vestibular.dao.DAO;
import br.com.vestibular.dao.SalaDAO;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Curso;
import br.com.vestibular.modelo.Sala;

@ManagedBean
@ViewScoped
public class DistribuicaoSalaMB {
	
	private List<Sala> salas;
	
	public DistribuicaoSalaMB() {
		salas = new ArrayList<>();
	}

	
	public String distribuir() {
		CandidatoDAO daoCand = new CandidatoDAO();
		SalaDAO daoSala = new SalaDAO();

		List<Curso> cursos = new DAO<>(Curso.class).listaTodos();
		List<Sala> salas;

		for(Curso c : cursos) {
			salas = daoSala.pesquisaPorCurso(c.getCodcurso());
			Collections.sort(c.getCandidatos(), new Candidato.ComparadorNome());
			
			// Indica o próxima candidato, que ainda não foi visitado.
			int prox_cand = 0;

			for(Sala s : salas) {
				for(int icd=prox_cand; icd < c.getCandidatos().size(); icd++) {
					Candidato cand = c.getCandidatos().get(icd);

					if(s.getCapacidade() == 0) {
						prox_cand = icd;
						break;
					}

					cand.setSala(s);
					s.setCapacidade(s.getCapacidade() - 1);
					prox_cand = icd + 1;
				}
			}
			
			// Zerar a sala dos outros candidatos podem já ter sido cadastrados.
			for(int icd=prox_cand; icd < c.getCandidatos().size(); icd++) {
				c.getCandidatos().get(icd).setSala(null);
			}

			// Atualiza os candidatos no BD para salvar o código da sala.
			daoCand.altera(c.getCandidatos().toArray(new Candidato[0]));
		}
		
		return "rel_candidato_sala?faces-redirect=true";		
	}

	public List<Sala> getSalas() {
		salas = new DAO<>(Sala.class).listaTodos();
		return salas;
	}

}
