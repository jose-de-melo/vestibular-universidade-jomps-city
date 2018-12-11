package br.com.vestibular.managebean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Sala;

@ManagedBean
@ViewScoped
public class ConsultaSalaMB {
	private Integer codSala;
	private List<Sala> salas;
	
	public void consultar() {
		if(codSala != null) {
			DAO<Sala> dao = new DAO<>(Sala.class);
			Sala sala = dao.listaPorPK(codSala);
			if(sala == null) {
				if(salas != null)
					salas.clear();
				Mensagem.msgErro("Nenhuma sala encontrada para o código fornecido!");
			}
			else {
				if(salas != null) {
					salas.clear();
				}else {
					salas = new ArrayList<>();
				}
				salas.add(sala);
			}
		}
		else {
			Mensagem.msgErro("Forneça um código válido!");
		}
		
		codSala = null;
	}
	
	public void remover(Sala sala) {
		DAO<Sala> dao = new DAO<>(Sala.class);
		salas.clear();
		dao.remove(sala);
		Mensagem.msgDelete("A sala foi removida com sucesso!");
		
	}
	
	public Integer getCodSala() {
		return codSala;
	}
	public void setCodSala(Integer codSala) {
		this.codSala = codSala;
	}
	public List<Sala> getSalas() {
		return salas;
	}
	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}
}