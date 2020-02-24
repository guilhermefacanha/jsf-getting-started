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

# PHASE 2 - JSF With Primefaces

## Phase 2 - Table of Contents
- [Creating the Project](#1---configuring-primefaces)
- [Configure Web Project](#2---configure-web-project)
- [Configure JSF Dependencies](#3---configure-jsf-dependencies)
- [Create our first Controller](#4---create-our-first-controller)
- [Create our first view or web page](#5---create-our-first-view-or-web-page)



## 1 - Configuring Primefaces

Now we already know that our controller is "talking" with our page view. Let's start programming and improve the user experience.

- Open the file `pom.xml` and insert the dependency for primefaces.

- Add the primefaces repository URL after tag packaging and before tag dependencies:

```
...<packaging>war</packaging>

	<repositories>
		<repository>
			<id>primefaces</id>
			<name>Primefaces Repo</name>
			<url>https://repository.primefaces.org/</url>
		</repository>
	</repositories>

...<dependencies>
```


- Add primefaces dependencies inside tag dependencies

```
		<!-- Primefaces -->
		<dependency>
			<groupId>org.primefaces</groupId>
			<artifactId>primefaces</artifactId>
			<version>7.0</version>
		</dependency>

		<!-- Primefaces Single Theme -->
		<dependency>
			<groupId>org.primefaces.themes</groupId>
			<artifactId>glass-x</artifactId>
			<version>1.0.10</version>
		</dependency>

		<!-- Primefaces All Themes -->
		<dependency>
			<groupId>org.primefaces.themes</groupId>
			<artifactId>all-themes</artifactId>
			<version>1.0.10</version>
		</dependency>
```


## 2 - Programming Task Crud

- Let's create a Model called Task under `src/main/java/entity`. Our classe will represent the data about a task that would be stored in a database and retrieved by users:

```
package entity;

import java.util.Date;

public class Task {

	private long id;
	private String name;
	private String description;
	private Date dueDate;
	private String assignedTo;

	// GETS SETS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

}

```

- Let's update our controller to manage tasks from retreiving (list) and creating new(save). Take a look at the code and the comments about the methods.

```
package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entity.Task;

@Named
@RequestScoped
public class TaskController implements Serializable {

	private static final long serialVersionUID = 2702358477103653868L;

	//object to create a new task entity
	private Task taskEntity = new Task();
	
	//object to retreive a list of tasks from database
	private List<Task> list = new ArrayList<>();

	//first method to test controller communication with view
	public String getHello() {
		return "Hello from my First Controller Class";
	}

	//method to generate a temporary list to simulate a retreive from database
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
	
	//method to add a new task to our list
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

```

- Let's update our page view to show the task list and create a form to add a new task to the list:

```
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:ui="http://xmlns.jcp.org/jsf/facelets" xmlns:f="http://xmlns.jcp.org/jsf/core" xmlns:p="http://primefaces.org/ui">
<h:head>
	<title>Hello World JSF 2.3</title>
	<style>
.ui-state-error {
	border: 1px dotted #c50038 !important;
	color: #f03369;
}
</style>
</h:head>
<h:body>
	<!-- Example on how to call for a method from controller when the page is loaded -->
	<f:metadata>
		<f:viewAction action="#{taskController.generateTempList()}" />
	</f:metadata>

	<h:form>
		<!-- Primefaces component to show messages to the user -->
		<p:messages closable="true" showDetail="true" showSummary="true" />
		<p:panel>

			<p:panel>
				<h:outputLabel value="This is a static message." />
				<br />
				<h:outputText value="This is a controller message: #{taskController.hello}" />
			</p:panel>

			<p:panel>
				<p:panelGrid columns="2" style="border:none;">
					<h:outputText value="Name:" />
					<p:inputText value="#{taskController.taskEntity.name}" required="true" requiredMessage="Required Field - Name" />
					<h:outputText value="Description:" />
					<p:inputText value="#{taskController.taskEntity.description}" />
					<h:outputText value="Dua Date:" />
					<p:calendar value="#{taskController.taskEntity.dueDate}" pattern="MM/dd/yyyy HH:mm:ss" showTodayButton="true" disabledWeekends="true" showHour="true" showOn="button" required="true" requiredMessage="Required Field - Due Date" />
					<h:outputText value="Assigned To:" />
					<p:inputText value="#{taskController.taskEntity.assignedTo}" required="true" requiredMessage="Required Field - Assigned To" />
				</p:panelGrid>
				<p:commandButton actionListener="#{taskController.save()}" value="Save" update="@form"></p:commandButton>
			</p:panel>

			<!-- Primefaces data table component -->
			<p:dataTable value="#{taskController.list}" var="task" paginator="true" rows="10">
				<p:column headerText="Id" filterBy="#{task.id}" filterMatchMode="contains">
					<h:outputText value="#{task.id}" />
				</p:column>
				<p:column headerText="Name" filterBy="#{task.name}" filterMatchMode="contains">
					<h:outputText value="#{task.name}" />
				</p:column>
				<p:column headerText="Description" filterBy="#{task.description}" filterMatchMode="contains">
					<h:outputText value="#{task.description}" />
				</p:column>
				<p:column headerText="Due Date">
					<h:outputText value="#{task.dueDate}">
						<f:convertDateTime pattern="MMM dd yyyy, HH:mm" />
					</h:outputText>
				</p:column>
				<p:column headerText="Assigned To" filterBy="#{task.assignedTo}" filterMatchMode="contains">
					<h:outputText value="#{task.assignedTo}" />
				</p:column>
			</p:dataTable>
		</p:panel>
	</h:form>

</h:body>
</html>
```