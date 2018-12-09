package br.com.vestibular.managebean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Candidato;
import br.com.vestibular.modelo.Gabarito;
import br.com.vestibular.modelo.Nota;

@ViewScoped
@ManagedBean
public class CorrecaoMB {

	//private UploadedFile respostasFile;
	private String respostasString;
	
	public static void main(String[] args) {
		new CorrecaoMB().upload();
	}

	public void upload() {
		try {
			
			/*
				DAW00001ABCEDCEABCAAACCBDEADABCDDCCABCAEACCBDEADABCDADCEAB 
				DAW00002EBCDDCEABCAAACCBDEADABCDDCEABCAAACCBDEADABCDADCEBB
			 */
			
			realizarCorrecao(respostasString);
			Mensagem.msgInfo("Corre��o realizada com sucesso.");

		} catch(IOException e) {
			Mensagem.msgErro("Erro ao fazer upload das respostas.");
		} catch (Exception e) {
			e.printStackTrace();
			Mensagem.msgErro(e.getMessage());
		} 
	}
	
	private void realizarCorrecao(String respostasS) throws Exception {
		ArrayList<Candidato> candidatos = obterCandidatosComRespostas(respostasS);
		DAO<Nota> daoNota = new DAO<>(Nota.class);
		
		List<Gabarito> gabaritos = new DAO<>(Gabarito.class).listaTodos();
		List<Nota> notas = new ArrayList<>();
		for(Candidato c : candidatos){
			System.out.println(c.getNome());
			System.out.println(c.getRespostaprova());
			System.out.println();
			
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

	private ArrayList<Candidato> obterCandidatosComRespostas(String respostasS) throws Exception {
		String respostasProvas[] = respostasS.trim().split("\n");
		
		DAO<Candidato> dao = new DAO<>(Candidato.class);
		ArrayList<Candidato> candRespostas = new ArrayList<>();
		
		for(String resposta : respostasProvas) {
			Candidato c = new Candidato();
			
			c.setNumInscricao(resposta.substring(0, 8));
			c = dao.listaPorPK(c.getNumInscricao());
			candRespostas.add(c);
			
			// Verifica se o n�mero de inscri��o � v�lido.
			if(c == null)
				throw new Exception(String.format("Numero de inscri��o do %d� candidato � inv�lido", candRespostas.size()));
			
			// Verifica as respostas do candidato
			c.setRespostaprova(resposta.substring(8).trim());
			if(!c.getRespostaprova().matches("[ABCDEabcde]*"))
				throw new Exception(String.format("O %d� candidato possui resposta inv�lida", candRespostas.size()));
			
			// Verifica o n�mero de respostas
			if(c.getRespostaprova().length() < 50)
				throw new Exception(String.format("O %d� candidato possui apenas %d respostas.", candRespostas.size(), c.getRespostaprova().length()));
			
			c.setRespostaprova(c.getRespostaprova().toUpperCase());
		}
		
		return candRespostas;
	}

	/*public UploadedFile getRespostasFile() {
		return respostasFile;
	}

	public void setRespostasFile(UploadedFile respostasFile) {
		this.respostasFile = respostasFile;
	}*/

	public String getRespostasString() {
		return respostasString;
	}

	public void setRespostasString(String respostasString) {
		this.respostasString = respostasString;
	}
	
}
