package com.frankfella.datasource.model.taskmanager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTaskDao implements TaskDao {

    private JdbcTemplate theTaskManagerDatabase;

    public JdbcTaskDao(JdbcTemplate theInjectedJdbcTemplate) {
        this.theTaskManagerDatabase = theInjectedJdbcTemplate;
    }

    /**
     * Retrieve all tasks from data source
     *
     * @param - none
     * @return - list of tasks
     */
    @Override
    public List<Task> getAllTasks() {

        List<Task> theTasks = new ArrayList();

        String allTasksSql = "Select taskId, description, dueDate, isComplete from task";

        SqlRowSet theSqlResult = theTaskManagerDatabase.queryForRowSet(allTasksSql);

        while (theSqlResult.next()) {
            theTasks.add(MapRowToTaskObject(theSqlResult));
        }

        return theTasks;
    }

    /**
     * @param taskId
     * @return a Task object
     */
    @Override
    public Task getTaskById(Long taskId) {

        Task returnedTask = null;

        String aTasksSql =  " Select taskId, description, dueDate, isComplete "
                          + "   from task"
                          +  " where taskId = ?";

        SqlRowSet theSqlResult = theTaskManagerDatabase.queryForRowSet(aTasksSql, taskId);

        if(theSqlResult.next()) {
            returnedTask = MapRowToTaskObject(theSqlResult);
        }
        return returnedTask;
    }

    /**
     * @param newTask
     * @return Task Object with primary key value filled in
     */
    @Override
    public Task addATask(Task newTask) {

        String addATaskSQL = " insert into task"
                           + " (dueDate, description, isComplete)"
                           + " values(?, ?, ?)"
                           + " returning taskId";

        Long newTaskId = theTaskManagerDatabase.queryForObject(addATaskSQL
                                                              ,Long.class
                                                              ,newTask.getDueDate()
                                                              ,newTask.getDescription()
                                                              ,newTask.isComplete());


        return this.getTaskById(newTaskId);
    }

    /**
     * @param updatedTask
     * @return theUpdateTask from the datasource
     */
    @Override
    public Task updateATask(Task updatedTask) {
        String updateATaskSQL = " update task"
                             +         " set dueDate     = ?"
                             + "           , description = ?"
                             + "           , isComplete  = ?"
                             + "   where taskId = ?";

        theTaskManagerDatabase.update(updateATaskSQL
                                     ,updatedTask.getDueDate()
                                     ,updatedTask.getDescription()
                                     ,updatedTask.isComplete()
                                     ,updatedTask.getTaskId());

        Task aTask = this.getTaskById(updatedTask.getTaskId());

        return this.getTaskById(updatedTask.getTaskId());
    }


    public Task MapRowToTaskObject(SqlRowSet tableRow) {
        Task aTask = new Task();

        aTask.setTaskId(tableRow.getLong("taskId"));
        aTask.setDescription(tableRow.getString("description"));
        aTask.setComplete(tableRow.getBoolean("isComplete"));
        aTask.setDueDate(tableRow.getDate("dueDate").toLocalDate());

        return aTask;
    }
}
