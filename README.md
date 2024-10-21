Web Blogger Application
A web-based blogging platform that allows users to create, manage, and interact with blogs. This project demonstrates full-stack development skills using Spring MVC, JSP, Hibernate, Java, and MySQL.

Features
User Authentication: Secure sign-up and login for both admin and users.
Blog Management: Users can create, update, delete, and view blogs.
Admin Panel: Admins have access to manage blog content.
Technologies Used
Spring MVC: For handling the web application’s flow.
Hibernate: For database management using Object Relational Mapping (ORM).
JSP: For dynamic web pages and user interface.
CSS: For designing the frontend.
Java: Core business logic.
MySQL: For the database to store user and blog data.
Installation
Clone the repository:

bash
Copy code
1.git clone https://github.com/your-username/web-blogger-app.git
Open the project in your favorite IDE.
2..Set up the MySQL Database:
CREATE DATABASE web_blogger_db;
3.Update the hibernate.cfg.xml file with your database configuration
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/web_blogger_db</property>
<property name="hibernate.connection.username">your-username</property>
<property name="hibernate.connection.password">your-password</property>
4.Build and run the project.

