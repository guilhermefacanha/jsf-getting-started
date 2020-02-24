package controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class TaskController implements Serializable {
	
	private static final long serialVersionUID = 2702358477103653868L;

	public String getHello() {
		return "Hello from my First Controller Class";
	}

}
