# Drippz-1

##README WIP

## Project Description
 A java based ORM for simplifying connecting to and from an SQL database without the need for SQL or connection management. 

## Technologies Used

* PostgreSQL - version 42.2.12  
* Java - version 8.0  
* HikariCP - version 3.4.5
* JUnit - version 4.12
* log4j - version 1.2.17
* Maven - version 1.8
* SonarCloud - for code monitoring

## Features

List of features ready and TODOs for future development  
* Simple to use API.  
* No need to directly use SQL.
* Simple Annotation based for ease of use. (Primary Key is required)
* Methods for performing operations with the input of an annotated class or Object of that class depending on the function
* Create: Annotated Class
* Get: Annotated Class, List<String> of field names (e.g. "first_name" to get just the results from the collumn first_name)[A single entry in this list with an * will return all fields], LinkedHashMap<String, String> containing in the first string the field name to search by, and in the 2nd String the value to compare to (e.g. ("id", 20) for WHERE id = 20) [an empty LinkedHashMap<String, String> will return all results in the table] (Returns a ResultsSet containing all results)
* Insert: Object of Annotated Class (Returns DB Generated Primary Key in ResultSet)
* Update: Object of Annotated Class, List<String> of field names (Uses ID of Object to determine which row to edit)(Must Specify the name of the column in the table here) (Returns number of rows changed in Result Set if successful)

To-do list: [`for future iterations`]
* Implement Delete Data Function   
* Bridge Hikari's Logger to Log4j to resolve startup red output
* Test Join Collumn Functionality
 



## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/Drippz_1.git
  cd *your-repo*_p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
		<dependency>

			<groupId>com.drippz</groupId>
			<artifactId>drippz</artifactId>
			<version>0.0.1-SNAPSHOT</version>

		</dependency>

```

Finally, inside your project structure you need a drippz.props file in located src/main/resources/
 ``` 
  DB_URL=path/to/database
  username=username/of/database
  password=password/of/database  
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.
   - #### @Entity(entityName = "table_name")  
      - Indicates that this class is associated with table 'table_name'  
   - #### @Column(name = "column_name)  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'   
   - #### @Id(ColumnName = "column_name") 
      - Indicates that the annotated field is the primary key for the table which will be generated by the DB and returned upon insertion.


  ### User API  
  
  - #### `Dripp something = new Dripp();`  
     - returns an object of the main class allowing access to the below methods
     - each object of the class has it's own cache that is used automatically when retrieving data from the DB
 
  - #### `something.get(annotatedClass.class, listOfTargetFields, linkedHashMapOfConstraints;`  
     - Returns a ResultSet corresponding to the GET method being run with the list of target fields and the constraints provided
     - All classes to be persisted must have the Entity Tag and a defined table name this name is extracted to target the GET
     - A list of strings containing the DB column names to be returned (A single entry of "*" will return all columns)
     - A LinkedHashMap containing the property name as a key and the value to search for as the value. Range searching is not implemented, only equals
 
 
     



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
