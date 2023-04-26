package com.bsp.task_manager_spring_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateTaskDTO {
    private String title;
    private String description;
    private Date deadline;
    public boolean completed;
}
