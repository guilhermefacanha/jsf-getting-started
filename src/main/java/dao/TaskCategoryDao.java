package dao;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import entity.TaskCategory;

@ApplicationScoped
public class TaskCategoryDao extends BaseDao<TaskCategory> implements Serializable {

	private static final long serialVersionUID = 8996433399822949886L;

}
