package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager taskManager;
    private final Gson gson;


    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = HttpTaskServer.getGson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        final Integer taskIdFromPath = getTaskIdFromPath(exchange.getRequestURI().getPath());
        switch (exchange.getRequestMethod()) {
            case "GET" -> {
                // Нет кода задачи - весь список
                if (taskIdFromPath == null) {
                    final String response = gson.toJson(taskManager.getTasksList());
                    System.out.println("Весь список задач:");
                    sendSuccess200(exchange, response);
                    break;
                }
                // Есть код задачи
                Task task = taskManager.getTaskByID(taskIdFromPath);
                if (task != null) {
                    final String response = gson.toJson(task);
                    sendSuccess200(exchange, response);
                } else {
                    System.out.println("Задача с кодом " + taskIdFromPath + " не найдена");
                    sendTaskNoFound404(exchange);
                }
            }

            case "POST" -> {
                String json = readText(exchange);
                Task task = gson.fromJson(json, Task.class);
                Integer taskID = task.getID();
                // Есть код задачи - апдэйт
                if (taskID != null) {
                    taskManager.updateTask(task);
                    System.out.println("Обновили задачу + " + taskID);
                    sendSuccess200(exchange, "");
                } else { // Нет кода задачи - добавили
                    System.out.println("Нет кода задачи - добавили");
                    Integer addedID = taskManager.addNewTask(task);
                    final String response = gson.toJson(addedID);
                    if (addedID != null) {
                        System.out.println("Создали задачу с кодом " + addedID);
                        sendTaskCreated201(exchange, response);
                    } else {
                        System.out.println("Задача не добавлена");
                        sendTaskIntercepted406(exchange);
                    }
                }
            }

            case "DELETE" -> {
                if (taskIdFromPath != null) {
                    System.out.println("Удалили задачу с кодом " + taskIdFromPath);
                    taskManager.deleteTask(taskIdFromPath);
                }
                sendSuccess200(exchange, "");
            }

            default -> { System.out.println("Неизвестный метод"); }
        }
    }
}
