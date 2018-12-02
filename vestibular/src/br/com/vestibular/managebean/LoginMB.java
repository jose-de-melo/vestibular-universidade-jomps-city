package br.com.vestibular.managebean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.vestibular.dao.DAO;
import br.com.vestibular.dao.LoginDAO;
import br.com.vestibular.mensagens.Mensagem;
import br.com.vestibular.modelo.Login;

@SessionScoped
@ManagedBean
public class LoginMB {
	
	private Login login;
	
	public LoginMB() {
		this.login = new Login();
	}

	public String loga() {
		LoginDAO loginDAO = new LoginDAO();
		
		if(loginDAO.existe(login)) {
			login = new DAO<>(Login.class).listaPorPK(login.getCPF());
			return "index?faces-redirect=true";
		}else {
			login = new Login();
			Mensagem.msgErro("Login ou senha incorreto!");
			return "login";
		}	
	}
	
	public boolean isLogado() {
		return login.getCPF() != null;
	}
	
	public String logout() {
		login = new Login();
		return "login?faces-redirect=true";
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}