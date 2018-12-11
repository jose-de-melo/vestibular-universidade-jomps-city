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
import br.com.vestibular.modelo.Curso;


@ViewScoped
@ManagedBean
public class RelAprovadosMB {
	
	private List<Resultado> resultados;
	private StreamedContent file;

	public RelAprovadosMB() {
		resultados = new ArrayList<>();
		obterAprovados();
		gerarArquivo();
	}
	
	private void gerarArquivo() {
		
		try {
			File arquivoTexto = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources") + "/file.txt");
			FileOutputStream outputStream = new FileOutputStream(arquivoTexto);
			Formatter formatter = new Formatter(outputStream);
			
			formatter.format("# Relatório de Aprovados\r\n\r\n");
			
			for(Resultado res : resultados) {
				
				formatter.format("Curso: %s\r\n", res.getCurso().getNome());
				formatter.format("%-12s %-35s %-20s %-8s %-11s\r\n", "Inscrição", "Nome", "Data de Nascimento", "Pontos", "Colocação");
				
				for(Candidato c : res.getCandidatos()) {
					formatter.format("%-12s %-35s %td-%3$tm-%3$-14tY %-8d %-11d\r\n", c.getNumInscricao(), c.getNome(),
								c.getDatanascimento(),
								(c.getTotalpontos() != null) ? c.getTotalpontos() : 0,
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

	public void obterAprovados() {
		DAO<Curso> daoCurso = new DAO<>(Curso.class);
		
		CandidatoDAO daoCand = new CandidatoDAO();
		
		List<Curso> cursos = daoCurso.listaTodos();
		for(Curso c : cursos) {
			Resultado r = new Resultado();
			List<Candidato> candidatos = daoCand.pesquisaPorCurso(c.getCodcurso());
			candidatos = obterAprovadosDaLista(candidatos, c);
			
			r.setCandidatos(candidatos);
			r.setCurso(c);
			resultados.add(r);
		}
		
	}

	
	private List<Candidato> obterAprovadosDaLista(List<Candidato> candidatos, Curso curso) {
		if(candidatos.isEmpty()) return candidatos;
		
		// Ordena pela colocação
		Collections.sort(candidatos, new Candidato.ComparadorColocacao());
		
		int numVagas = curso.getNumvagas();
		if(numVagas > candidatos.size())
			numVagas = candidatos.size();
		
		return candidatos.subList(0, numVagas);
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
			file = new DefaultStreamedContent(stream, "text/plain", "relatorio_aprovados.txt");
		}catch (Exception e) {
			e.printStackTrace();
		}
     
        return file;
    }
	
	
	public static class Resultado{
		
		private Curso curso;
		private List<Candidato> candidatos;
		
		
		public Resultado() {
			curso = new Curso();
			candidatos = new ArrayList<>();
		}
		public Curso getCurso() {
			return curso;
		}
		public void setCurso(Curso curso) {
			this.curso = curso;
		}
		public List<Candidato> getCandidatos() {
			return candidatos;
		}
		public void setCandidatos(List<Candidato> candidatos) {
			this.candidatos = candidatos;
		}
		
	}
	


	
}