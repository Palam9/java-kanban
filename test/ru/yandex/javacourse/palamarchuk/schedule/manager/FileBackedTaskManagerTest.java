import org.junit.jupiter.api.*;
import ru.yandex.javacourse.palamarchuk.schedule.manager.FileBackedTaskManager;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;

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
    void testSaveMultipleTasks() {
        Task task = new Task("Task 1", "Description 1", Status.NEW, Duration.ofMinutes(10), null);
        Epic epic = new Epic("Epic 1", "Epic Description");
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", Status.IN_PROGRESS, Duration.ofMinutes(20), null, epic.getId());

        int taskId = manager.addTask(task);
        int epicId = manager.addEpic(epic);
        subtask.setEpicId(epicId);
        int subtaskId = manager.addSubtask(subtask);

        manager.save();

        List<String> lines;
        try {
            lines = Files.readAllLines(tempFile.toPath());
        } catch (IOException e) {
            fail("Ошибка чтения файла");
            return;
        }


        assertEquals(4, lines.size(), "Файл должен содержать заголовок и 3 записи");
        assertTrue(lines.get(1).contains("Task 1"), "Файл должен содержать запись о Task 1");
        assertTrue(lines.get(2).contains("Epic 1"), "Файл должен содержать запись о Epic 1");
        assertTrue(lines.get(3).contains("Subtask 1"), "Файл должен содержать запись о Subtask 1");
    }



//    @Test
//    public void testLoadMultipleTasksFromFile() throws Exception {
//        // Создаем задачи
//        Task task1 = new Task("Task 1", "Description 1", Status.NEW, Duration.ofMinutes(30), LocalDateTime.now());
//        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, Duration.ofMinutes(45), LocalDateTime.now().plusHours(1));
//
//        // Создаем эпик и добавляем его в менеджер
//        Epic epic = new Epic("Epic 1", "Epic Description");
//        int epicId = manager.addEpic(epic); // Добавляем эпик и получаем его ID
//
//        // Создаем подзадачи, связанные с эпиком
//        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, Duration.ofMinutes(15), LocalDateTime.now().plusHours(2), epicId);
//        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description 2", Status.DONE, Duration.ofMinutes(20), LocalDateTime.now().plusHours(3), epicId);
//
//        // Добавляем задачи в менеджер
//        int task1Id = manager.addTask(task1);
//        int task2Id = manager.addTask(task2);
//        int subtask1Id = manager.addSubtask(subtask1);
//        int subtask2Id = manager.addSubtask(subtask2);
//
//        // Сохраняем менеджер в файл
//        manager.save();
//
//        // Загружаем менеджер из файла
//        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
//
//        // Проверяем, что задачи, эпик и подзадачи загружены корректно
//        assertEquals(2, loadedManager.getAllTasks().size(), "Должно быть 2 обычных задачи");
//        assertEquals(1, loadedManager.getAllEpics().size(), "Должен быть 1 эпик");
//        assertEquals(2, loadedManager.getAllSubtasks().size(), "Должно быть 2 подзадачи");
//
//        // Проверяем, что загруженные задачи совпадают с исходными
//        assertEquals(task1, loadedManager.getTask(task1Id), "Задача 1 должна быть восстановлена корректно");
//        assertEquals(task2, loadedManager.getTask(task2Id), "Задача 2 должна быть восстановлена корректно");
//        assertEquals(epic, loadedManager.getEpic(epicId), "Эпик должен быть восстановлен корректно");
//        assertEquals(subtask1, loadedManager.getSubtask(subtask1Id), "Подзадача 1 должна быть восстановлена корректно");
//        assertEquals(subtask2, loadedManager.getSubtask(subtask2Id), "Подзадача 2 должна быть восстановлена корректно");
//
//        // Проверяем связи между эпиком и подзадачами
//        Epic loadedEpic = loadedManager.getEpic(epicId);
//        assertTrue(loadedEpic.getSubtaskIds().contains(subtask1Id), "Эпик должен содержать ID подзадачи 1");
//        assertTrue(loadedEpic.getSubtaskIds().contains(subtask2Id), "Эпик должен содержать ID подзадачи 2");
//    }


}
