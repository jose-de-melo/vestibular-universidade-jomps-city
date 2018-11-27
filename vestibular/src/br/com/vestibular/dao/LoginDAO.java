package br.com.vestibular.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vestibular.modelo.Login;

public class LoginDAO {
	
	public boolean existe(Login usuario) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createQuery("FROM Login u WHERE u.CPF = :pCPF and u.senha = :pSenha");
		query.setParameter("pCPF", usuario.getCPF());
		query.setParameter("pSenha", usuario.getSenha());
		
		boolean encontrado = !query.getResultList().isEmpty();
		em.getTransaction().commit();
		em.close();
		
		return encontrado;
	}

}
