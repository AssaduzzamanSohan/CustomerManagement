# Customer-Management
  # Check ui-screenshot.docx file to know how UI look like
  # If you want go through source code.
    1. Customer-Management\server for server source code
    2. Customer-Management\ui for UI source code

# Pre Requisite
  1. Apache Tomcat Server (To Run Project)
  2. MySql Database (To Store Data)

# How To Run Project
  1. Copy Customer-Management/Builted/Customer 
      and Customer-Management/Builted/Customer-server 
      to Tomcat/webapps/ folder

# Project Server Configuration
  # Tomcat/webapps/Customer-server/WEB-INF/classes/logback.properties file config
    1. log.root.dir path, where you want to save project log. 
  # Open Tomcat/webapps/Customer-server/WEB-INF/classes/application.properties file config
    1. spring.datasource.url=jdbc:mysql://DATEBASE-SERVER:PORT/DATEBASE-NAME
    2. spring.datasource.username=DATEBASE-LOGIN-NAME
    3. spring.datasource.password=DATEBASE-LOGIN-PASSWORD
    4. spring.jpa.hibernate.ddl-auto=create(Only for first time run then use "update")
    5. create.demo.customer = true (if you want some demo customer in DataBase)
         All customer's password will be admin and login-name admin2, admin3 ... admin20 
    
# Project UI Configuration
  # Open Tomcat/webapps/Customer/common/ServerConfig.js file config  
    1.  var url = 'ip:port/Customer-server';
         *** If you are running UI in same tomcat then keep, ip = localhost
         *** port = tomcat running port (By default it is 8080), change if you changed tomcat running port
    
# Project Backlog
  Due to we are not implementing User Management therefore 2 user will be created by system to login.
  1. Admin user: login-name = admin, password = admin
  2. General user: login-name = general, password = general

# Project view
  1. Start Apache Tomcat Server
  2. Open browser and go to http://localhost:8080/Customer link
