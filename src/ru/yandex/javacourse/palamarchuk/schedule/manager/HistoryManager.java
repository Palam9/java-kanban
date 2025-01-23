package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import java.util.List;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int id);
    List<Task> getHistory();
}

