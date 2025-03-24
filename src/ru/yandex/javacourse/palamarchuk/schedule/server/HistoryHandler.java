package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;

import java.io.IOException;
import java.util.List;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<Task> history = manager.getHistory();
            String response = gson.toJson(history);
            sendText(exchange, response, 200);
        } else {
            sendNotFound(exchange);
        }
    }
}
