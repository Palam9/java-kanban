import ru.yandex.javacourse.palamarchuk.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.palamarchuk.schedule.manager.HistoryManager;  // Возможно, потребуется добавить импорт интерфейса
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import ru.yandex.javacourse.palamarchuk.schedule.task.Status;

import org.junit.jupiter.api.*;
import ru.yandex.javacourse.palamarchuk.schedule.manager.*;
import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void setUp() throws IOException {
        // Создаем временный файл перед каждым тестом
        tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit(); // Файл будет удален при завершении программы
        manager = new FileBackedTaskManager(tempFile);
    }

    @Test
    void testSaveAndLoadEmptyFile() {
        // Проверяем, что файл пустой при создании менеджера
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Поскольку в файле нет задач, их должно быть 0
        assertEquals(0, loadedManager.getTasks().size());
        assertEquals(0, loadedManager.getAllEpics().size());
        assertEquals(0, loadedManager.getAllSubtasks().size());
    }

    @Test
    void testSaveMultipleTasks() {
        // Добавляем несколько задач
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        manager.addTask(task1);

        Epic epic = new Epic("Epic 1", "Description 2");
        epic.setId(2);
        manager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Description 3", Status.IN_PROGRESS, epic.getId());
        subtask.setId(3);
        manager.addSubtask(subtask);

        // Сохраняем данные
        manager.save();

        // Загружаем данные из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем, что все задачи загрузились
        assertEquals(1, loadedManager.getTasks().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());

        // Проверяем данные задачи
        Task loadedTask1 = loadedManager.getTasks().get(0);
        assertEquals("Task 1", loadedTask1.getTitle());

        Epic loadedEpic = loadedManager.getAllEpics().get(0);
        assertEquals("Epic 1", loadedEpic.getTitle());

        Subtask loadedSubtask = loadedManager.getAllSubtasks().get(0);
        assertEquals("Subtask 1", loadedSubtask.getTitle());
    }

    @Test
    void testLoadMultipleTasks() {
        // Создаем задачи и сохраняем их в файл
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        task1.setId(1);
        manager.addTask(task1);

        Epic epic = new Epic("Epic 1", "Description 2");
        epic.setId(2);
        manager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask 1", "Description 3", Status.IN_PROGRESS, epic.getId());
        subtask.setId(3);
        manager.addSubtask(subtask);

        manager.save(); // Сохраняем в файл

        // Загружаем данные из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем количество задач
        assertEquals(1, loadedManager.getTasks().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());

        // Проверяем данные задач
        Task loadedTask = loadedManager.getTasks().get(0);
        assertEquals("Task 1", loadedTask.getTitle());

        Epic loadedEpic = loadedManager.getAllEpics().get(0);
        assertEquals("Epic 1", loadedEpic.getTitle());

        Subtask loadedSubtask = loadedManager.getAllSubtasks().get(0);
        assertEquals("Subtask 1", loadedSubtask.getTitle());
    }
}

