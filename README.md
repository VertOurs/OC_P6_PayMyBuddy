# Ne pas oublier avant la soutenance :
- masquer les identifiants via le Jar. et mettre ne place le dossier
- verifier le script data.sql 
- mettre en place les livrables
- finalisations du PPT
- ajouter les documents(readme par exemple) au github (présent en local mais pas en ligne)

#Pay My Buddy
PayMyBuddy is a local and secure web application that allows users to easily exchange money with each other and with their own bank accounts.

##Prerequisites :
To install PayMyBuddy, you will need:
- an IDE
- java 11
- Maven 3
- MySQL 8,0
- a web Browser

##Running App :
After installing all the required softwares you need to create a Database with tables and data.
For this, please run the sql commands present in the file `src/main/resources/Data.sql`.

For more security, the database credentials must be externalized.
a folder containing the project is accessible at `src/main/resources/BuddyRun`.

move the project where you want it and open terminal in this folder.
you can run the application with this command :

`` java -jar <Nom du jar> ``.

To access the application, open your browser, go to `http://localhost:8080`.

##Documentation :
###Class Diagram:
![Class_Diagram](Class_Diagram.png)
###Physical Data Model:
![Physical_Data_Model](Physical_Data_Model.png)





