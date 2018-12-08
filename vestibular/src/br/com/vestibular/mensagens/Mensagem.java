package br.com.vestibular.mensagens;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

	
	public static void msgInfo(String mensagem) {
		FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));
	}
	
	public static void msgErro(String mensagem) {
		FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}
	
	public static void msgWarning(String mensagem) {
		FacesContext.getCurrentInstance()
			.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, ""));
	}
}
