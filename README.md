This project was made using Spring Boot, Spring data JPA and PostgreSQL.<br>
The purpose of this project is to create an Rest API that allows to perform CRUD operations in a database that
consists of questions with different cattegories and answers. <br>
This application allows the creation of a quiz taking a specified amount of random questions of the chosen category and saving it in the database.
You can also submit answers in the request body specifying the id and answer of the questions and you will get a response with the amount of correct questions. <br>
This project implements custom exceptions and custom http responses. <br>
All testing was made using Postman.<br><br>

Adding question to the database:<br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/addQuestion.png?raw=true) <br>
Fetching all existing questions:<br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/getAll.png?raw=true) <br>
Updating question:<br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/updateQuestion.png?raw=true) <br>
Deleting question: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/deleteQuestion.png?raw=true) <br>
Deleting question with an invalid ID: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/deleteException.png?raw=true) <br>
Fetching questions by category: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/getCategory.png?raw=true) <br>
Creating a quiz: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/createQuiz.png?raw=true) <br>
Get quiz: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/getQuiz.png?raw=true) <br>
Get quiz with invalid ID: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/noQuizFound.png?raw=true) <br>
Answering quiz: <br>
![alt text](https://github.com/Franruval/Quiz-Manager/blob/main/src/main/resources/static/img/answerQuiz.png?raw=true) <br>
