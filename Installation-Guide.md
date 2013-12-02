# DOX - Installation Guide
_A document management system brought you by Silvio Wangler_

## Server Installation
In order to get an optimal experience I recommend to run DOX on a Linux server such as Ubuntu or some other Linux distribution.

### Install MySQL 5.5 
DOX uses Hibernate 4.x to interact with a relational database. I recommend to use MySQL 5.5 (or greater).

	sudo apt-get install mysql-server-5.5

### Install latest stable Nginx
Install add-apt-repository Python script.
	
	sudo apt-get install python-software-properties
	sudo apt-get install software-properties-common

Then install the Nginx PPA repo.

	sudo add-apt-repository ppa:nginx/stable
	sudo apt-get update
	sudo apt-get install nginx

### Install Java 7

	sudo add-apt-repository ppa:webupd8team/java
	sudo apt-get update
	sudo apt-get install oracle-java7-installer

### Install Tomcat 7