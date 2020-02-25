# jsf-getting-started
JSF Getting Started Project

For this project we're going to use 
- Eclipse IDE
- JSF 2.3
- Primefaces
- Hibernate/JPA
- H2 Database
- MySQL Database

PHASE 1 - JSF Simple Hello World in 5 Steps

## Table of Contents
- [Creating the Project](#1---creating-the-project)
- [Configure Web Project](#2---configure-web-project)
- [Configure JSF Dependencies](#3---configure-jsf-dependencies)
- [Create our first Controller](#4---create-our-first-controller)
- [Create our first view or web page](#5---create-our-first-view-or-web-page)



## 1 - Creating the Project

- In eclipse, go: New -> Project -> (you cant use the filter to search for Maven -> Maven Project
- Check Create a simple project (skip archetype selection)
- Create a group id: eg: com.lab
- Create a artifact id: eg: jsf
- Change packaging to war
- Finish

Now, he have an empty Maven project. Maven will be resposible for managing the dependencies and libraries that we're going to use in our project.

## 2 - Configure Web Project

As a web project we need a file called, web.xml. Right click on the project and select `Java EE Tools -> Generate Deployment Descriptor Stub`. You should see a new file in `main/webap/WEB-INF/web.xml`. You can also use the shortcut `Ctrl+Shift+R` to search for a resource and go directly to it.

In order to say to our project to process jsf pages we need to modify our `web.xml` file to:

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	version="3.1">
	<display-name>jsf</display-name>
	
	<servlet>
		<servlet-name>Faces Servlet</servlet-name>
		<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>Faces Servlet</servlet-name>
		<url-pattern>*.xhtml</url-pattern>
	</servlet-mapping>

	<welcome-file-list>
		<welcome-file>index.xhtml</welcome-file>
	</welcome-file-list>
	
</web-app>
```

## 3 - Configure JSF Dependencies

Now we are going to tell Maven that:
- We are going to use JDK 8 EE libraries and classes.
- We are going to use JSF depedencies. 
- Open your file `pom.xml` (try to use the shortcut `Ctrl+Shift+R`) and paste the content as follow:

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.lab</groupId>
	<artifactId>jsf</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<dependencies>
    <!-- JSF 2.3 -->
		<dependency>
			<groupId>org.glassfish</groupId>
			<artifactId>jakarta.faces</artifactId>
			<version>2.3.14</version>
		</dependency>

		<!-- CDI Dependency Injection -->
		<dependency>
			<groupId>org.jboss.weld.servlet</groupId>
			<artifactId>weld-servlet-shaded</artifactId>
			<version>3.0.0.Final</version>
		</dependency>

	</dependencies>

	<build>
    <finalName>jsf</finalName>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.8.1</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
```

- Let's finish configuring our project:
- On src/main/webapp/WEB-INF: right click -> New -> beans.xml File. It will generate a file called beans.xml with the content:
```
<?xml version="1.0"?>
<beans bean-discovery-mode="annotated" version="2.0"
 xmlns="http://xmlns.jcp.org/xml/ns/javaee"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/beans_2_0.xsd"/>
```
- On src/main/webapp/WEB-INF: right click -> New -> Other... -> Faces Config. It will generate a file called faces-config.xml with the content:
```
<?xml version="1.0"?>
<faces-config version="2.2" xmlns="http://xmlns.jcp.org/xml/ns/javaee"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd">
 <application/>
</faces-config>

```


After that, right click your project and select `Maven -> Update Project` (or `Alt+F5`).

## 4 - Create our first Controller

- On `src/main/java` folder let's right click and select `New->Package` let's create a package called `controller`.
- Right click the new package and select `New -> Class`. Let's name it `TaskController`. Our class will look like this:
```
package controller;

public class TaskController {

}

```
- To say to our project that our new Class is a View Controller we are going to annotate it with 2 annotations, `@Named`, and `@ViewScoped`. 
- Another thing we are going to do is add a simple String return method in order to test the communication between our view and our controller.
- When you create a controller a good practice for JSF is to implement Serializable and generate a serial id for the class.
Our class should like this:
```
package controller;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class TaskController implements Serializable {
	
	private static final long serialVersionUID = 2702358477103653868L;

	public String getHello() {
		return "Hello from my First Controller Class";
	}

}
```

## 5 - Create our first view or web page

- In folder `src/main/webapp` right click and select `New->XHTML Page`. Let's call it index.xhtml.
- That's our first JSP page and is like a html page but with JSF tags:

```
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
<h:head>
    <title>Hello World JSF 2.3</title>
</h:head>
<h:body>

    <h:form>
        <h:outputLabel value="This is a static message."/>
        <br/>
        <h:outputText value="This is a controller message: #{taskController.hello}"/>
    </h:form>

</h:body>
</html>
```

## Test it
- To run our web application we need a web container. We are going to use Tomcat version 9.x.
- Download Tomcat from [Tomcat Download](https://tomcat.apache.org/download-90.cgi) and extract to a folder of your choice.
- In Eclipse you have a tab in the bottom of the IDE called Servers. Click the link `create a new server...` -> select Tomcat v9.0 Server -> select your folder from the previous step and Finish.
- Right click on the server -> Add Remove.. -> Select the jsf project -> Add -> Finish.
- Right click on the server -> Debug
- Open localhost:8080/jsf

## Create a Simple Task Crud

### Model

- Let's create a Model class called Task under `src/main/java/entity`. Our classe will represent the data about a task that would be stored in a database and retrieved by users:

```
package entity;

import java.util.Date;

public class Task {

	private long id;
	private String name;
	private String description;
	private Date dueDate = new Date();
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

### Update Controller

- Let's update our controller to manage tasks from retreiving (list) and creating new(save). Take a look at the code and the comments about the methods.

```
package controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import entity.Task;

@Named
@ViewScoped
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

### Update View
- Let's update a view to include a simple task form and a task list table
- Added a `f:metadata` with `f:viewAction` to call for a method on controller when the page loads
- Added a `h:panelGrid` with 2 columns to organize our form
- Added a `h:datatable` to retreive a list of tasks from our controller and show in the view

```
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:ui="http://xmlns.jcp.org/jsf/facelets" xmlns:f="http://xmlns.jcp.org/jsf/core">
<h:head>
	<title>Hello World JSF 2.3</title>
</h:head>
<h:body>
	<!-- Example on how to call for a method from controller when the page is loaded -->
	<f:metadata>
		<f:viewAction action="#{taskController.generateTempList()}" />
	</f:metadata>
	<h:messages showDetail="true" showSummary="true" />
	<h:form id="form">
		<fieldset>
			<legend>Tasks</legend>
			<h:panelGroup>
				<h:outputLabel value="This is a static message." />
				<br />
				<h:outputText value="This is a controller message: #{taskController.hello}" />
			</h:panelGroup>
			<h:panelGrid columns="2">
				<h:outputText value="Name:" />
				<h:inputText value="#{taskController.taskEntity.name}" required="true" requiredMessage="Required Field - Name"/>
				<h:outputText value="Description:" />
				<h:inputText value="#{taskController.taskEntity.description}" />
				<h:outputText value="Dua Date:" />
				<h:inputText value="#{taskController.taskEntity.dueDate}" required="true" requiredMessage="Required Field - Due Date" placeholder="MM/dd/yyyy HH:mm">
					<f:convertDateTime pattern="MM/dd/yyyy HH:mm" />
				</h:inputText>
				<h:outputText value="Assigned To:" />
				<h:inputText value="#{taskController.taskEntity.assignedTo}" required="true" requiredMessage="Required Field - Assigned To" />
				<h:commandButton value="Save" actionListener="#{taskController.save()}" immediate="false"/>
			</h:panelGrid>


			<!-- JSF data table component -->
			<h:panelGroup>
				<h:dataTable value="#{taskController.list}" var="task">
						<f:facet name="header">Task List</f:facet>
					<h:column>
						<f:facet name="header">Id</f:facet>
						<h:outputText value="#{task.id}" />
					</h:column>
					<h:column>
						<f:facet name="header">Name</f:facet>
						<h:outputText value="#{task.name}" />
					</h:column>
					<h:column>
						<f:facet name="header">Description</f:facet>
						<h:outputText value="#{task.description}" />
					</h:column>
					<h:column>
						<f:facet name="header">Due Date</f:facet>
						<h:outputText value="#{task.dueDate}">
							<f:convertDateTime pattern="MMM dd yyyy, HH:mm" />
						</h:outputText>
					</h:column>
					<h:column>
						<f:facet name="header">Assigned To</f:facet>
						<h:outputText value="#{task.assignedTo}" />
					</h:column>
				</h:dataTable>
			</h:panelGroup>
		</fieldset>
	</h:form>

</h:body>
</html>
```
