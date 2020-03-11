package controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dao.BaseDao;
import entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseController<T extends BaseEntity> implements Serializable {

	private static final long serialVersionUID = 4721461454168381459L;

	protected abstract BaseDao<T> getDao();

	protected abstract void newEntity();
	protected String sucessMsg = "Record added!";

	@Setter
	T ent;

	@Getter
	@Setter
	List<T> list;

	public void initController() {
		newEntity();
		loadList();
	}

	private void loadList() {
		list = getDao().getAll();
	}

	// method to add a new task to our list
	public void save() {
		this.getDao().save(ent);
		loadList();
		newEntity();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", sucessMsg));
	}

	public T getEnt() {
		if (ent == null)
			newEntity();

		return ent;
	}

}
