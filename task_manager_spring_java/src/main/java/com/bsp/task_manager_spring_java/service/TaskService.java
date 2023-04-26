package com.bsp.task_manager_spring_java.service;

import com.bsp.task_manager_spring_java.dto.CreateTaskDTO;
import com.bsp.task_manager_spring_java.dto.UpdateTaskDTO;
import com.bsp.task_manager_spring_java.entities.TaskEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class TaskService {
    private ArrayList<TaskEntity> tasks = new ArrayList<>();
    private int taskId = 1;
    private final SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public TaskEntity addTask(CreateTaskDTO body) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle(body.getTitle());
        task.setDescription(body.getDescription());
        task.setDeadline(deadlineFormatter.parse(body.getDeadline()));
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public ArrayList<TaskEntity> getTasks(){
        return tasks;
    }

    public TaskEntity getTaskById(int taskId) {
        for(TaskEntity task: tasks) {
            if (task.getId() == taskId) return task;
        }
        return null;
    }

    public TaskEntity updateTask(int taskId, UpdateTaskDTO body) throws ParseException{
        TaskEntity task = getTaskById(taskId);
        if (task == null) return null;
        if (body.getTitle() != null) task.setTitle(body.getTitle());
        if (body.getDescription() != null) task.setDescription(body.getDescription());
        if (body.isCompleted() != task.isCompleted()) task.setCompleted(body.isCompleted());
        if (body.getDeadline() != null) task.setDeadline(body.getDeadline());
        return task;
    }
}
