package http.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import manager.memory.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HistoryHandlerTest {
    // создаём экземпляр InMemoryTaskManager
    InMemoryTaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();
    String http = "http://localhost:8080";

    public HistoryHandlerTest() throws IOException {

    }

    @BeforeEach
    public void setUp() {
        manager.deleteAllTasks();
        manager.deleteAllSubTasks();
        manager.deleteAllEpics();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @DisplayName("Запрос упорядоченного списка задач : GET  /history")
    @Test
    public void testPrioritizedList() throws IOException, InterruptedException {
        Task task1, task2, task3;
        HttpResponse<String> response;
        URI url = URI.create(http + "/tasks");
        List<Task> tasksFromManager;

        // Добавили 1 задачу
        task1 = new Task("Тест 1", "01.01.2030 00:00", 5, "Тест добавления задачи",
                Status.NEW);
        // создаём HTTP-клиент и запрос
        response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task1)))
                        .build()
                , HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // положить задачу в историю просмотра
        manager.getTaskByID(manager.getTasksList().size());

        // Добавили 2 задачу
        task2 = new Task("Тест 2", "02.01.2030 00:00", 5, "Тест добавления задачи",
                Status.NEW);
        // создаём HTTP-клиент и запрос
        response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task2)))
                        .build()
                , HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());
        // положить задачу в историю просмотра
        manager.getTaskByID(manager.getTasksList().size());

        // Добавили 3 задачу
        task3 = new Task("Тест 2", "03.01.2030 00:00", 5, "Тест добавления задачи",
                Status.NEW);
        // создаём HTTP-клиент и запрос
        response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                        .uri(url)
                        .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task3)))
                        .build()
                , HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());
        // положить задачу в историю просмотра
        manager.getTaskByID(manager.getTasksList().size());

        // получение списка
        List<Task> historyTasksList = manager.getHistory();
        Integer historyTasksCount = historyTasksList.size();

        assertNotNull(historyTasksList, "Задачи не возвращаются");
        assertEquals(3, historyTasksCount, "Некорректное количество задач в списке. " +
                "Ожидалось 3, получили " + historyTasksCount);

        url = URI.create(http + "/history");
        response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                        .uri(url)
                        .GET()
                        .build()
                , HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // разбираем строку в формате JSON на элементы
        JsonArray getAsJsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        assertEquals(getAsJsonArray.size(), historyTasksCount, "количество задач в ответе за запрос: "
                + getAsJsonArray.size() + " не совпадает с количеством задач в списке: " + historyTasksCount );

    }

}