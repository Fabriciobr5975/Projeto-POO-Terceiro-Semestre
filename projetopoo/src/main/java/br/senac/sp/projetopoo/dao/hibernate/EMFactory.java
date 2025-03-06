package br.senac.sp.projetopoo.dao.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {

	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("senac"); 
	private static EntityManager manager;
	
	public static EntityManager getEntityManager() {
		if(manager == null) {
			manager = FACTORY.createEntityManager();
		}
		return manager;
	}
}
