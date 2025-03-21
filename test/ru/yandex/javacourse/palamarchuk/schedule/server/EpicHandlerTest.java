//package ru.yandex.javacourse.palamarchuk.schedule.server;
//
//import com.google.gson.Gson;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryTaskManager;
//import ru.yandex.javacourse.palamarchuk.schedule.manager.TaskManager;
//import ru.yandex.javacourse.palamarchuk.schedule.task.Epic;
//import ru.yandex.javacourse.palamarchuk.schedule.task.Status;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class EpicHandlerTest {
//    private TaskManager manager;
//    private HttpTaskServer taskServer;
//    private Gson gson;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        manager = new InMemoryTaskManager();
//        taskServer = new HttpTaskServer(manager);
//        gson = new Gson();
//        taskServer.start();
//    }
//
//    @AfterEach
//    void tearDown() {
//        taskServer.stop();
//    }
//
//    @Test
//    void testAddEpic() throws IOException, InterruptedException {
//        // Создаем эпик
//        Epic epic = new Epic("Test Epic", "Description", Status.NEW);
//
//        // Сериализуем эпик в JSON
//        String epicJson = gson.toJson(epic);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/epics");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(201, response.statusCode(), "Неверный статус ответа");
//
//        // Проверяем, что эпик добавился в менеджер
//        Epic addedEpic = manager.getEpic(1); // Предполагаем, что ID эпика будет 1
//        assertNotNull(addedEpic, "Эпик не добавлен");
//        assertEquals("Test Epic", addedEpic.getTitle(), "Неверное название эпика");
//    }
//
//    @Test
//    void testGetAllEpics() throws IOException, InterruptedException {
//        // Добавляем эпики
//        Epic epic1 = new Epic("Epic 1", "Description 1", Status.NEW);
//        Epic epic2 = new Epic("Epic 2", "Description 2", Status.IN_PROGRESS);
//        manager.addEpic(epic1);
//        manager.addEpic(epic2);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/epics");
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
//        Epic[] epics = gson.fromJson(response.body(), Epic[].class);
//        assertEquals(2, epics.length, "Неверное количество эпиков");
//        assertEquals("Epic 1", epics[0].getTitle(), "Неверное название первого эпика");
//        assertEquals("Epic 2", epics[1].getTitle(), "Неверное название второго эпика");
//    }
//
//    @Test
//    void testDeleteEpic() throws IOException, InterruptedException {
//        // Добавляем эпик
//        Epic epic = new Epic("Test Epic", "Description", Status.NEW);
//        manager.addEpic(epic);
//
//        // Создаем HTTP-клиент и запрос
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/epics");
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(url)
//                .DELETE()
//                .build();
//
//        // Отправляем запрос и получаем ответ
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Проверяем код ответа
//        assertEquals(200, response.statusCode(), "Неверный статус ответа");
//
//        // Проверяем, что эпик удален
//        assertTrue(manager.getAllEpics().isEmpty(), "Эпики не удалены");
//    }
//}
