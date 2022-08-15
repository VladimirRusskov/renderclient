package com.client;

import com.client.config.MainConfiguration;
import com.client.error.RestTemplateResponseErrorHandler;
import com.client.service.ApplicationServiceImpl;
import com.client.service.TaskServiceImpl;
import com.client.service.UserServiceImpl;
import com.client.service.dto.ApplicationDTO;
import com.client.service.dto.TaskDTO;
import com.client.service.dto.UserDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        UserServiceImpl userService = context.getBean(UserServiceImpl.class);
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        System.out.println("Welcome Render Farm");
        String mainMenu = "Select next step number:" + "\n" + "1 Registration; 2 Authorization; 3 Exit.";
        boolean start = true;
        while (start) {
            String startChoice = request(mainMenu);
            startChoice = validInput(d -> d.matches("[1-2]"), mainMenu, startChoice);
            int startMenu = Integer.parseInt(startChoice);
            // Registration menu
            if (startMenu == 1) {
                String email = request("Enter email");
                String password = request("Enter password");
                UserDTO user = userService.registration(email, password);
                if (user == null) {
                    System.out.println("this email busy, choose another email");
                } else if (user.getId() == null) {
                    System.out.println("email not valid");
                } else {
                    System.out.println("User " + user.getEmail() + " created, log in.");
                }
                // Authorization menu
            } else if (startMenu == 2) {
                String email = request("Enter email");
                String password = request("Enter password");
                UserDTO user = userService.auth(email, password);
                if (user == null) {
                    System.out.println("user not found, check email and password");
                } else if (user.getId() == null) {
                    System.out.println("email not valid");
                } else {
                    // Personal menu
                    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(email, password));
                    HttpHeaders headers = context.getBean(HttpHeaders.class);
                    headers.setBasicAuth(email, password);
                    System.out.println("Welcome " + user.getEmail());
                    boolean personalMenuFlag = true;
                    String personalMenu = "Choose the next step. Choose the number corresponding to the action." + "\n" +
                            "1. Creating a new task; 2. Displaying a list of created tasks; 3. Displaying the history of task status changes. 4. Logout";
                    while (personalMenuFlag) {
                        String personalMenuChoice = request(personalMenu);
                        personalMenuChoice = validInput(d -> d.matches("[1-4]"), personalMenu, personalMenuChoice);
                        int personalChoice = Integer.parseInt(personalMenuChoice);
                        TaskServiceImpl taskService = context.getBean(TaskServiceImpl.class);
                        if (personalChoice == 1) {
                            ApplicationServiceImpl applicationService = context.getBean(ApplicationServiceImpl.class);
                            List<ApplicationDTO> applications = applicationService.getApplications();
                            if (applications.isEmpty()) {
                                System.out.println("No task apps found, contact developers");
                            } else {
                                System.out.println(applications);
                                String appRequest = request("Choose application id:");
                                TaskDTO task = taskService.addTask(Long.valueOf(appRequest));
                                if (task == null || task.getId() == null) {
                                    System.out.println("Task not created, try again.");
                                } else {
                                    System.out.println("Task created successfully. ID = " + task.getId() + ". Status = " + task.getStatus());
                                }
                            }
                        } else if (personalChoice == 2) {
                            List<TaskDTO> tasks = taskService.getTasks();
                            if (tasks.isEmpty()) {
                                System.out.println("List task is empty.");
                            } else {
                                System.out.println(tasks);
                            }
                        } else if (personalChoice == 3) {
                            List<TaskDTO> tasksHistory = taskService.getTasksHistory();
                            if (tasksHistory.isEmpty()) {
                                System.out.println("History is empty.");
                            } else {
                                System.out.println(tasksHistory);
                            }
                        } else {
                            System.out.println("See you.");
                            personalMenuFlag = false;
                            start = false;
                        }
                    }
                }
            }
        }
    }

    private static String validInput(Validator valid, String title, String input) {
        while (!valid.isValid(input)) {
            System.out.println("Exception input, try again.");
            input = request(title);
        }
        return input;
    }

    private static String request(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        return scanner.next();
    }

    private interface Validator {
        boolean isValid(String value);
    }
}