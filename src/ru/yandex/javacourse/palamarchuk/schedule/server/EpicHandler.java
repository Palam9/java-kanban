package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Epic;

import java.io.IOException;
import java.util.List;

public class EpicHandler extends BaseHttpHandler {
    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            case "DELETE":
                handleDelete(exchange);
                break;
            default:
                sendNotFound(exchange);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        List<Epic> epics = manager.getAllEpics();
        String response = gson.toJson(epics);
        sendText(exchange, response, 200);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        Epic epic = gson.fromJson(body, Epic.class);
        manager.addEpic(epic);
        sendText(exchange, gson.toJson(epic), 201);
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        manager.deleteEpics();
        sendText(exchange, gson.toJson("All epics deleted"), 200);
    }
}
