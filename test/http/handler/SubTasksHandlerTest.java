package http.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import manager.memory.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubTasksHandlerTest {

    // создаём экземпляр InMemoryTaskManager
    InMemoryTaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();



    String http = "http://localhost:8080";


    Integer epicID;
    Epic epic;
    String epicJson;
    URI epicUrl;
    HttpRequest epicRequest;
    HttpResponse<String> epicResponse;

    public SubTasksHandlerTest() throws IOException {

    }

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        manager.deleteAllTasks();
        manager.deleteAllSubTasks();
        manager.deleteAllEpics();
        taskServer.start();

// Создаем эпик для привязки сабтасков
        epic = new Epic("Эпик для связки", "Эпик для связки - описание");
        epicJson = gson.toJson(epic);
        epicUrl = URI.create(http + "/epics");
        // создаём HTTP-клиент и запрос
        epicRequest = HttpRequest.newBuilder().uri(epicUrl).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
        epicResponse = HttpClient.newHttpClient().send(epicRequest, HttpResponse.BodyHandlers.ofString());
        List<Epic> epicsFromManager = manager.getEpicsList();
        epicID = epicsFromManager.get(0).getID();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }


    @DisplayName("Создание подзадачи: POST /subtasks")
    @Test
    public void testAddSubTask() throws IOException, InterruptedException {
        // создаём задачу
        SubTask subTask;
        String taskJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        subTask = new SubTask("Тест 1", "01.01.2030 00:00", 5, "Тест добавления подзадачи",
                epicID, Status.NEW);
        // конвертируем её в JSON
        taskJson = gson.toJson(subTask);

        url = URI.create(http + "/subtasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<SubTask> subTasksFromManager = manager.getSubTasksList();

        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество подзадач");
        assertEquals("Тест 1", subTasksFromManager.get(0).getTaskName(), "Некорректное имя подзадачи");
    }

    @DisplayName("Создание пересекающейся по времения подзадачи:  POST /subtasks")
    @Test
    public void testAddInterruptedSubTask() throws IOException, InterruptedException {

        SubTask subTask1, subTask2;
        String subTask1Json, subTask2Json;
        HttpRequest request;
        HttpResponse<String> response;

        URI url = URI.create(http + "/subtasks");

        // создаём 1-ю задачу
        subTask1 = new SubTask("Тест 1", "01.01.2030 00:00", 5, "Тест пересечения задачи",
                epicID, Status.NEW);
        subTask1Json = gson.toJson(subTask1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTask1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        subTask2 = new SubTask("Тест пересекающейся", "01.01.2030 00:00", 5, "Тест добавления пересекаемой задачи",
                epicID, Status.NEW);
        subTask2Json = gson.toJson(subTask2);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTask2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(406, response.statusCode(), "Вместо кода 406 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что осталась одна задача
        List<SubTask> subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество подзадач");
    }

    @DisplayName("Изменение параметров подзадачи: POST /subtasks/{id}")
    @Test
    public void testUpdateSubTask() throws IOException, InterruptedException {

        SubTask subTask, subTask1;
        String subTaskJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        // создаём задачу
        subTask = new SubTask("Тест 1", "01.01.2030 00:00", 5, "Тест изменения задачи",
                1, Status.NEW);
        // конвертируем её в JSON
        subTaskJson = gson.toJson(subTask);

        url = URI.create(http + "/subtasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что задача создалась
        List<SubTask> subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество задач");

        // Меняем статус задачи
        subTask1 = subTasksFromManager.get(0);
        Integer subTaskID = subTask1.getID();
        subTask1.setStatus(Status.IN_PROGRESS);

        // конвертируем её в JSON
        subTaskJson = gson.toJson(subTask1);
        url = URI.create(http + "/subtasks/" + String.valueOf(subTaskID));
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество задач");
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        assertEquals(Status.IN_PROGRESS, subTasksFromManager.get(0).getStatus(), "Установился неверный статус " + subTasksFromManager.get(0).getStatus().toString());
    }

    @DisplayName("Удаление подзадачи : DELETE  /subtasks/{id}")
    @Test
    public void testDeleteSubTask() throws IOException, InterruptedException {
        SubTask subTask2, subTask1;
        String subTask1Json, subTask2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<SubTask> subTasksFromManager;
        URI url = URI.create(http + "/subtasks");

        // создаём 1-ю задачу
        subTask1 = new SubTask("Задача 1", "01.01.2030 00:00", 5, "Тест удаления задачи",
                1, Status.NEW);
        subTask1Json = gson.toJson(subTask1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTask1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        subTask2 = new SubTask("Задача для удаления", "01.02.2030 00:00", 5, "Тест удаления задачи",
                1, Status.NEW);
        subTask2Json = gson.toJson(subTask2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTask2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(2, subTasksFromManager.size(), "Некорректное количество задач");


        // Удаляем задачу 2
        Integer taskForDeleteID = subTasksFromManager.get(subTasksFromManager.size() - 1).getID();
        url = URI.create(http + "/subtasks/" + String.valueOf(taskForDeleteID));

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что осталась одна задача
        subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество задач");

    }



    @DisplayName("Запрос списка подзадач : GET  /subtasks")
    @Test
    public void testGetSubTask() throws IOException, InterruptedException {
        SubTask subTask1, subTask2;
        String task1Json, task2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<SubTask> subTasksFromManager;
        URI url = URI.create(http + "/subtasks");

        // создаём 1-ю задачу
        subTask1 = new SubTask("Задача 1", "01.01.2030 00:00", 5, "Тест удаления задачи"
                , 1, Status.NEW);
        task1Json = gson.toJson(subTask1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        subTask2 = new SubTask("Задача для удаления", "01.02.2030 00:00", 5, "Тест удаления задачи"
                , 1, Status.NEW);
        task2Json = gson.toJson(subTask2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(task2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(2, subTasksFromManager.size(), "Некорректное количество задач");

        // Запрос списка задач
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // разбираем строку в формате JSON на элементы
        JsonArray getAsJsonArray = JsonParser.parseString(response.body()).getAsJsonArray();

        assertEquals(getAsJsonArray.size(), subTasksFromManager.size(), "количество задач в ответе за запрос: "
                + getAsJsonArray.size() + " не совпадает с количеством задач в списке: " + subTasksFromManager.size() );

    }

    @DisplayName("Запрос подзадачи : GET  /subtasks/{id}")
    @Test
    public void testGetSubTaskByID() throws IOException, InterruptedException {
        SubTask subTask;
        String subTaskJson;
        List<SubTask> subTasksFromManager;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        String taskNameForCheck = "Тест 1";

        // создаём задачу
        subTask = new SubTask(taskNameForCheck, "01.01.2030 00:00", 5, "Тест изменения задачи",
                1, Status.NEW);
        // конвертируем её в JSON
        subTaskJson = gson.toJson(subTask);

        url = URI.create(http + "/subtasks");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что задача создалась
        subTasksFromManager = manager.getSubTasksList();
        assertNotNull(subTasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subTasksFromManager.size(), "Некорректное количество задач");

        // Запрашиваем задачу
        SubTask subTask1 = subTasksFromManager.get(0);
        Integer subTaskID = subTask1.getID();
        url = URI.create(http + "/subtasks" + String.valueOf(subTaskID));
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // сверяем задачи
        assertEquals(subTaskID, subTasksFromManager.get(0).getID(), "Некорректный код задачи. Ожидалось "
                + subTaskID + ", получили " + subTasksFromManager.get(0).getID());
        assertEquals(taskNameForCheck, subTasksFromManager.get(0).getTaskName(), "Некорректное наименование задачи. Ожидалось "
                + taskNameForCheck + ", получили " + subTasksFromManager.get(0).getTaskName());
    }

}