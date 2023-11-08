Begin transaction;

drop table if exists task;

create table task
(taskId      serial   primary key,
 dueDate     date     not null,
 description text     not null,
 iscomplete  boolean  default false
);



Insert into task
(dueDate, description, iscomplete)
Values('11/14/2023','Complete Task Manager App', false)
;	   
Insert into task
(dueDate, description, iscomplete)
Values('12/24/2023','Finish Holiday Shopping', false)
;	
Insert into task
(dueDate, description, iscomplete)
Values('10/28/2023','Attend Jax and Rustom Wedding', true)
;	
Insert into task
(dueDate, description, iscomplete)
Values('11/10/2023','Plan Thanksgiving Dinner', true)
;	
commit;
--rollback
