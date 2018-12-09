package br.com.vestibular.managebean;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Candidato;

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
				DAW00001ABCDDCEABCAAACCBDEADABCDDCEABCAAACCBDEADABCDADCEAB 
				DAW00002ABCDDCEABCAAACCBDEADABCDDCEABCAAACCBDEADABCDADCEAB
			 */
			
			realizarCorrecao(respostasString);
			Mensagem.msgInfo("Correção realizada com sucesso.");

		} catch(IOException e) {
			Mensagem.msgErro("Erro ao fazer upload das respostas.");
		} catch (Exception e) {
			e.printStackTrace();
			Mensagem.msgErro(e.getMessage());
		} 
	}
	
	private void realizarCorrecao(String respostasS) throws Exception {
		ArrayList<Candidato> candidatos = obterCandidatosComRespostas(respostasS);
		
		for(Candidato c : candidatos){
			System.out.println(c.getNome());
			System.out.println(c.getRespostaprova());
			System.out.println();
			//String respostas[] = c.getRespostaprova().split("");
		}
		
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
			
			// Verifica se o número de inscrição é válido.
			if(c == null)
				throw new Exception(String.format("Numero de inscrição do %dº candidato é inválido", candRespostas.size()));
			
			// Verifica as respostas do candidato
			c.setRespostaprova(resposta.substring(8).trim());
			if(!c.getRespostaprova().matches("[ABCDEabcde]*"))
				throw new Exception(String.format("O %dº candidato possui resposta inválida", candRespostas.size()));
			
			// Verifica o número de respostas
			if(c.getRespostaprova().length() < 50)
				throw new Exception(String.format("O %dº candidato possui apenas %d respostas.", candRespostas.size(), c.getRespostaprova().length()));
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
