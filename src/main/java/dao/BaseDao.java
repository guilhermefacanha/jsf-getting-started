package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.BaseEntity;

public abstract class BaseDao<T extends BaseEntity> implements Serializable {
	
	private static final long serialVersionUID = 7400219292515786319L;

	@Inject
	private EntityManager manager;
	
	protected Class<T> modelClass;
	
	public T save(T ent) {
		manager.getTransaction().begin();
		ent = manager.merge(ent);
		manager.getTransaction().commit();
		return ent;
	}
	
	public T get(long id) {
		return (T) manager.find(getEntityClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		return manager.createQuery("from "+getEntityClass().getSimpleName()).getResultList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Class<T> getEntityClass() {
		if (this.modelClass == null) {
			if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
				this.modelClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
			} else if (((Class) getClass().getGenericSuperclass())
					.getGenericSuperclass() instanceof ParameterizedType) {
				this.modelClass = (Class<T>) ((ParameterizedType) ((Class) getClass().getGenericSuperclass())
						.getGenericSuperclass()).getActualTypeArguments()[0];
			}
		}
		return this.modelClass;
	}
}
