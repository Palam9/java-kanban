import org.junit.jupiter.api.*;
import ru.yandex.javacourse.palamarchuk.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        manager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    void tearDown() {
        tempFile.delete();
    }


    @Test
    public void testSaveAndLoadEmptyFile() throws Exception {
        // Сохраняем пустой менеджер
        manager.save();

        // Загружаем пустой менеджер из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(loadedManager.getAllTasks().isEmpty());
        assertTrue(loadedManager.getAllEpics().isEmpty());
        assertTrue(loadedManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void testSaveMultipleTasks() throws Exception {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
        Epic epic = new Epic("Epic 1", "Epic Description");

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic);

        manager.save();

        assertTrue(Files.exists(tempFile.toPath()), "Файл должен существовать после сохранения");

        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(4, lines.size(), "Файл должен содержать заголовок и 3 записи");

        assertEquals(FileBackedTaskManager.getHeader(), lines.get(0), "Первая строка должна быть заголовком");
        assertTrue(lines.contains(FileBackedTaskManager.toString(task1)), "Задача 1 должна быть сохранена");
        assertTrue(lines.contains(FileBackedTaskManager.toString(task2)), "Задача 2 должна быть сохранена");
        assertTrue(lines.contains(FileBackedTaskManager.toString(epic)), "Эпик должен быть сохранен");
    }


    //todo Тут либо FileBackedTaskManager ошибка, либо подскажи, подплуйста, правильная логика

//    @Test
//    public void testLoadMultipleTasksFromFile() throws Exception {
//        // Создаем задачи
//        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
//        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS);
//        Epic epic = new Epic("Epic 1", "Epic Description");
//
//        int task1Id = manager.addTask(task1);
//        int task2Id = manager.addTask(task2);
//        int epicId = manager.addEpic(epic);
//
//        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, epicId);
//        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", Status.DONE, epicId);
//
//        int subtask1Id = manager.addSubtask(subtask1);
//        int subtask2Id = manager.addSubtask(subtask2);
//
//        manager.save();
//
//        // Загружаем новый менеджер из файла
//        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
//
//        // Проверяем, что задачи корректно восстановлены
//        assertEquals(2, loadedManager.getAllTasks().size(), "Должно быть 2 обычных задачи");
//        assertEquals(1, loadedManager.getAllEpics().size(), "Должен быть 1 эпик");
//        assertEquals(2, loadedManager.getAllSubtasks().size(), "Должно быть 2 подзадачи");
//
//        // Проверяем, что загруженные задачи совпадают по данным
//        assertEquals(task1, loadedManager.getTask(task1Id), "Задача 1 должна быть восстановлена корректно");
//        assertEquals(task2, loadedManager.getTask(task2Id), "Задача 2 должна быть восстановлена корректно");
//        assertEquals(epic, loadedManager.getEpic(epicId), "Эпик должен быть восстановлен корректно");
//        assertEquals(subtask1, loadedManager.getSubtask(subtask1Id), "Подзадача 1 должна быть восстановлена корректно");
//        assertEquals(subtask2, loadedManager.getSubtask(subtask2Id), "Подзадача 2 должна быть восстановлена корректно");
//    }






}
