package http.handler;

import com.google.gson.*;
import http.adapter.DurationAdapter;
import http.adapter.LocalDateTimeAdapter;
import http.handler.HttpTaskServer;
import manager.memory.InMemoryTaskManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TasksHandlerTest {

    // создаём экземпляр InMemoryTaskManager
    InMemoryTaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();
    String http = "http://localhost:8080";

    public TasksHandlerTest() throws IOException {

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


    @DisplayName("Создание задачи: POST /tasks")
    @Test
    public void testAddTask() throws IOException, InterruptedException {
        // создаём задачу
        Task task;
        String taskJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;


        task = new Task("Тест 1", "01.01.2030 00:00", 5, "Тест добавления задачи",
                Status.NEW);
        // конвертируем её в JSON
        taskJson = gson.toJson(task);

        url = URI.create(http + "/tasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Task> tasksFromManager = manager.getTasksList();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Тест 1", tasksFromManager.get(0).getTaskName(), "Некорректное имя задачи");
    }

    @DisplayName("Создание пересекающейся по времения задачи:  POST /tasks")
    @Test
    public void testAddInterruptedTask() throws IOException, InterruptedException {
        Task task1, task2;
        String task1Json, task2Json;
        HttpRequest request;
        HttpResponse<String> response;

        URI url = URI.create(http + "/tasks");

        // создаём 1-ю задачу
        task1 = new Task("Тест 1", "01.01.2030 00:00", 5, "Тест пересечения задачи",
                Status.NEW);
        task1Json = gson.toJson(task1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        task2 = new Task("Тест пересекающейся", "01.01.2030 00:00", 5, "Тест добавления пересекаемой задачи",
                Status.NEW);
        task2Json = gson.toJson(task2);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(406, response.statusCode(), "Вместо кода 406 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что осталась одна задача
        List<Task> tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");

    }

    @DisplayName("Изменение параметров задачи: POST /tasks/{id}")
    @Test
    public void testUpdateTask() throws IOException, InterruptedException {

        Task task;
        String taskJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        // создаём задачу
        task = new Task("Тест 1", "01.01.2030 00:00", 5, "Тест изменения задачи",
                Status.NEW);
        // конвертируем её в JSON
        taskJson = gson.toJson(task);

        url = URI.create(http + "/tasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode());

        // проверяем, что задача создалась
        List<Task> tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");

        // Меняем статус задачи
        Task task1 = tasksFromManager.get(0);
        Integer taskID = task1.getID();
        task1.setStatus(Status.IN_PROGRESS);

        // конвертируем её в JSON
        taskJson = gson.toJson(task1);
        url = URI.create(http + "/tasks/" + String.valueOf(taskID));
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        assertEquals(Status.IN_PROGRESS, tasksFromManager.get(0).getStatus(), "Установился неверный статус " + tasksFromManager.get(0).getStatus().toString());
    }

    @DisplayName("Удаление задачи : DELETE  /tasks/{id}")
    @Test
    public void testDeleteTask() throws IOException, InterruptedException {

        Task task1, task2;
        String task1Json, task2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<Task> tasksFromManager;
        URI url = URI.create(http + "/tasks");

        // создаём 1-ю задачу
        task1 = new Task("Задача 1", "01.01.2030 00:00", 5, "Тест удаления задачи",
                Status.NEW);
        task1Json = gson.toJson(task1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        task2 = new Task("Задача для удаления", "01.02.2030 00:00", 5, "Тест удаления задачи",
                Status.NEW);
        task2Json = gson.toJson(task2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");


        // Удаляем задачу 2
        Integer taskForDeleteID = tasksFromManager.get(tasksFromManager.size() - 1).getID();
        url = URI.create(http + "/tasks/" + String.valueOf(taskForDeleteID));

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что осталась одна задача
        tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
    }

    //    @DisplayName("Запрос списка задач : GET  /tasks") //
    @Test
    public void testGetTasks() throws IOException, InterruptedException {

        Task task1, task2;
        String task1Json, task2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<Task> tasksFromManager;
        URI url = URI.create(http + "/tasks");

        // создаём 1-ю задачу
        task1 = new Task("Задача 1", "01.01.2030 00:00", 5, "Тест удаления задачи",
                Status.NEW);
        task1Json = gson.toJson(task1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        task2 = new Task("Задача для удаления", "01.02.2030 00:00", 5, "Тест удаления задачи",
                Status.NEW);
        task2Json = gson.toJson(task2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");

        // Запрос списка задач
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // разбираем строку в формате JSON на элементы
        JsonArray getAsJsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        assertEquals(getAsJsonArray.size(), tasksFromManager.size(), "количество задач в ответе за запрос: "
                + getAsJsonArray.size() + " не совпадает с количеством задач в списке: " + tasksFromManager.size() );

    }

    @DisplayName("Запрос задачи : GET  /tasks/{id}")
    @Test
    public void testGetTaskByID() throws IOException, InterruptedException {

        Task task;
        String taskJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        String taskNameForCheck = "Тест 1";

        // создаём задачу
        task = new Task(taskNameForCheck, "01.01.2030 00:00", 5, "Тест изменения задачи",
                Status.NEW);
        // конвертируем её в JSON
        taskJson = gson.toJson(task);

        url = URI.create(http + "/tasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что задача создалась
        List<Task> tasksFromManager = manager.getTasksList();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");

        // Запрашиваем задачу
        Task task1 = tasksFromManager.get(0);
        Integer taskID = task1.getID();
        url = URI.create(http + "/tasks" + String.valueOf(taskID));
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // сверяем задачи
        assertEquals(taskID, tasksFromManager.get(0).getID(), "Некорректный код задачи. Ожидалось "
                + taskID + ", получили " + tasksFromManager.get(0).getID());
        assertEquals(taskNameForCheck, tasksFromManager.get(0).getTaskName(), "Некорректное наименование задачи. Ожидалось "
                + taskNameForCheck + ", получили " + tasksFromManager.get(0).getTaskName());
    }
}
