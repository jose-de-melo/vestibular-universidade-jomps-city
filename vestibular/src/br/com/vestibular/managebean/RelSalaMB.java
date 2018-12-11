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

import br.com.vestibular.dao.DAO;
import br.com.vestibular.modelo.Sala;

@ManagedBean
@ViewScoped
public class RelSalaMB {
	
	private List<Sala> salas;
	private StreamedContent file;

	public RelSalaMB() {
		salas = new ArrayList<>();
		obterSalas();
		gerarArquivo();
	}
	
	public void obterSalas() {
		DAO<Sala> dao = new DAO<>(Sala.class);
		salas = dao.listaTodos();
			
		// Ordena por Código
		Collections.sort(salas);
	}
	
	private void gerarArquivo() {
		
		try {
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources"));
			File arquivoTexto = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources") + "/file.txt");
			FileOutputStream outputStream = new FileOutputStream(arquivoTexto);
			Formatter formatter = new Formatter(outputStream);
			
			formatter.format("# Relatório Salas\r\n\r\n");
			
			formatter.format("%-12s %-11s %-30s %-20s\r\n", "Código Sala", "Capacidade", "Atríbuida ao Curso", "Total de Candidatos");
			for(Sala s : salas) {
				
				formatter.format("%-12s %-11s %-30s %-20s\r\n", s.getCodsala(), s.getCapacidade(), s.getCurso().getNome(), s.getCandidatos().size());
			}
			
			formatter.close();
			outputStream.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StreamedContent getFile() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/file.txt");
			file = new DefaultStreamedContent(stream, "text/plain", "relatorio_salas.txt");
		}catch (Exception e) {
			e.printStackTrace();
		}
     
        return file;
    }

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}
	
}
