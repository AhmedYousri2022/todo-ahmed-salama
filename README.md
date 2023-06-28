# Simple-Todo
 
### Service-description and the assumption
- the service is responsible for creating a simple todo list with the ability to:
1. Adding new item
2. update the description of an item
3. mark an item to done or not done
4. show all (not done) item
5. show all item
5. show details for a specific item
6. automatically change the items that due date has past

### Assumptions and limitations
1. once the item's status has changed to Past_due it can not be changed
2. items that marked as done the done date is automatically inserted to the database and initial due date will stay as user definded when he/she created the itme
3. you can change any item from not done to done
4. if the user want to change item's status from done  to not done the user has to define a new due date and done date will be reset to null
5. there is a Scheduler running every day at midnight in UTC time zone to check all (not done) todos and change their status from (NOT_Done) to (PAST_DUE).
6. the length for description field on for an item should be between 2 to 200 character 
### technical assumption
1. allowing user to mark done and not done item has been developed in one API with optional parameter dueDate.
2. get not done item API has a optional boolean parameter (withAllItem) to allow the user to get all items
3. item is not allowed to be created if the dueDate is in the past
4. once the item is created createdAt field is add to current time in UTC 
5. all date is inserted in DB in UTC 
6. all date of an item is showing to the user in zonedDateTime with Berlin TimeZone following that pattern (dd-MM-yyyy HH:mm:ss)


### tech stack used (runtime environment, frameworks, key libraries)
the service is developed with:
1. Java 11
2. springboot
3. tomcat server
4. h2 db
5. the service is using mapStruct to change dto to model and from model to dto
6. used springfox (not done fully done)
7. the service run on docker

### how to: build the service run automatic tests run the service locally
- the service could be run on docker 
- you can run the following command  'docker-compose -f docker-compose.yml up --build -d'
- and you can send a request on port 8080 for example to send post 
``` 
curl --location 'http://localhost:8080/todos' \
  --header 'Content-Type: application/json' \
  --data '{
  "description": "4",
  "status" :"NOT_DONE",
  "dueDate" : "27-06-2023 15:53:00"
  }'
```

- I have attached as well postman collection you can you it for testing manually
- To run the test from the command line run 'mvn test'

