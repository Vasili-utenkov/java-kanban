package http.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

    protected String readText(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    protected void sendSuccess200(HttpExchange exchange, String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(response);
        exchange.close();
    }


    protected void sendTaskCreated201(HttpExchange exchange, String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(201, response.length);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(response);
        exchange.close();
    }


    protected void sendTaskNoFound404(HttpExchange exchange) throws IOException {
        byte[] response = "Задача не найдена.".getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(404, response.length);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    protected void sendTaskIntercepted406(HttpExchange exchange) throws IOException {
        byte[] response = ("Новая задача пересекается по времени выполнения " +
                "с уже внесенными в список задачами.").getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(406, response.length);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.getResponseBody().write(response);
        exchange.close();
    }


    // ++++++ Получить код из строки
    public Integer getTaskIdFromPath(String path) {
        Integer integer = null;
        String[] paths = path.split("/");
        if (paths.length > 2) {
            integer = Integer.parseInt(paths[2]);
        }
        return integer;
    }

    public boolean getSubTasksList(String path) {
        boolean getList = false;
        String[] paths = path.split("/");
        if (paths.length > 3) {
            getList = paths[3].equals("subtasks");
        }
        return getList;
    }


}
