package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.SubTask;

import java.io.IOException;

public class SubTasksHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager taskManager;
    private final Gson gson;


    public SubTasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = HttpTaskServer.getGson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        final Integer subTaskIdFromRequest = getTaskIdFromPath(exchange.getRequestURI().getPath());

        switch (exchange.getRequestMethod()) {
            case "GET" -> {
                // Нет кода задачи - весь список
                if (subTaskIdFromRequest == null) {
                    final String response = gson.toJson(taskManager.getSubTasksList());
                    System.out.println("Весь список подзадач:");
                    sendSuccess200(exchange, response);
                    break;
                }
                // Есть код задачи
                SubTask subTask = taskManager.getSubTaskByID(subTaskIdFromRequest);
                if (subTask != null) {
                    final String response = gson.toJson(subTask);
                    sendSuccess200(exchange, response);
                } else {
                    System.out.println("Подадача с кодом " + subTaskIdFromRequest + " не найдена");
                    sendTaskNoFound404(exchange);
                }
            }

            case "POST" -> {
                String json = readText(exchange);
                SubTask subTask = gson.fromJson(json, SubTask.class);
                Integer subTaskID = subTask.getID();
                // Есть код задачи - апдэйт
                if (subTaskID != null) {
                    taskManager.updateTask(subTask);
                    System.out.println("Обновили задачу + " + subTaskID);
                    sendSuccess200(exchange, "");
                } else { // Нет кода задачи - добавили
                    System.out.println("Нет кода задачи - добавили");
                    Integer addedID = taskManager.addNewSubTask(subTask);
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
                if (subTaskIdFromRequest != null) {
                    System.out.println("Удалили задачу с кодом " + subTaskIdFromRequest);
                    taskManager.deleteSubTask(subTaskIdFromRequest);
                }
                sendSuccess200(exchange, "");
            }

            default -> { System.out.println("Неизвестный метод"); }
        }
    }
}

