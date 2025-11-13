package edu.universidad.tarea3.arbolesBinarios;

import java.util.HashMap;
import java.util.Map;

/**
 * Árbol binario de búsqueda simple con contadores de frecuencia para operaciones.
 *
 * La tarea pide indicar contador de frecuencias y orden de magnitud para:
 * - searchBinary (búsqueda)
 * - traversePostorder (postorden)
 *
 * Aquí se proveen implementaciones que incrementan el contador "visits" por cada nodo visitado.
 */
public class BinarySearchTree {
    public NodeTree root;
    private Map<String, Integer> freq;

    public BinarySearchTree() { this.freq = new HashMap<>(); }

    private void incr(String k) { freq.put(k, freq.getOrDefault(k,0)+1); }

    public void insert(int val) {
        root = insertRec(root, val);
    }
    private NodeTree insertRec(NodeTree node, int val) {
        if (node == null) return new NodeTree(val);
        if (val < node.info) node.left = insertRec(node.left, val);
        else node.right = insertRec(node.right, val);
        return node;
    }

    // a) Búsqueda en ABB: incrementa contador por cada visita a nodo
    // Contador de frecuencias: "searchBinaryVisits"
    // Orden de magnitud: O(h) donde h = altura del árbol; promedio O(log n) si balanceado, peor O(n) si degenerado.
    public boolean searchBinary(NodeTree node, int datum) {
        incr("searchBinaryCalls");
        if (node != null) {
            incr("searchBinaryVisits");
            if (datum < node.info) {
                return searchBinary(node.left, datum);
            } else if (datum > node.info) {
                return searchBinary(node.right, datum);
            } else {
                System.out.println("Nodo encontrado en el árbol");
                return true;
            }
        } else {
            System.out.println("Nodo no encontrado en el árbol");
            return false;
        }
    }

    // b) Recorrido postorden
    // Contador de frecuencias: "postorderVisits" incrementado por cada nodo visitado
    // Orden de magnitud: O(n) (visita cada nodo una vez)
    public void traversePostorder(NodeTree node) {
        incr("traversePostorderCalls");
        if (node != null) {
            traversePostorder(node.left);
            traversePostorder(node.right);
            incr("postorderVisits");
            System.out.println("Nodo: " + node.info);
        }
    }

    public Map<String,Integer> getFreq() {
        return new HashMap<>(freq);
    }
}
