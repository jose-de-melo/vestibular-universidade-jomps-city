package br.com.vestibular.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vestibular.modelo.Candidato;

public class CandidatoDAO extends DAO<Candidato>{
	
	public CandidatoDAO() {
		super(Candidato.class);
	}

	public List<Candidato> pesquisaPorCurso(Integer codcurso){
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		Query query = em.createQuery("FROM Candidato u WHERE u.curso.codcurso = :pCurso");
		query.setParameter("pCurso", codcurso);
		
		List<Candidato> candidatos = new ArrayList<Candidato>();
		for(Object objetot : query.getResultList()) {
			candidatos.add((Candidato)objetot);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return candidatos;
	}

}
