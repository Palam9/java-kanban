package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseHttpHandler implements HttpHandler {
    protected TaskManager manager;
    protected Gson gson = new Gson();

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    protected void sendText(HttpExchange exchange, String text, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, text.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(text.getBytes());
        }
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        String response = gson.toJson("404 Not Found");
        sendText(exchange, response, 404);
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        String response = gson.toJson("406 Not Acceptable");
        sendText(exchange, response, 406);
    }
}
