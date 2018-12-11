package br.com.vestibular.managebean;

import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Curso;
import br.com.vestibular.modelo.Sala;


@ViewScoped
@ManagedBean
public class SalaMB {

	private Sala sala;
	private List<Sala> salas = ordenarSalas(new DAO<>(Sala.class).listaTodos());

	public SalaMB() {
		sala = new Sala();
	}

	public void salvar() {
		DAO<Sala> dao = new DAO<>(Sala.class);
		
		System.out.println(sala);

		if(sala.getCodsala() == null) {
			Curso curso = new DAO<>(Curso.class).listaPorPK(sala.getCurso().getCodcurso());
			sala.setCurso(curso);
			dao.adiciona(sala);
			Mensagem.msgInfo("Sala cadastrada com sucesso!");
		}else {
			dao.altera(sala);
			Mensagem.msgInfo("Sala alterada com sucesso!");
		}

		sala = new Sala();
		salas = ordenarSalas(dao.listaTodos());
	}

	public Sala getSala() {
		return sala;
	}
	
	private List<Sala> ordenarSalas(List<Sala> salas){
		salas.sort(new Comparator<Sala>() {
			@Override
			public int compare(Sala sala1, Sala sala2) {
				return Integer.compare(sala1.getCodsala(), sala2.getCodsala());
			}
		});
		return salas;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}
}