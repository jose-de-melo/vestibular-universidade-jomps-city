package br.com.vestibular.managebean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Login;


@ViewScoped
@ManagedBean
public class UsuarioMB {

	private Login usuario;

	public UsuarioMB() {
		usuario = new Login();
	}

	public void salvar(){
		DAO<Login> dao = new DAO<>(Login.class);
		
		// Insere o perfil com base no usuário que está logado ou não
		inserirPerfilUsuario();
		
		if(usuario.getCPF() != null) {
			try {
				dao.adiciona(usuario);
				Mensagem.msgInfo("Cadastrado com sucesso!");
			}catch (Exception e) {
				e.printStackTrace();
				Mensagem.msgErro("CPF já foi cadastrado!");
			}
		}

		usuario = new Login();
	}


	private void inserirPerfilUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		LoginMB loginBean = context.getApplication().evaluateExpressionGet(context, "#{loginMB}", LoginMB.class);
		
		if(loginBean.getLogin().getPerfil() == "root")
			usuario.setPerfil("sect");
		else
			usuario.setPerfil("cand");
		
	}

	public Login getUsuario() {
		return usuario;
	}

	public void setUsuario(Login usuario) {
		this.usuario = usuario;
	}
	
}
