# jsf-getting-started
JSF Getting Started Project

For this project we're going to use 
- Eclipse IDE
- JSF 2.3
- Primefaces
- Hibernate/JPA
- H2 Database
- MySQL Database

## Table of Contents
- [Phase 1 - JSF Simple Hello World in 5 Steps](#phase-1---jsf-simple-hello-world-in-5-steps)
- [Phase 2 - JSF With Primefaces](#phase-2---jsf-with-primefaces)
- [Phase 3 - Hibernate/JPA](#phase-3---hibernate/jpa)

# PHASE 3 - Hibernate/JPA

## Phase 3 - Table of Contents
- [Configure Hibernate](#1---configuring-hibernate-and-h2-database)
- [Task Entity](#2---task-entity)
- [Database Access Object](#3---database-access-object)
- [Create a context load method](#4---context-load-method)
- [Create our first view or web page](#5---create-our-first-view-or-web-page)



## 1 - Configuring Hibernate and H2 Database

To configure hibernate and our first database we have to add the hibernate and h2 library to our pom file. 
To better organize our architecture, we create a pom property for our hibernate version:

```
<properties>
	<hibernate.version>5.4.12.Final</hibernate.version>
</properties>
```

And then we add our hibernate and h2 database dependency:

```

		<!-- Hibernate / JPA -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>5.4.12.Final</version>
		</dependency>


		<!-- H2 Memory Database -->
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>1.4.200</version>
		</dependency>
				
```



## 2 - Task Entity

- Let's update our Model Task under `src/main/java/entity`. Our class will now have two annotations `@Entity` and `@Id` to specify to our database context that the Task class represents a database table and which attribute will represent tha table id.

```
package entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {

	@Id
	private long id;
	private String name;
	private String description;
	private Date dueDate;
	private String assignedTo;

	//GETS SETS
	... continue the same from before
	

}


```

## 3 - Database Access Object

To communicate with our database we need code to connect and perform the transactions. We're going to create two classes. One to connect and create a context with the database called `dao.manager.DaoManager` and other to communicate with our Task table called `dao.TaskDao`.

- DaoManager

```

package dao.manager;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class DaoManager implements Serializable {
	private static final long serialVersionUID = 6982324627502940451L;

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");

	@Produces
	@ApplicationScoped
	@Default
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public void dispose(@Disposes EntityManager em) {
		em.close();
	}
}


```

- TaskDao

```

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


```

## 4 - Context Load Method

To improve our code and show an example on how to pre configure your web application this code was done. To work with hibernate this code is optional.
We created a new servlet class called `servlet.AppConfigServlet.java`. This class uses a init code and loadOnStartup property to initialize some code after our context is loaded into our web server.

- First, to use the servlet classes we added one already provided by our web server dependency to our pom:

```

<!-- Servlet Provided -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>javax.servlet-api</artifactId>
	<version>4.0.0</version>
	<scope>provided</scope>
</dependency>


```

- In this example we started our database context and added an example task. This will save as the time to boot our database context on our first request to the server.

```

package servlet;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import dao.TaskDao;
import entity.Task;

@WebServlet(loadOnStartup = 2, name = "initServlet", value = "/initServlet")
public class AppConfigServlet extends HttpServlet{

	private static final long serialVersionUID = 2151250503543054202L;
	
	@Inject
	private TaskDao dao;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("==== Initial Configuration for the Context ====");
		System.out.println("==== Database Context Initialization ====");
		
		createSampleTask();

		System.out.println("==== Database Context Initialized ====");
	}

	private void createSampleTask() {
		Task t = new Task();
		t.setName("New Task Example");
		t.setAssignedTo("User");
		t.setDueDate(new Date());
		t.setDescription("record generated by system");
		dao.save(t);
	}

}


```

