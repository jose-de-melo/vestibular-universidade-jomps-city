package br.com.vestibular.managebean;

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

	public SalaMB() {
		sala = new Sala();
	}

	public void salvar() {
		DAO<Sala> dao = new DAO<>(Sala.class);
		
		System.out.println(sala);

		if(sala.getCodsala() == null) {
			Curso curso = new DAO<>(Curso.class).listaPorPK(sala.getCurso().getCodcurso());
			System.out.println("Curso: "+curso);
			sala.setCurso(curso);
			dao.adiciona(sala);
			Mensagem.msgInfo("Sala cadastrada com sucesso!");
		}else {
			dao.altera(sala);
			Mensagem.msgInfo("Sala alterada com sucesso!");
		}

		sala = new Sala();
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

}
