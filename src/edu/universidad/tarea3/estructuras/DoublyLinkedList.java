package edu.universidad.tarea3.estructuras;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista doblemente ligada simple (solo lo necesario para adyacencia).
 *
 * Complejidad:
 *  - addFront / addBack: O(1)
 *  - remove(value): O(n) (recorrer hasta encontrar)
 *  - size / toList: O(n)
 */
public class DoublyLinkedList {
    private DNode head;
    private DNode tail;
    private int size;

    public DoublyLinkedList() { this.size = 0; }

    public void addFront(int v) {
        DNode n = new DNode(v);
        if (head == null) {
            head = tail = n;
        } else {
            n.next = head;
            head.prev = n;
            head = n;
        }
        size++;
    }

    public void addBack(int v) {
        DNode n = new DNode(v);
        if (tail == null) {
            head = tail = n;
        } else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
        size++;
    }

    // elimina la primera ocurrencia de value y devuelve true si se eliminó
    public boolean remove(int value) {
        DNode cur = head;
        while (cur != null) {
            if (cur.value == value) {
                if (cur.prev != null) cur.prev.next = cur.next;
                else head = cur.next;
                if (cur.next != null) cur.next.prev = cur.prev;
                else tail = cur.prev;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public int size() { return size; }

    // lista de enteros en orden desde head a tail
    public List<Integer> toList() {
        List<Integer> res = new ArrayList<>();
        DNode cur = head;
        while (cur != null) {
            res.add(cur.value);
            cur = cur.next;
        }
        return res;
    }

    public boolean contains(int v) {
        DNode cur = head;
        while (cur != null) {
            if (cur.value == v) return true;
            cur = cur.next;
        }
        return false;
    }
}
