CREATE TABLE PROFILE
(    
   username VARCHAR(50) not null primary key,
   password VARCHAR(10) not null,     
   status INTEGER not null,
   fname VARCHAR(50),
   lname VARCHAR(50),
   gender VARCHAR(10)
);