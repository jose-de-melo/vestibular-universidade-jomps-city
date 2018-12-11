package br.com.vestibular.managebean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.vestibular.dao.CandidatoDAO;
import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Sala;

@ManagedBean
@ViewScoped
public class RelCandSalaMB {
	
	private List<Resultado> resultados;
	private StreamedContent file;

	public RelCandSalaMB() {
		resultados = new ArrayList<>();
		obterCandidatosSala();
		gerarArquivo();
	}
	
	private void gerarArquivo() {
		
		try {
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources"));
			File arquivoTexto = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources") + "/file.txt");
			FileOutputStream outputStream = new FileOutputStream(arquivoTexto);
			Formatter formatter = new Formatter(outputStream);
			
			formatter.format("# Relatório de Candidatos por Sala\r\n\r\n");
			
			for(Resultado res : resultados) {
				
				formatter.format("Sala: %05d  Capacidade: %d\r\n", res.getSala().getCodsala(), res.getSala().getCapacidade());
				formatter.format("%-12s %-35s %-20s %-11s\r\n", "Inscrição", "Nome", "Data de Nascimento", "Colocação");
				
				for(Candidato c : res.getCandidatos()) {
					formatter.format("%-12s %-35s %td-%3$tm-%3$-14tY %-11d\r\n", c.getNumInscricao(), c.getNome(),
								c.getDatanascimento(),
								c.getColocacao());
				}
		
				formatter.format("Total de Candidatos: %d\r\n\r\n", res.getCandidatos().size());
			}
			
			formatter.close();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void obterCandidatosSala() {
		DAO<Sala> daoSala = new DAO<>(Sala.class);
		
		CandidatoDAO daoCand = new CandidatoDAO();
		
		List<Sala> salas = daoSala.listaTodos();
		for(Sala s : salas) {
			Resultado r = new Resultado();
			
			List<Candidato> candidatos = daoCand.pesquisaPorSala(s.getCodsala());
			
			// Ordena por Nome
			Collections.sort(candidatos, new Candidato.ComparadorNome());
			
			r.setCandidatos(candidatos);
			r.setSala(s);
			resultados.add(r);
		}
		
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}
	
	public StreamedContent getFile() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/file.txt");
			file = new DefaultStreamedContent(stream, "text/plain", "relatorio_candidatos_sala.txt");
		}catch (Exception e) {
			e.printStackTrace();
		}
     
        return file;
    }	
	
	public static class Resultado{
		
		private Sala sala;
		private List<Candidato> candidatos;
		
		
		public Resultado() {
			sala = new Sala();
			candidatos = new ArrayList<>();
		}
		
		public Sala getSala() {
			return sala;
		}

		public void setSala(Sala sala) {
			this.sala = sala;
		}

		public List<Candidato> getCandidatos() {
			return candidatos;
		}
		public void setCandidatos(List<Candidato> candidatos) {
			this.candidatos = candidatos;
		}
		
	}

	
}
