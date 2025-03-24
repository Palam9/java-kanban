package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHandler extends BaseHttpHandler {
    public TaskHandler(TaskManager manager) {
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

        if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
            try {
                int taskId = Integer.parseInt(pathParts[2]);
                Task task = manager.getTask(taskId);

                if (task != null) {
                    String response = gson.toJson(task);
                    sendText(exchange, response, 200);
                } else {
                    String response = "Задача с id=" + taskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор задачи";
                sendText(exchange, response, 400);
            }
        } else {
            List<Task> tasks = manager.getTasks();
            String response = gson.toJson(tasks);
            sendText(exchange, response, 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(body, Task.class);

        if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
            try {
                int taskId = Integer.parseInt(pathParts[2]);
                Task existingTask = manager.getTask(taskId);

                if (existingTask != null) {
                    task.setId(taskId);
                    if (manager.isTaskTimeOverlap(task)) {
                        String response = "Задача пересекается по времени с другой задачей";
                        sendText(exchange, response, 406);
                    } else {
                        manager.updateTask(task);
                        sendText(exchange, gson.toJson(task), 200);
                    }
                } else {
                    String response = "Задача с id=" + taskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор задачи";
                sendText(exchange, response, 400);
            }
        } else {
            if (manager.isTaskTimeOverlap(task)) {
                String response = "Задача пересекается по времени с другой задачей";
                sendText(exchange, response, 406);
            } else {
                manager.addTask(task);
                sendText(exchange, gson.toJson(task), 201);
            }
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
            try {
                int taskId = Integer.parseInt(pathParts[2]);
                Task task = manager.getTask(taskId);

                if (task != null) {
                    manager.removeTask(taskId);
                    sendText(exchange, gson.toJson("Задача с id=" + taskId + " удалена"), 200);
                } else {
                    String response = "Задача с id=" + taskId + " не найдена";
                    sendText(exchange, response, 404);
                }
            } catch (NumberFormatException e) {
                String response = "Некорректный идентификатор задачи";
                sendText(exchange, response, 400);
            }
        } else {
            String response = "Не указан идентификатор задачи";
            sendText(exchange, response, 400);
        }
    }

    protected void sendText(HttpExchange exchange, String text, int statusCode) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
}
