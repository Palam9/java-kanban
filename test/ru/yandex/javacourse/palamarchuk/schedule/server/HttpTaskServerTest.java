package ru.yandex.javacourse.palamarchuk.schedule.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import ru.yandex.javacourse.palamarchuk.schedule.task.Status;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private TaskManager manager;
    private HttpTaskServer taskServer;
    private Gson gson;

    @BeforeEach
    void setUp() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(manager);
        gson = new Gson();
        taskServer.start();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

//    @Test
//    void testAddTask() throws IOException, InterruptedException {
//        // Создаем задачу
//        Task task = new Task("Test Task", "Description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
//
//        // Сериализуем задачу в JSON
//        String taskJson = gson.toJson(task);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(201, response.statusCode(), "Неверный статус ответа");
//
//        // Проверяем, что задача добавилась в менеджер
//        Task addedTask = manager.getTask(1); // Предполагаем, что ID задачи будет 1
//        assertNotNull(addedTask, "Задача не добавлена");
//        assertEquals("Test Task", addedTask.getTitle(), "Неверное название задачи");
//    }

//    @Test
//    void testGetAllTasks() throws IOException, InterruptedException {
//        // Добавляем задачи
//        Task task1 = new Task("Task 1", "Description 1", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
//        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
//        manager.addTask(task1);
//        manager.addTask(task2);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .GET()
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(200, response.statusCode(), "Неверный статус ответа");
//
//        // Десериализуем ответ
//        Task[] tasks = gson.fromJson(response.body(), Task[].class);
//        assertEquals(2, tasks.length, "Неверное количество задач");
//        assertEquals("Task 1", tasks[0].getTitle(), "Неверное название первой задачи");
//        assertEquals("Task 2", tasks[1].getTitle(), "Неверное название второй задачи");
//    }
//
//    @Test
//    void testGetTaskById() throws IOException, InterruptedException {
//        // Добавляем задачу
//        Task task = new Task("Test Task", "Description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
//        int taskId = manager.addTask(task);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks/" + taskId);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .GET()
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(200, response.statusCode(), "Неверный статус ответа");
//
//        // Десериализуем ответ
//        Task returnedTask = gson.fromJson(response.body(), Task.class);
//        assertNotNull(returnedTask, "Задача не найдена");
//        assertEquals(taskId, returnedTask.getId(), "Неверный ID задачи");
//        assertEquals("Test Task", returnedTask.getTitle(), "Неверное название задачи");
//    }

    @Test
    void testDeleteTask() throws IOException, InterruptedException {
        // Добавляем задачу
        Task task = new Task("Test Task", "Description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
        int taskId = manager.addTask(task);

        // Создаем HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/" + taskId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        // Отправляем запрос и получаем ответ
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Проверяем код ответа
        assertEquals(200, response.statusCode(), "Неверный статус ответа");

        // Проверяем, что задача удалена
        assertNull(manager.getTask(taskId), "Задача не удалена");
    }

//    @Test
//    void testGetHistory() throws IOException, InterruptedException {
//        // Добавляем задачи и получаем их, чтобы добавить в историю
//        Task task1 = new Task("Task 1", "Description 1", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
//        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
//        manager.addTask(task1);
//        manager.addTask(task2);
//        manager.getTask(1);
//        manager.getTask(2);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/history");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .GET()
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(200, response.statusCode(), "Неверный статус ответа");
//
//        // Десериализуем ответ
//        Task[] history = gson.fromJson(response.body(), Task[].class);
//        assertEquals(2, history.length, "Неверное количество задач в истории");
//        assertEquals("Task 1", history[0].getTitle(), "Неверное название первой задачи в истории");
//        assertEquals("Task 2", history[1].getTitle(), "Неверное название второй задачи в истории");
//    }
//
//    @Test
//    void testGetPrioritizedTasks() throws IOException, InterruptedException {
//        // Добавляем задачи с разным временем начала
//        Task task1 = new Task("Task 1", "Description 1", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now());
//        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
//        manager.addTask(task1);
//        manager.addTask(task2);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/prioritized");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .GET()
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(200, response.statusCode(), "Неверный статус ответа");
//
//        // Десериализуем ответ
//        Task[] prioritizedTasks = gson.fromJson(response.body(), Task[].class);
//        assertEquals(2, prioritizedTasks.length, "Неверное количество задач");
//        assertEquals("Task 1", prioritizedTasks[0].getTitle(), "Неверный порядок задач");
//    }
}
