package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Subtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler {
    public SubtaskHandler(TaskManager manager) {
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

        if (pathParts.length == 3 && pathParts[1].equals("subtasks")) {
            try {
                int subtaskId = Integer.parseInt(pathParts[2]);
                Subtask subtask = manager.getSubtask(subtaskId);

                if (subtask != null) {
                    String response = gson.toJson(subtask);
                    sendText(exchange, response, 200);
                } else {
                    String response = "Подзадача с id=" + subtaskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор подзадачи";
                sendText(exchange, response, 400);
            }
        } else {
            List<Subtask> subtasks = manager.getAllSubtasks();
            String response = gson.toJson(subtasks);
            sendText(exchange, response, 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Subtask subtask = gson.fromJson(body, Subtask.class);

        if (pathParts.length == 3 && pathParts[1].equals("subtasks")) {
            try {
                int subtaskId = Integer.parseInt(pathParts[2]);
                Subtask existingSubtask = manager.getSubtask(subtaskId);

                if (existingSubtask != null) {
                    subtask.setId(subtaskId);
                    if (manager.isTaskTimeOverlap(subtask)) {
                        String response = "Подзадача пересекается по времени с другой задачей";
                        sendText(exchange, response, 406);
                    } else {
                        manager.updateSubtask(subtask);
                        sendText(exchange, gson.toJson(subtask), 200);
                    }
                } else {
                    String response = "Подзадача с id=" + subtaskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор подзадачи";
                sendText(exchange, response, 400);
            }
        } else {
            if (manager.isTaskTimeOverlap(subtask)) {
                String response = "Подзадача пересекается по времени с другой задачей";
                sendText(exchange, response, 406);
            } else {
                manager.addSubtask(subtask);
                sendText(exchange, gson.toJson(subtask), 201);
            }
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 3 && pathParts[1].equals("subtasks")) {
            try {
                int subtaskId = Integer.parseInt(pathParts[2]);
                Subtask subtask = manager.getSubtask(subtaskId);

                if (subtask != null) {
                    manager.removeSubtask(subtaskId);
                    sendText(exchange, gson.toJson("Подзадача с id=" + subtaskId + " удалена"), 200);
                } else {
                    String response = "Подзадача с id=" + subtaskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор подзадачи";
                sendText(exchange, response, 400);
            }
        } else {
            String response = "Не указан идентификатор подзадачи";
            sendText(exchange, response, 400);
        }
    }
}

