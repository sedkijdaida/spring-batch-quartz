# User Guide :

The goal of this project is how to integrate Spring-batch and quartz either you using xml config (Spring 3) or java configuration (using annotaion :Spring4)

Scenario of this project : 

reading data from data base (Mysql DataBase).

processing data in ItemProcess.

saving the output in CVS/XML/TXT file.

All this steps is repeated every 10 seconde.

***************How to run this project ?************************************

- After download this project and importing it into eclipse. 

- You should create your database and configure them in the application.properties.

- Next Step you should execute the sql statement bellow :

- create table EXAM_RESULT (
   student_name VARCHAR(30) NOT NULL,
   dob DATE NOT NULL,
   percentage  double NOT NULL
);
 
- insert into exam_result(student_name,dob,percentage) 
value('Brian Burlet','1985-02-01',76),('Rita Paul','1993-02-01',92),('Han Yenn','1965-02-01',83),('Peter Pan','1987-02-03',62);





