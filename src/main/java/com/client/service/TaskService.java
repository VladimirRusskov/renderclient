package com.client.service;

import com.client.service.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO addTask(Long applicationId);

    List<TaskDTO> getTasks();

    List<TaskDTO> getTasksHistory();
}
