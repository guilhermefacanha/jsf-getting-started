package dao;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import entity.Task;

@ApplicationScoped
public class TaskDao extends BaseDao<Task> implements Serializable {

	private static final long serialVersionUID = 7913652102805046409L;

}
