package br.com.vestibular.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.caelum.stella.validation.CPFValidator;

@FacesValidator("valida_cpf")
public class ValidaCPF implements Validator{

	@Override
	public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String cpf = value.toString();
		
		try {
			System.out.println();
			new CPFValidator().assertValid(cpf);
		}catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF inválido.", ""));
		}
	}
}
