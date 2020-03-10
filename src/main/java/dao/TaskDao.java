package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Task;

@ApplicationScoped
public class TaskDao implements Serializable {

	private static final long serialVersionUID = 7913652102805046409L;

	@Inject
	private EntityManager manager;

	public Task save(Task task) {
		manager.getTransaction().begin();
		task = manager.merge(task);
		manager.getTransaction().commit();
		return task;
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getAll(){
		return manager.createQuery("from Task").getResultList();
	}
}
