package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Epic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 3 && pathParts[1].equals("epics")) {
            try {
                int epicId = Integer.parseInt(pathParts[2]);
                Epic epic = manager.getEpic(epicId);

                if (epic != null) {
                    sendText(exchange, gson.toJson(epic), 200);
                } else {
                    sendText(exchange, "Эпик с id=" + epicId + " не найден", 404);
                }
            } catch (NumberFormatException e) {
                sendText(exchange, "Некорректный идентификатор эпика", 400);
            }
        } else {
            List<Epic> epics = manager.getAllEpics();
            sendText(exchange, gson.toJson(epics), 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(body, Epic.class);

        if (pathParts.length == 3 && pathParts[1].equals("epics")) {
            try {
                int epicId = Integer.parseInt(pathParts[2]);
                Epic existingEpic = manager.getEpic(epicId);

                if (existingEpic != null) {
                    epic.setId(epicId);
                    manager.updateEpic(epic);
                    sendText(exchange, gson.toJson(epic), 200);
                } else {
                    sendText(exchange, "Эпик с id=" + epicId + " не найден", 404);
                }
            } catch (NumberFormatException e) {
                sendText(exchange, "Некорректный идентификатор эпика", 400);
            }
        } else {
            manager.addEpic(epic);
            sendText(exchange, gson.toJson(epic), 201);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 3 && pathParts[1].equals("epics")) {
            try {
                int epicId = Integer.parseInt(pathParts[2]);
                Epic epic = manager.getEpic(epicId);

                if (epic != null) {
                    manager.removeEpic(epicId);
                    sendText(exchange, "Эпик с id=" + epicId + " удалён", 200);
                } else {
                    sendText(exchange, "Эпик с id=" + epicId + " не найден", 404);
                }
            } catch (NumberFormatException e) {
                sendText(exchange, "Некорректный идентификатор эпика", 400);
            }
        }
    }
}
