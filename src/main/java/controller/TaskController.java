package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entity.Task;

@Named
@RequestScoped
public class TaskController implements Serializable {

	private static final long serialVersionUID = 2702358477103653868L;

	private Task taskEntity = new Task();
	private List<Task> list = new ArrayList<>();

	public String getHello() {
		generateTempList();
		return "Hello from my First Controller Class";
	}

	public void generateTempList() {
		for (int i = 0; i < 20; i++) {
			Task t = new Task();
			t.setId(i);
			t.setName("Task " + i);
			t.setDescription("Description of task " + i);
			t.setDueDate(Date.from(LocalDateTime.now().plusMinutes(i * 10).atZone(ZoneId.systemDefault()).toInstant()));
			t.setAssignedTo("User " + i);

			list.add(t);
		}
	}
	
	public void save() {
		this.list.add(0,taskEntity);
		this.taskEntity = new Task();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Task added!"));
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
