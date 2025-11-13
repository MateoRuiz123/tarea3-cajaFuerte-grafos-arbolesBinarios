package edu.universidad.tarea3.estructuras;

/**
 * Nodo para lista doblemente ligada (guarda índice del vértice adyacente).
 */
public class DNode {
    public int value;      // índice / id del vértice adyacente
    public DNode prev;
    public DNode next;

    public DNode(int value) {
        this.value = value;
    }
}
