CREATE TABLE SCORES
(    
   id INT not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),   
   player VARCHAR(50) references profile(username),     
   opponent VARCHAR(50),
   score VARCHAR(30)    
);
