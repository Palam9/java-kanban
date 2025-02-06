package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.Epic;
import ru.yandex.javacourse.palamarchuk.schedule.task.Subtask;
import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
//для проверки

import java.util.List;

public interface TaskManager {

   void addTaskTime(Task task);

    List<Task> getPrioritizedTasks();

    int addTask(Task task);

    int addEpic(Epic epic);

    Integer addSubtask(Subtask subtask);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubtask(int id);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getSubtasksByEpic(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> getHistory(); // Новый метод

    List<Task> getTasks();

}
