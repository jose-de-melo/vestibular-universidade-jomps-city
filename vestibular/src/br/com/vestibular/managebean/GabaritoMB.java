package br.com.vestibular.managebean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

	private List<String> gabaritos;


	public static void main(String[] args) {
		new GabaritoMB().upload();
	}



	public GabaritoMB() {
		gabaritos = new ArrayList<String>();
		gabaritos.add(getGabarito(new DAO<>(Gabarito.class).listaTodos()));
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
			gabaritoString = "";


		} catch(IOException e) {
			Mensagem.msgErro("Erro ao fazer upload do gabarito.");
		} catch (Exception e) {
			e.printStackTrace();
			Mensagem.msgErro(e.getMessage());
		}
		gabaritos = new ArrayList<String>();
		gabaritos.add(getGabarito(new DAO<>(Gabarito.class).listaTodos()));
	}

	private String getGabarito(List<Gabarito> listaTodos) {
		ordenarGabarito(listaTodos);
		
		String gabarito = "";
		for (Gabarito gab : listaTodos) {
			System.out.println(gab.getNumeroquestao());
			gabarito += gab.getReposta();
		}
		return gabarito;
	}
	
	private void ordenarGabarito(List<Gabarito> lista) {
		lista.sort(new Comparator<Gabarito>() {

			@Override
			public int compare(Gabarito gab1, Gabarito gab2) {
				return Integer.compare(gab1.getNumeroquestao(), gab2.getNumeroquestao());
			}
		});
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
			Mensagem.msgInfo("Gabarito cadastrado!");
		}else {
			ordenarGabarito(gabaritos);
			
			// Alterar
			for (int i = 0; i < respostas.length; i++) {
				gabaritos.get(i).setReposta(respostas[i].toUpperCase().charAt(0));
			}
			Mensagem.msgInfo("O gabarito foi atualizado!");
		}	

		dao.altera(gabaritos.toArray(new Gabarito[0]));
	}

	private String[] obterRespostas(String gabaritoS) throws Exception {
		if(!gabaritoS.matches("[ABCDEabcde]*"))
			throw new Exception(String.format("Apenas A, B, C, D ou E são respostas válidas."));

		String respostas[] = gabaritoS.trim().split("");

		if(respostas.length < 50)
			throw new Exception(String.format("Apenas %d de 50 respostas foram encontradas.", respostas.length));
		
		System.out.println(Arrays.toString(respostas));

		return respostas;
	}

	public void remover() {
		DAO<Gabarito> dao = new DAO<>(Gabarito.class);
		List<Gabarito> gabarito = dao.listaTodos() ;
		
		for (Gabarito gab : gabarito) {
			dao.remove(gab);
		}
		
		Mensagem.msgDelete("Gabarito removido!");
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

	public List<String> getGabaritos() {
		return gabaritos;
	}

	public void setGabaritos(List<String> gabaritos) {
		this.gabaritos = gabaritos;
	}
}