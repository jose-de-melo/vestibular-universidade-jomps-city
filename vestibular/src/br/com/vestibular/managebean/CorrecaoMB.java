package br.com.vestibular.managebean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.vestibular.dao.CandidatoDAO;
import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Curso;
import br.com.vestibular.modelo.Gabarito;
import br.com.vestibular.modelo.Nota;

@ViewScoped
@ManagedBean
public class CorrecaoMB {
	
	private UploadedFile file;
	private StreamedContent arquivo;
	private String respostasString;
	
   
    
	
	public void carregarArquivo() {
		if(file != null) {
           try {
			InputStream iS = file.getInputstream();
			Scanner scanner = new Scanner(iS);
			
			List<String> linhasDoArquivo = new ArrayList<String>();
			
			while (scanner.hasNext()) {
				linhasDoArquivo.add(scanner.nextLine());
			}
			
			realizarCorrecao(linhasDoArquivo);
			refazerColocacao();
			
			Mensagem.msgInfo("Correção realizada com sucesso.");
			
		} catch (IOException e) {
			Mensagem.msgErro("Erro ao fazer upload das respostas.");
			
		} catch (Exception e) {
			e.printStackTrace();
			Mensagem.msgErro(e.getMessage());
		}
           
        }else {
        	Mensagem.msgErro("O arquivo não foi carregado!");
        }
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		if(event.getFile() != null)
			Mensagem.msgInfo("Arquivo carregado com sucesso!");
    }
	
	private void refazerColocacao() {
		List<Curso> cursos = new DAO<>(Curso.class).listaTodos();
		
		CandidatoDAO daoCand = new CandidatoDAO();
		List<Candidato> candidatos;
		for(Curso c : cursos) {
			candidatos = daoCand.pesquisaPorCurso(c.getCodcurso());
			Collections.sort(candidatos);
			
			int colocacao = 0;
			for(Candidato cand : candidatos) {
				cand.setColocacao(++colocacao);
			}
			
			daoCand.altera(candidatos.toArray(new Candidato[0]));
		}
	}

	private void realizarCorrecao(List<String> linhasDoArquivo) throws Exception {
		ArrayList<Candidato> candidatos = obterCandidatosComRespostas(linhasDoArquivo);
		DAO<Nota> daoNota = new DAO<>(Nota.class);
		
		List<Gabarito> gabaritos = new DAO<>(Gabarito.class).listaTodos();
		List<Nota> notas = new ArrayList<>();
		for(Candidato c : candidatos){
			Nota nota = c.getNota();
			if(nota == null)
				nota = new Nota();
			
			nota.setCandidato(c);
			
			int acertos = 0;
			for(Gabarito questao : gabaritos) {
				char resposta = c.getRespostaprova().charAt(questao.getNumeroquestao() - 1);
				if(resposta == questao.getReposta())
					acertos++;
			}
			
			nota.setAcertos(acertos);
			notas.add(nota);
			
			c.setTotalpontos(nota.getAcertos() * 2);
		}
		
		daoNota.altera(notas.toArray(new Nota[0]));
		new DAO<>(Candidato.class).altera(candidatos.toArray(new Candidato[0]));
	}

	private ArrayList<Candidato> obterCandidatosComRespostas(List<String> linhasDoArquivo) throws Exception {
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		ArrayList<Candidato> candRespostas = new ArrayList<>();
		
		for(String resposta : linhasDoArquivo) {
			Candidato c = new Candidato();
			resposta = resposta.trim();

			// Verifica se possui número de inscrição e respostas
			if(resposta.length() < 59)
				throw new Exception(String.format("Numero de inscrição ou número de respostas do %dº candidato é inválido", candRespostas.size() + 1));
			
			c.setNumInscricao(resposta.substring(0, 8));
			c = dao.listaPorPK(c.getNumInscricao());
			candRespostas.add(c);
			
			// Verifica se o número de inscrição é válido.
			if(c == null)
				throw new Exception(String.format("Numero de inscrição do %dº candidato é inválido", candRespostas.size()));
			
			c.setRespostaprova(resposta.substring(9));
			// Verifica as respostas do candidato
			if(!c.getRespostaprova().matches("[ABCDEabcde]*"))
				throw new Exception(String.format("O %dº candidato possui resposta inválida", candRespostas.size()));
			
			c.setRespostaprova(c.getRespostaprova().toUpperCase());
		}
		
		return candRespostas;
	}

	public String getRespostasString() {
		return respostasString;
	}

	public void setRespostasString(String respostasString) {
		this.respostasString = respostasString;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}
	
	 public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
