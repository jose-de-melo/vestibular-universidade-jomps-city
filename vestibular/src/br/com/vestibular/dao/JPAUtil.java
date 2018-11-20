package br.com.vestibular.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("distribuidora");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
