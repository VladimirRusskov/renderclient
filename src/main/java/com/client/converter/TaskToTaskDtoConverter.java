package com.client.converter;

import com.client.json.Task;
import com.client.service.dto.TaskDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDTO> {
    @Override
    public TaskDTO convert(Task task) {
        return new TaskDTO().setId(task.getId()).setStatus(task.getStatus());
    }
}
