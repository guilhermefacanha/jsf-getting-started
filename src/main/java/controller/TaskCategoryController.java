package controller;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BaseDao;
import dao.TaskCategoryDao;
import entity.TaskCategory;

@Named
@ViewScoped
public class TaskCategoryController extends BaseController<TaskCategory>{

	private static final long serialVersionUID = 4884372877835103541L;
	
	@Inject
	TaskCategoryDao dao;

	@Override
	protected BaseDao<TaskCategory> getDao() {
		return dao;
	}

	@Override
	protected void newEntity() {
		this.ent = new TaskCategory();
	}

}
