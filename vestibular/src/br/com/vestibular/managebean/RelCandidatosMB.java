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
import br.com.vestibular.modelo.Candidato;

@ManagedBean
@ViewScoped
public class RelCandidatosMB {
	
	private List<Candidato> candidatos;
	private StreamedContent file;

	public RelCandidatosMB() {
		candidatos = new ArrayList<>();
		obterCandidatos();
		gerarArquivo();
	}
	
	public void obterCandidatos() {
		CandidatoDAO daoCand = new CandidatoDAO();
		candidatos = daoCand.listaTodos();
			
		// Ordena por Nome do Candidato
		Collections.sort(candidatos, new Candidato.ComparadorNome());
	}
	
	private void gerarArquivo() {
		
		try {
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources"));
			File arquivoTexto = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources") + "/file.txt");
			FileOutputStream outputStream = new FileOutputStream(arquivoTexto);
			Formatter formatter = new Formatter(outputStream);
			
			formatter.format("# Relatório de Candidatos\r\n\r\n");
			
			formatter.format("%-12s %-35s %-20s %-11s %-14s\r\n", "Inscrição", "Nome", "Data de Nascimento", "Colocação", "Nome do Curso");
			for(Candidato c : candidatos) {
				
				formatter.format("%-12s %-35s %td-%3$tm-%3$-14tY %-11d %-14s\r\n", c.getNumInscricao(), c.getNome(),
							c.getDatanascimento(),
							c.getColocacao(),
							c.getCurso().getNome());
				
			}
			
			formatter.format("\r\nTotal de Candidatos: %d\r\n\r\n", candidatos.size());
			
			formatter.close();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StreamedContent getFile() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/file.txt");
			file = new DefaultStreamedContent(stream, "text/plain", "relatorio_candidatos.txt");
		}catch (Exception e) {
			e.printStackTrace();
		}
     
        return file;
    }

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
	
}
