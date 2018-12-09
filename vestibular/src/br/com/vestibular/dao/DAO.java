package br.com.vestibular.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> {
	
	private Class<T> classe;
	
	public DAO(Class<T> classe){
		this.classe = classe;
	}
	
	public void adiciona(T objeto){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(objeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void altera(T objeto){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(objeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public void adiciona(T... objetos){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		
		for(Object objeto : objetos)
			manager.persist(objeto);
			
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public void altera(T... objetos){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		
		for(Object objeto : objetos)
			manager.merge(objeto);
		
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void remove(T objeto){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.remove(manager.merge(objeto));
		manager.getTransaction().commit();
		manager.close();
	}
	
	public T listaPorPK(Object pk){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		return manager.find(classe, pk);
	}
	
	public List<T> listaTodos(){ 
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
		return manager.createQuery(query).getResultList();
	}
}

