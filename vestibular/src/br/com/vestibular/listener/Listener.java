package br.com.vestibular.listener;

import java.util.Arrays;
import java.util.HashSet;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.vestibular.managebean.LoginMB;

public class Listener  implements PhaseListener{
	private static final long serialVersionUID = 1L;
	
	private HashSet<String> paginasSemLogin = new HashSet<>(Arrays.asList( "/login.xhtml", "/usuario.xhtml"));

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		
		if(paginasSemLogin.contains(context.getViewRoot().getViewId()))
			return;
		
		LoginMB loginBean = context.getApplication().evaluateExpressionGet(context, "#{loginMB}", LoginMB.class);
		
		if(!loginBean.isLogado()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "login?faces-redirect=true");
			context.renderResponse();
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	

}
