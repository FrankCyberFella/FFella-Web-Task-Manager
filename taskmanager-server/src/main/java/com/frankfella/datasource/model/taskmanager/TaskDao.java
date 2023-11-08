package com.frankfella.datasource.model.taskmanager;


import java.util.List;

public interface TaskDao {
    /**
     * Retrieve all tasks from data source
     *
     * @param  - none
     * @return - list of tasks
     */
    public List<Task> getAllTasks();

    /**
     *
     * @param taskId
     * @return a Task object
     */
    public Task getTaskById(Long taskId);

    /**
     *
     * @param newTask
     * @return Task Object with primary key value filled in
     */
    public Task addATask(Task newTask);

    /**
     *
     * @param updatedTask
     * @return theUpdateTask from the datasource
     */
    public Task updateATask(Task updatedTask);
}

