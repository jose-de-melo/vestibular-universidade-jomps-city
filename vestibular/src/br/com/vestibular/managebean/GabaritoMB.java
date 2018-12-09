package br.com.vestibular.managebean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Gabarito;



@ViewScoped
@ManagedBean
public class GabaritoMB {
	
	//private UploadedFile gabaritoFile;
	private String gabaritoString;
	
	
	public static void main(String[] args) {
		new GabaritoMB().upload();
	}
	
	public void upload() {
		try {
			
			/* System.out.println(gabaritoFile.getFileName());
			System.out.println(gabaritoFile.getContentType());
			System.out.println(gabaritoFile.getInputstream());			
			Scanner file = new Scanner(gabaritoFile.getInputstream());
			System.out.println(file.nextLine());
			file.close(); */
			
			salvarGabarito(gabaritoString);
			Mensagem.msgInfo("Gabarito salvo com sucesso.");
			
		 
		  } catch(IOException e) {
			  Mensagem.msgErro("Erro ao fazer upload do gabarito.");
		  } catch (Exception e) {
			  e.printStackTrace();
			  Mensagem.msgErro(e.getMessage());
		  } 
	}
	
	private void salvarGabarito(String gabaritoS) throws Exception {
		String respostas[] = obterRespostas(gabaritoS);


		List<Gabarito> gabaritos = new ArrayList<>();
		int questao = 0;
		
		DAO<Gabarito> dao = new DAO<>(Gabarito.class);
		gabaritos = dao.listaTodos();
	
		if(gabaritos.isEmpty()) {
			// Cadastrar
			for(String res : respostas) {
				res = res.toUpperCase();
				gabaritos.add(new Gabarito());		
			
				gabaritos.get(questao).setReposta(res.charAt(0));
				questao++;
			}
		}else {
			// Alterar
			for(Gabarito g : gabaritos) {
				String res = respostas[g.getNumeroquestao()-1].toUpperCase();
				g.setReposta(res.charAt(0));
			}
		}	
		
		dao.altera(gabaritos.toArray(new Gabarito[0]));
	}

	private String[] obterRespostas(String gabaritoS) throws Exception {
		if(!gabaritoS.matches("[ABCDEabcde]*"))
			throw new Exception(String.format("Apenas A, B, C, D ou E são respostas válidas."));
		
		String respostas[] = gabaritoS.trim().split("");
		
		if(respostas.length < 50)
			throw new Exception(String.format("Apenas %d de 50 respostas foram encontradas.", respostas.length));
		
		return respostas;
	}

	/*public UploadedFile getGabaritoFile() {
		return gabaritoFile;
	}

	public void setGabaritoFile(UploadedFile gabaritoFile) {
		this.gabaritoFile = gabaritoFile;
	}
	 */
	public String getGabaritoString() {
		return gabaritoString;
	}

	public void setGabaritoString(String gabaritoString) {
		this.gabaritoString = gabaritoString;
	}

}

