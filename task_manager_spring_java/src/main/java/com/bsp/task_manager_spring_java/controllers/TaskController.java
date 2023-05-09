package com.bsp.task_manager_spring_java.controllers;

import com.bsp.task_manager_spring_java.dto.CreateTaskDTO;
import com.bsp.task_manager_spring_java.dto.ErrorResponseDTO;
import com.bsp.task_manager_spring_java.dto.TaskResponseDTO;
import com.bsp.task_manager_spring_java.dto.UpdateTaskDTO;
import com.bsp.task_manager_spring_java.entities.TaskEntity;
import com.bsp.task_manager_spring_java.service.NoteService;
import com.bsp.task_manager_spring_java.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final NoteService noteService;
    private ModelMapper modelMapper = new ModelMapper();
    public TaskController(TaskService taskService, NoteService noteService) {
        this.taskService = taskService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        var tasks = taskService.getTasks();
        if (tasks.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") Integer taskId) {
        var task = taskService.getTaskById(taskId);
        var notes = noteService.getNotesForTask(taskId);
        if (task == null) return ResponseEntity.notFound().build();
        var taskResponse = modelMapper.map(task, TaskResponseDTO.class);
        taskResponse.setNotes(notes);
        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException{
        var task = taskService.addTask(body);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer taskId, @RequestBody UpdateTaskDTO body) throws ParseException {
        TaskEntity updatedTask = taskService.updateTask(taskId, body);
        if (updatedTask == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskEntity> deleteTask(@PathVariable("id") Integer taskId) throws ParseException {
        TaskEntity deletedTask = taskService.deleteTask(taskId);
        return ResponseEntity.ok(deletedTask);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        if (e instanceof ParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }
}
