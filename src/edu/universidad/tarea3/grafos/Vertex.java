package edu.universidad.tarea3.grafos;

import edu.universidad.tarea3.estructuras.DoublyLinkedList;

/**
 * Representa un vértice en el grafo: {info, head}.
 * info: valor entero (id o etiqueta); head: lista de adyacencia (LDL).
 */
public class Vertex {
    public int info;
    public DoublyLinkedList head;
    public boolean active; // ayuda para "eliminar" sin mover indices

    public Vertex(int info) {
        this.info = info;
        this.head = new DoublyLinkedList();
        this.active = true;
    }
}
