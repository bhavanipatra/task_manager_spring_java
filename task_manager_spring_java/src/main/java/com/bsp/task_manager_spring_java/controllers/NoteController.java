package com.bsp.task_manager_spring_java.controllers;

import com.bsp.task_manager_spring_java.dto.CreateNoteDTO;
import com.bsp.task_manager_spring_java.dto.CreateNoteResponseDTO;
import com.bsp.task_manager_spring_java.entities.NoteEntity;
import com.bsp.task_manager_spring_java.service.NoteService;
import com.bsp.task_manager_spring_java.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NoteController {
    private NoteService noteService;
    private TaskService taskService;

    public NoteController (NoteService noteService, TaskService taskService) {

        this.noteService = noteService;
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") Integer taskId) {
        var task = taskService.getTaskById(taskId);
        if (task == null) return ResponseEntity.notFound().build();
        var notes = noteService.getNotesForTask(taskId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDTO> addNote(
            @PathVariable("taskId") Integer taskId,
            @RequestBody CreateNoteDTO body)
    {
        var task = taskService.getTaskById(taskId);
        if (task == null) return ResponseEntity.notFound().build();
        var note = noteService.addNoteForTask(taskId, body.getTitle(), body.getBody());
        return ResponseEntity.ok(new CreateNoteResponseDTO(taskId, note));
    }

}
