package br.com.vestibular.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vestibular.modelo.Sala;

public class SalaDAO extends DAO<Sala>{
	
	public SalaDAO() {
		super(Sala.class);
	}
	
	public List<Sala> pesquisaPorCurso(Integer codcurso){
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("FROM Sala u WHERE u.curso.codcurso = :pCurso");
		query.setParameter("pCurso", codcurso);
		
		List<Sala> salas = new ArrayList<Sala>();
		for(Object objeto : query.getResultList()) {
			salas.add((Sala) objeto);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return salas;
	}


}
