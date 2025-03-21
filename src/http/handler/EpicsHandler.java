package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {

    private final TaskManager taskManager;
    private final Gson gson;


    public EpicsHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = HttpTaskServer.getGson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        final Integer epicIdFromRequest = getTaskIdFromPath(exchange.getRequestURI().getPath());
        final boolean isGetSubTasksList = getSubTasksList(exchange.getRequestURI().getPath());

        switch (exchange.getRequestMethod()) {
            case "GET" -> {
                // Нет кода задачи - весь список
                if (epicIdFromRequest == null) {
                    final String response = gson.toJson(taskManager.getEpicsList());
                    System.out.println("Весь список подзадач:");
                    sendSuccess200(exchange, response);
                    break;
                }
                // Есть код задачи
                Epic epic = taskManager.getEpicByID(epicIdFromRequest);
                if (epic != null) {
                    final String response;
                    if (isGetSubTasksList) {
                        ArrayList<Integer> subTasksID = epic.getEpicSubtasks();
                        response = gson.toJson(taskManager.getSubTasksList().stream()
                                .filter(subtask -> subTasksID.contains(subtask.getID()))
                                .collect(Collectors.toList()));
                    } else {
                        response = gson.toJson(epic);
                    }
                    sendSuccess200(exchange, response);
                } else {
                    System.out.println("Подадача с кодом " + epicIdFromRequest + " не найдена");
                    sendTaskNoFound404(exchange);
                }
            }

            case "POST" -> {
                String json = readText(exchange);
                Epic epic = gson.fromJson(json, Epic.class);
                Integer epicID = epic.getID();
                if (epicID == null) { /* Нет кода задачи - добавили */
                    Integer addedID = taskManager.addNewEpic(epic);
                    System.out.println("Создали задачу с кодом " + addedID);
                    final String response = gson.toJson(addedID);
                    sendTaskCreated201(exchange, response);
                    break;
                }
                /* Есть код задачи - ничего не делаем */
                sendTaskCreated201(exchange, "");
            }

            case "DELETE" -> {
                if (epicIdFromRequest != null) {
                    System.out.println("Удалили задачу с кодом " + epicIdFromRequest);
                    taskManager.deleteEpic(epicIdFromRequest);
                }
                sendSuccess200(exchange, "");
            }

            default -> { System.out.println("Неизвестный метод"); }

        }
    }
}
