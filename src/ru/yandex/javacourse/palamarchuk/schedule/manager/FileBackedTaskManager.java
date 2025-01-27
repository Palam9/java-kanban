package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

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
        try (Writer writer = new FileWriter(file)) {
            // Заголовок
            writer.write("id,type,name,status,description,epic\n");

            // Сохранение всех задач
            for (Task task : getTasks()) {
                writer.write(toString(task));
                writer.write("\n");
            }

            // Сохранение всех эпиков
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic));
                writer.write("\n");
            }

            // Сохранение всех подзадач
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask));
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных в файл: " + file.getName(), e);
        }
    }

    /**
     * Преобразование задачи в строку CSV.
     */
    private String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(",")
                .append(getTaskType(task)).append(",")
                .append(task.getTitle()).append(",")
                .append(task.getStatus()).append(",")
                .append(task.getDescription());

        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            sb.append(",").append(subtask.getEpicId());
        } else {
            sb.append(",");
        }

        return sb.toString();
    }

    /**
     * Получение типа задачи.
     */
    private TaskType getTaskType(Task task) {
        if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        } else if (task instanceof Epic) {
            return TaskType.EPIC;
        } else {
            return TaskType.TASK;
        }
    }

    /**
     * Восстановление менеджера из файла.
     */
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.isEmpty()) return manager;

            for (int i = 1; i < lines.size(); i++) { // Пропускаем заголовок
                String line = lines.get(i);
                Task task = fromString(line);
                if (task instanceof Subtask) {
                    manager.addSubtask((Subtask) task);
                } else if (task instanceof Epic) {
                    manager.addEpic((Epic) task);
                } else {
                    manager.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных из файла: " + file.getName(), e);
        }

        return manager;
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
                Task task = new Task(name, description, status);
                task.setId(id);
                return task;
            case EPIC:
                // Создаем Epic с двумя параметрами: name и description
                Epic epic = new Epic(name, description);
                epic.setId(id);
                return epic;
            case SUBTASK:
                int epicId = Integer.parseInt(fields[5]);
                Subtask subtask = new Subtask(name, description, status, epicId);
                subtask.setId(id);
                return subtask;
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }
}

