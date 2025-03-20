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
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class EpicsHandlerTest {

    // создаём экземпляр InMemoryTaskManager
    InMemoryTaskManager manager = new InMemoryTaskManager();
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();
    String http = "http://localhost:8080";

    public EpicsHandlerTest() throws IOException {

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


    @DisplayName("Создание эпика: POST /epics")
    @Test
    public void testAddEpic() throws IOException, InterruptedException {

        Epic epic;
        String epicJson;
        URI epicUrl;
        HttpRequest epicRequest;
        HttpResponse<String> epicResponse;

        epic = new Epic("Эпик 1", "Эпик 1 - описание");
        epicJson = gson.toJson(epic);
        epicUrl = URI.create(http + "/epics");
        // создаём HTTP-клиент и запрос
        epicRequest = HttpRequest.newBuilder().uri(epicUrl).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
        epicResponse = HttpClient.newHttpClient().send(epicRequest, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, epicResponse.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + epicResponse.statusCode());

        List<Epic> epicsFromManager = manager.getEpicsList();

        assertNotNull(epicsFromManager, "Задачи не возвращаются");
        assertEquals(1, epicsFromManager.size(), "Некорректное количество подзадач");
        assertEquals("Эпик 1", epicsFromManager.get(0).getTaskName(), "Некорректное имя подзадачи");

    }


    @DisplayName("Удаление эпика : DELETE  /epics/{id}")
    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {

        Epic epic1, epic2;
        String epic1Json, epic2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<Epic> epicList;
        URI url = URI.create(http + "/epics");

        // создаём 1-ю задачу
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1Json = gson.toJson(epic1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epic1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        epic2 = new Epic("Эпик 1 - удаление", "Описание эпика 1");
        epic2Json = gson.toJson(epic2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epic2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        epicList = manager.getEpicsList();
        assertNotNull(epicList, "Эпики не возвращаются");
        assertEquals(2, epicList.size(), "Некорректное количество эпиков");


        // Удаляем задачу 2
        Integer taskForDeleteID = epicList.get(epicList.size() - 1).getID();
        url = URI.create(http + "/epics/" + String.valueOf(taskForDeleteID));

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).DELETE().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что осталась одна задача
        epicList = manager.getEpicsList();
        assertNotNull(epicList, "Эпики не возвращаются");
        assertEquals(1, epicList.size(), "Некорректное количество эпиков");


    }

    @DisplayName("Запрос списка эпиков : GET  /epics")
    @Test
    public void testGetEpicList() throws IOException, InterruptedException {

        Epic epic1, epic2;
        String epic1Json, epic2Json;
        HttpRequest request;
        HttpResponse<String> response;
        List<Epic> epicList;
        URI url = URI.create(http + "/epics");

        // создаём 1-ю задачу
        epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1Json = gson.toJson(epic1);

        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epic1Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // создаём 2-ю задачу
        epic2 = new Epic("Эпик 2", "Описание эпика 2");
        epic2Json = gson.toJson(epic2);
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epic2Json)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что есть 2 задачи
        epicList = manager.getEpicsList();
        assertNotNull(epicList, "эпикои не возвращаются");
        assertEquals(2, epicList.size(), "Некорректное количество эпиков");

        // Запрос списка задач
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // разбираем строку в формате JSON на элементы
        JsonArray getAsJsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        assertEquals(getAsJsonArray.size(), epicList.size(), "количество эпиков в ответе за запрос: "
                + getAsJsonArray.size() + " не совпадает с количеством эпиков в списке: " + epicList.size() );
    }

    @DisplayName("Запрос эпика : GET  /epics/{id}")
    @Test
    public void testGetEpicByID() throws IOException, InterruptedException {
        Epic epic;
        String epicJson;
        HttpRequest request;
        HttpResponse<String> response;
        URI url;

        String taskNameForCheck = "Эпик 1";

        // создаём задачу
        epic = new Epic(taskNameForCheck, "Эпик 1 - описание");
        // конвертируем её в JSON
        epicJson = gson.toJson(epic);

        url = URI.create(http + "/epics");
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201, response.statusCode(), "Вместо кода 201 вернулся неверный код ответа " + response.statusCode());

        // проверяем, что задача создалась
        List<Epic> epicsList = manager.getEpicsList();
        assertNotNull(epicsList, "Задачи не возвращаются");
        assertEquals(1, epicsList.size(), "Некорректное количество задач");

        // Запрашиваем задачу
        Epic epic1 = epicsList.get(0);
        Integer epicID = epic1.getID();
        url = URI.create(http + "/epics" + String.valueOf(epicID));
        // создаём HTTP-клиент и запрос
        request = HttpRequest.newBuilder().uri(url).GET().build();
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode(), "Вместо кода 200 вернулся неверный код ответа " + response.statusCode());
        // сверяем задачи
        assertEquals(epicID, epicsList.get(0).getID(), "Некорректный код эпика. Ожидалось "
                + epicID + ", получили " + epicsList.get(0).getID());
        assertEquals(taskNameForCheck, epicsList.get(0).getTaskName(), "Некорректное наименование эпика. Ожидалось "
                + taskNameForCheck + ", получили " + epicsList.get(0).getTaskName());

    }

}