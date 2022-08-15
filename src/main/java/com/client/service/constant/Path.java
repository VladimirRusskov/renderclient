package com.client.service.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Path {
    public static final String HOST_NAME = "http://localhost:8080/";
    public static final String AUTHORIZATION = HOST_NAME + "auth";
    public static final String REGISTRATION = HOST_NAME + "registration";
    public static final String APPLICATION_LIST = HOST_NAME + "application/list";
    public static final String TASK_LIST = HOST_NAME + "task/list";
    public static final String TASK_ADD = HOST_NAME + "task/add";
    public static final String TASK_HISTORY = HOST_NAME + "task/list/history";
}
