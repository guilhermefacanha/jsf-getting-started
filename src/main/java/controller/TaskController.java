package controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BaseDao;
import dao.TaskDao;
import entity.Task;

@Named
@ViewScoped
public class TaskController extends BaseController<Task> implements Serializable {

	private static final long serialVersionUID = 2702358477103653868L;
	
	//object to communicate with database
	@Inject
	private TaskDao dao;

	//first method to test controller communication with view
	public String getHello() {
		return "Hello from my First Controller Class";
	}

	@Override
	protected BaseDao<Task> getDao() {
		return dao;
	}

	@Override
	protected void newEntity() {
		ent = new Task();
		
	}	


}
