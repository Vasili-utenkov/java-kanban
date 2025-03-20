package http.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.adapter.DurationAdapter;
import http.adapter.LocalDateTimeAdapter;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubTasksHandlerTest {
/*
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

//        epic = new Epic("Эпик для связки", "Эпик для связки - описание");
//
//        System.out.println("epic = " + epic);
//        gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//                .registerTypeAdapter(Duration.class, new DurationAdapter())
//                .create();
//
//        epicJson = gson.toJson(epic);
//
//        System.out.println("epicJson = " + epicJson);
//
//        epicUrl = URI.create(http + "/epics");
//        // создаём HTTP-клиент и запрос
//
//        epicRequest = HttpRequest.newBuilder().uri(epicUrl).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
//        epicResponse = HttpClient.newHttpClient().send(epicRequest, HttpResponse.BodyHandlers.ofString());

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

        // создаем эпик

        epicID = epic.getID();
        System.out.println(epic);
        System.out.println("epicID = " + epicID);




    }

    @DisplayName("Создание пересекающейся по времения подзадачи:  POST /subtasks")
    @Test
    public void testAddInterruptedSubTask() throws IOException, InterruptedException {}

    @DisplayName("Изменение параметров подзадачи: POST /subtasks/{id}")
    @Test
    public void testUpdateSubTask() throws IOException, InterruptedException {}

    @DisplayName("Удаление подзадачи : DELETE  /subtasks/{id}")
    @Test
    public void testDeleteSubTask() throws IOException, InterruptedException {}

    @DisplayName("Запрос списка подзадач : GET  /subtasks")
    @Test
    public void testGetSubTask() throws IOException, InterruptedException {}

    @DisplayName("Запрос подзадачи : GET  /subtasks/{id}")
    @Test
    public void testGetSubTaskByID() throws IOException, InterruptedException {}
*/
}