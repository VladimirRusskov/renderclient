package com.client.service;

import com.client.converter.TaskToTaskDtoConverter;
import com.client.json.Task;
import com.client.service.dto.TaskDTO;
import com.client.service.dto.TaskDTOList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.client.service.constant.Path.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final TaskToTaskDtoConverter converter;

    @Override
    public TaskDTO addTask(Long applicationId) {
        Task task = new Task().setApplicationId(applicationId);
        HttpEntity<Task> request = new HttpEntity<>(task, headers);
        ResponseEntity<Task> taskResponseEntity = restTemplate.postForEntity(TASK_ADD, request, Task.class);
        return taskResponseEntity.hasBody() ? converter.convert(Objects.requireNonNull(taskResponseEntity.getBody())) : null;
    }

    @Override
    public List<TaskDTO> getTasks() {
        TaskDTOList list = restTemplate.getForObject(TASK_LIST, TaskDTOList.class);
        return list != null ? list.getTasks() : Collections.emptyList();
    }

    @Override
    public List<TaskDTO> getTasksHistory() {
        TaskDTOList list = restTemplate.getForObject(TASK_HISTORY, TaskDTOList.class);
        return list != null ? list.getTasks() : Collections.emptyList();
    }
}
