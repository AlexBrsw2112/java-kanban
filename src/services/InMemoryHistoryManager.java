package services;

import models.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Node first;
    private Node last;

    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
       if (task == null) {
           return;
       } else {
           int id = task.getId();
           remove(id);
           linkLast(task);
           nodeMap.put(id, last);
       }
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.remove(id);
        if (node == null) {
            return;
        } else {
            removeNode(node);
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
            if (node.next == null) {
                last = node.prev;
            } else {
                node.next.prev = node.prev;
            }
        } else {
            first = node.next;
            if (first == null) {
                last = null;
            } else  {
                first.prev = null;
            }
         }
    }

    private void linkLast(Task task) {
        Node node = new Node(task, last, null);
        if(first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    private  List<Task> getTask() {
      List<Task> tasksArray = new ArrayList<>();
      Node node = first;
      while (node != null) {
          tasksArray.add(node.task);
          node = node.next;
      }
      return tasksArray;
    }
    @Override
    public List<Task> getHistory() {
        return getTask();
    }

    public static class Node {
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
