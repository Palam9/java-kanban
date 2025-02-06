package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.Duration;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String HEADER = "id,type,name,status,description,epic";
    private final File file;

    public static String getHeader() {
        return HEADER;
    }

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public int addTask(Task task) {
        int id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer addSubtask(Subtask subtask) {
        Integer id = super.addSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }


    /**
     * Сохранение состояния менеджера в файл.
     */
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Заголовок
            writer.write(HEADER);
            writer.newLine();

            // Сохранение всех задач
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }

            // Сохранение всех эпиков
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }

            // Сохранение всех подзадач
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                writer.write(toString(entry.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных в файл: " + file.getName(), e);
        }
    }

    /**
     * Преобразование задачи в строку CSV.
     */
    public static String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() + ","
                + task.getDescription() + (task.getType().equals(TaskType.SUBTASK) ? "," + ((Subtask) task).getEpicId() : "");
    }

    /**
     * Восстановление менеджера из файла.
     */
    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManager = new FileBackedTaskManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split(System.lineSeparator());
            int generatorId = 0;
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    break;
                }
                final Task task = fromString(line);
                final int id = task.getId();
                if (id > generatorId) {
                    generatorId = id;
                }
                taskManager.addAnyTask(task);
            }
            for (Map.Entry<Integer, Subtask> e : taskManager.subtasks.entrySet()) {
                final Subtask subtask = e.getValue();
                final Epic epic = taskManager.epics.get(subtask.getEpicId());
                epic.addSubtaskId(subtask.getId());
            }
            taskManager.generatorId = generatorId;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных из файла: " + file.getName(), e);
        }
        return taskManager;
    }

    /**
     * Преобразование строки CSV в задачу.
     */
    private static Task fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String name = fields[2];
        Status status = Status.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case TASK:
                return new Task(name, description, status, Duration.ZERO, null); // Добавлены Duration и startTime
            case EPIC:
                return new Epic(name, description);
            case SUBTASK:
                int epicId = Integer.parseInt(fields[5]);
                return new Subtask(name, description, status, Duration.ZERO, null, epicId); // Добавлены Duration и startTime
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }


    /**
     * Вспомогательный метод для добавления задачи.
     */
    protected void addAnyTask(Task task) {
        final int id = task.getId();
        switch (task.getType()) {
            case TASK:
                tasks.put(id, task);
                break;
            case SUBTASK:
                subtasks.put(id, (Subtask) task);
                break;
            case EPIC:
                epics.put(id, (Epic) task);
                break;
        }
    }
}

