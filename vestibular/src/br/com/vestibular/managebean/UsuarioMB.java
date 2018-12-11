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
	private boolean alterar;

	public UsuarioMB() {
		alterar = false;
		Login login = obterUsuarioLogado();
		usuario = (login == null)? new Login() : login;
	}

	private Login obterUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(context.getViewRoot().getViewId().equals("/altera_senha.xhtml")) {
			alterar = true;
			LoginMB loginBean = context.getApplication().evaluateExpressionGet(context, "#{loginMB}", LoginMB.class);
			return (loginBean.isLogado()) ? loginBean.getLogin() : null;
		}
		
		return null;
	}

	public String salvar(){
		DAO<Login> dao = new DAO<>(Login.class);
		
		if(usuario.getCPF() != null) {
			Login login = dao.listaPorPK(usuario.getCPF());
			
			if(login == null) {
				// Insere o perfil com base no usuário que está logado ou não
				inserirPerfilUsuario();
				dao.adiciona(usuario);
				
				Mensagem.msgInfo("Usuário cadastrado com sucesso!");
			}else if(alterar) {
				dao.altera(usuario);
				Mensagem.msgInfo("Senha alterada com sucesso!");
				return "index?faces-redirect=true";
			}else
				Mensagem.msgErro("O CPF já existe!");
				
		}

		usuario = new Login();
		return "";
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
