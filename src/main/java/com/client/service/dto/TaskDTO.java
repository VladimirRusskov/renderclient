package com.client.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TaskDTO {
    private Long id;
    private String status;
}
