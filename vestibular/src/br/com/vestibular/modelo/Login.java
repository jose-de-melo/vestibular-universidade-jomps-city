package br.com.vestibular.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Login {
	

	@Id
	@Column(nullable = false)
	private String CPF;
	
	@NotEmpty(message="Senha não pode ser vazia")
	private String senha;
	
	@NotEmpty(message="Perfil não pode ser vazio")
	private String perfil;
	
	
	public Login() {
		
	}


	public String getCPF() {
		return CPF;
	}


	public void setCPF(String cPF) {
		CPF = cPF;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	@Override
	public String toString() {
		return "Login [CPF=" + CPF + ", senha=" + senha + ", perfil=" + perfil + "]";
	}


}
