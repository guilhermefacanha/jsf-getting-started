package dao.manager;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class DaoManager implements Serializable {
	private static final long serialVersionUID = 6982324627502940451L;

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");

	@Produces
	@ApplicationScoped
	@Default
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public void dispose(@Disposes EntityManager em) {
		em.close();
	}
}
