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
import br.com.vestibular.modelo.Gabarito;

@ManagedBean
@ViewScoped
public class RelGabaritoMB {
	
	private List<Gabarito> gabaritos;
	private StreamedContent file;

	public RelGabaritoMB() {
		gabaritos = new ArrayList<>();
		obterGabaritos();
		gerarArquivo();
	}
	
	public void obterGabaritos() {
		DAO<Gabarito> dao = new DAO<>(Gabarito.class);
		gabaritos = dao.listaTodos();
			
		// Ordena por questão
		Collections.sort(gabaritos);
	}
	
	private void gerarArquivo() {
		
		try {
			System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources"));
			File arquivoTexto = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources") + "/file.txt");
			FileOutputStream outputStream = new FileOutputStream(arquivoTexto);
			Formatter formatter = new Formatter(outputStream);
			
			formatter.format("# Relatório Gabarito\r\n\r\n");
			
			formatter.format("%-10s %-10s\r\n", "Questão", "Resposta");
			for(Gabarito g : gabaritos) {
				
				formatter.format("%-10s %-10s\r\n", g.getNumeroquestao(), g.getReposta());
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
			file = new DefaultStreamedContent(stream, "text/plain", "relatorio_gabarito.txt");
		}catch (Exception e) {
			e.printStackTrace();
		}
     
        return file;
    }

	public List<Gabarito> getGabaritos() {
		return gabaritos;
	}

	public void setGabaritos(List<Gabarito> gabaritos) {
		this.gabaritos = gabaritos;
	}	
	
}
