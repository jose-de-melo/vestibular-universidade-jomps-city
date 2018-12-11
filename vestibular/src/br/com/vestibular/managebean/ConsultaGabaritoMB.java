package br.com.vestibular.managebean;

import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Gabarito;

@ManagedBean
public class ConsultaGabaritoMB {
	private Integer numeroQuestao;
	private List<Gabarito> gabarito = inicializar();

	private List<Gabarito> inicializar() {
		List<Gabarito> gabarito = new DAO<>(Gabarito.class).listaTodos();
		gabarito.sort(new Comparator<Gabarito>() {
			@Override
			public int compare(Gabarito g1, Gabarito g2) {
				return Integer.compare(g1.getNumeroquestao(), g2.getNumeroquestao());
			}
		});
		return gabarito;
	}

	public void consultar() {
		if(gabarito.isEmpty()) {
			Mensagem.msgErro("Nenhum gabarito foi cadastrado!");
		}else {
			if(numeroQuestao != null) {
				if(numeroQuestao >= 1 && numeroQuestao <= 50) {
					Mensagem.msgInfo(String.format("Resposta para a questão %d: %s", numeroQuestao, gabarito.get(numeroQuestao-1).getReposta()));
				}else {
					Mensagem.msgErro("Forneça um número entre 1 e 50 para consultar o Gabarito!");
				}
			}
			else {
				Mensagem.msgErro("Número inválido!");
			}
		}


		numeroQuestao = null;
	}
	
	public void listarTodas() {
		if(gabarito.isEmpty()) {
			Mensagem.msgErro("Nenhum gabarito foi cadastrado!");
		}else {
			Mensagem.msgInfo("Gabarito");
			Mensagem.msgInfo("");
			for (Gabarito gab : this.gabarito) {
				Mensagem.msgInfo(String.format("Resposta para a questão %d: %s\n", gab.getNumeroquestao(), gab.getReposta()));
			}
		}
	}

	public Integer getNumeroQuestao() {
		return numeroQuestao;
	}

	public void setNumeroQuestao(Integer numeroQuestao) {
		this.numeroQuestao = numeroQuestao;
	}

	public List<Gabarito> getGabarito() {
		return gabarito;
	}

	public void setGabarito(List<Gabarito> gabarito) {
		this.gabarito = gabarito;
	}
}