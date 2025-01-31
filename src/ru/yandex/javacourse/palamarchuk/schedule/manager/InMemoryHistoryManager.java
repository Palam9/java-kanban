package ru.yandex.javacourse.palamarchuk.schedule.manager;

import ru.yandex.javacourse.palamarchuk.schedule.task.Task;
import ru.yandex.javacourse.palamarchuk.schedule.manager.HistoryManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }

        if (historyMap.size() == HISTORY_LIMIT) {
            remove(head.task.getId()); // Удаляем самую старую задачу
        }

        Node newNode = new Node(task, tail, null);
        linkLast(newNode);
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node node = historyMap.get(id);
        if (node != null) {
            removeNode(node);
            historyMap.remove(id);
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

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
