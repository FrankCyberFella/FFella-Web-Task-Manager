package com.frankfella.controller;

import com.frankfella.datasource.model.taskmanager.Task;
import com.frankfella.datasource.model.taskmanager.TaskDao;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.frankfella.generalpurposeutilities.LogHttpRequest.logHttpRequest;

@RestController
@CrossOrigin
public class TaskManagerAPIController {

    TaskDao theTasksDatasource;

    public TaskManagerAPIController(TaskDao theInjectedTasksDataSource) {
        theTasksDatasource = theInjectedTasksDataSource;
    }

    @RequestMapping(path="/tasks", method=RequestMethod.GET)
    public List<Task> returnAllTasks(HttpServletRequest theRequest) {
        logHttpRequest(theRequest);
        return theTasksDatasource.getAllTasks();
    }

    @RequestMapping(path="/tasks/{taskId}", method=RequestMethod.GET)
    public Task returnATaskFromId(HttpServletRequest theRequest
                                 ,@PathVariable Long taskId ) {
        logHttpRequest(theRequest);
        return theTasksDatasource.getTaskById(taskId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/tasks", method=RequestMethod.POST)
    public Task addANewTask(HttpServletRequest theRequest
                          , @Valid @RequestBody Task newTask ) {
        logHttpRequest(theRequest);
        return theTasksDatasource.addATask(newTask);
    }


    @RequestMapping(path="/tasks", method=RequestMethod.PUT)
    public Task updateATask(HttpServletRequest theRequest
            , @Valid @RequestBody Task newTask ) {
        logHttpRequest(theRequest);
        return theTasksDatasource.updateATask(newTask);
    }


}
