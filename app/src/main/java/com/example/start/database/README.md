# Database
This folder contains everything related to the database
Description of each file present in the folder is given below:

#### InputDatabase
All the Data Access Objects are declared and the entities are mentioned. Four entities: DataInput,Tasks, Notifications, Dashboard.

getDatabase(): Returns a database if already created else creates a new database with the name myheart_database.

**Further Additions** : Callback methods can be added to prepopulate the database or do some work onOpen() or onCreate().
Make addition to queries in the Data Access Objects(DAOs) as per the requirement.

#### Dashboard
This is the entity class for the dashboard table
**DAO** - DashboardDao

#### DataInput
This is the entity class for the user data table
**DAO** - InputDao

#### Notifications
This is the entity class for the notificaion table.
This table consists the notifications that are to be delivered to the user
**DAO** - NotificaionsDao

#### Tasks
This is the entity class for the tasks table.
This table consists of tasks, vital information collected from the user, followup from the notifications, and also the goals.
**DAO** - TasksDao

#### Converters
This is the TypeConverter class for the database . Converting Enums to integer and integer back to Enums as the room database cannot store Enums or Dates.

#### FileData
This is a subclass created that is used as a return type in the select query from the datainput table to get particular columns in the result.

#### Repository
This connects the rest of the application with the database. This exports the methods from all the DAOs to the application.
**Further Additions** - If ever online connections are made from the app it would be preffered to export those methods through the repository.

