package com.bsp.task_manager_spring_java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTaskDTO {
    private String title;
    private String description;
    private String deadline;
}
