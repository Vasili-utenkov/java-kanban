package http.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import http.adapter.DurationAdapter;
import http.adapter.LocalDateTimeAdapter;
import manager.Managers;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;


    public HttpTaskServer(TaskManager manager) throws IOException {

        server = HttpServer
                .create(new InetSocketAddress("localhost", PORT), 0);

        server.createContext("/tasks", new TaskHandler(manager));
        server.createContext("/subtasks", new SubTasksHandler(manager));
        server.createContext("/epics", new EpicsHandler(manager));
        server.createContext("/history", new HistoryHandler(manager));
        server.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        HttpTaskServer taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    public void start() {
        System.out.println("ЗАПУСТИЛИ сервер на порту: " + PORT);
        server.start();
    }

    public void stop() {
        System.out.println("ОСТАНОВИЛИ сервер на порту: " + PORT);
        server.stop(0);
    }

}
