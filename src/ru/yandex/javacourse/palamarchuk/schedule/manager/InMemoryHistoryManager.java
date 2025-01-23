package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> taskMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (taskMap.containsKey(task.getId())) {
            // Удаляем старый узел, если задача уже есть в истории
            remove(task.getId());
        }

        // Создаем новый узел с задачей
        Node newNode = new Node(task);

        // Добавляем в конец списка
        linkLast(newNode);

        // Добавляем в HashMap
        taskMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node node = taskMap.get(id);
        if (node != null) {
            // Удаляем узел из списка
            removeNode(node);

            // Удаляем из HashMap
            taskMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    private void linkLast(Node newNode) {
        if (tail == null) {
            head = tail = newNode; // Пустой список
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode; // Теперь новый узел — это хвост
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // Если это был первый элемент
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev; // Если это был последний элемент
        }
    }

    // Класс для узлов списка
    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task) {
            this.task = task;
        }
    }
}

