package com.bsp.task_manager_spring_java.dto;

import com.bsp.task_manager_spring_java.entities.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateNoteResponseDTO {
    private Integer taskId;
    private NoteEntity note;
}
