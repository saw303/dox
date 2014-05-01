# DOX - Installation Guide
_A document management system brought you by Silvio Wangler_

## Server Installation
In order to get an optimal experience I recommend to run DOX on a Linux server such as Ubuntu or some other Linux distribution.

### Install third party software

#### Install MySQL 5.5
DOX uses Hibernate 4.x to interact with a relational database. I recommend to use MySQL 5.5 (or greater).

	sudo apt-get install mysql-server-5.5

#### Install latest stable Nginx
Install add-apt-repository Python script.
	
	sudo apt-get install python-software-properties
	sudo apt-get install software-properties-common

Then install the Nginx PPA repo.

	sudo add-apt-repository ppa:nginx/stable
	sudo apt-get update
	sudo apt-get install nginx

#### Install Java 7

	sudo add-apt-repository ppa:webupd8team/java
	sudo apt-get update
	sudo apt-get install oracle-java7-installer

#### Install Apache Tomcat 7

### Configure third party software

### Install DOX
#### Setup the MySQL database
To set up the database for DOX you can the following DDL script ddl_mysql5innodb_create.sql for MySQL. You find the script
in the domain.jar of the WAR. Open the WAR file by using 7zip (or some equal app) and navigate to

	/WEB-INF/lib/dox-domain-<version>.jar

To create the database 

	mysql -u root -p -e "CREATE DATABASE dox;"

The apply the DDL to the database `dox`

	mysql -u root -p dox < ddl_mysql5innodb_create.sql

##### Create a database user for the application

	GRANT ALL PRIVILEGES ON dox.* TO 'doxuser'@'localhost' IDENTIFIED BY 'doxpassword';

#### Configure Apache Tomcat 7

##### Install your JDBC driver in 

    <TOMCAT_HOME>/lib

##### Create JDNI datasource

DOX uses JDNI to lookup its connection to the database. The lookup name is called `jdbc/dox`. To configure the datasource edit the file `<TOMCAT_HOME>/conf/context.xml`.

    <Resource name="jdbc/dox" auth="Container"
              type="javax.sql.DataSource" driverClassName="com.mysql.jdbc.Driver"
              url="jdbc:mysql://localhost/dox"
              username="doxuser" password="doxpassword" maxActive="20" maxIdle="10"
              maxWait="-1"/>

#### Deploy DOX

Copy the WAR file into `<TOMCAT_HOME>/webapps`

    cp dox-<version>.war <TOMCAT_HOME>/webapps/ROOT.war

Start Tomcat 

    <TOMCAT_HOME>/bin/startup.sh