package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dao.TaskDao;
import entity.Task;

@Named
@RequestScoped
public class TaskController implements Serializable {

	private static final long serialVersionUID = 2702358477103653868L;
	
	//object to communicate with database
	@Inject
	private TaskDao dao;

	//object to create a new task entity
	private Task taskEntity = new Task();
	
	//object to retreive a list of tasks from database
	private List<Task> list = new ArrayList<>();

	//first method to test controller communication with view
	public String getHello() {
		return "Hello from my First Controller Class";
	}

	//method to add a new task to our list
	public void save() {
		this.dao.save(taskEntity);
		loadList();
		this.taskEntity = new Task();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Task added!"));
	}
	
	public void loadList() {
		this.list = dao.getAll();
	}

	// Gets Sets
	public List<Task> getList() {
		return list;
	}

	public void setList(List<Task> list) {
		this.list = list;
	}

	public Task getTaskEntity() {
		return taskEntity;
	}

	public void setTaskEntity(Task taskEntity) {
		this.taskEntity = taskEntity;
	}

}
