package ru.yandex.javacourse.palamarchuk.schedule.server;

import com.sun.net.httpserver.HttpServer;
import ru.yandex.javacourse.palamarchuk.schedule.manager.Managers;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final  HttpServer server;
    private final  TaskManager manager;

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.manager = manager;
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler(manager));
        server.createContext("/subtasks", new SubtaskHandler(manager));
        server.createContext("/epics", new EpicHandler(manager));
        server.createContext("/history", new HistoryHandler(manager));
        server.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public void start() {
        server.start();
        System.out.println("HTTP-сервер запущен на порту " + PORT);
    }

    public void stop() {
        server.stop(0);
        System.out.println("HTTP-сервер остановлен");
    }

    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefault();
        HttpTaskServer taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }
}
