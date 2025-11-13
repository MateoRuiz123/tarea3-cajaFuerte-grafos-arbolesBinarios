package edu.universidad.tarea3.arbolesBinarios;

import java.util.HashMap;
import java.util.Map;

/**
 * PROBLEMA 3: Árbol Binario de Búsqueda (ABB) con análisis de complejidad
 *
 * Un árbol binario de búsqueda es una estructura donde:
 * - Cada nodo tiene como máximo 2 hijos (izquierdo y derecho)
 * - Todos los valores a la IZQUIERDA son MENORES que el nodo
 * - Todos los valores a la DERECHA son MAYORES que el nodo
 *
 * Ejemplo visual:
 * 50
 * / \
 * 30 70
 * / \ / \
 * 20 40 60 80
 */
public class BinarySearchTree {
    public NodeTree root; // raíz del árbol (nodo principal)
    private Map<String, Integer> freq; // contadores de operaciones

    public BinarySearchTree() {
        this.freq = new HashMap<>();
    }

    /**
     * Incrementa un contador específico en el mapa de frecuencias
     */
    private void incr(String k) {
        freq.put(k, freq.getOrDefault(k, 0) + 1);
    }

    /**
     * Reinicia todos los contadores a cero
     */
    public void resetFreq() {
        freq.clear();
    }

    /**
     * Inserta un nuevo valor en el árbol.
     * Mantiene la propiedad: izquierda < nodo < derecha
     */
    public void insert(int val) {
        root = insertRec(root, val);
    }

    /**
     * Método auxiliar para insertar.
     * Va navegando por el árbol hasta encontrar el lugar correcto.
     */
    private NodeTree insertRec(NodeTree node, int val) {
        // Caso base: encontramos un lugar vacío
        if (node == null)
            return new NodeTree(val);

        // Caso: decidir si ir a izquierda o derecha
        if (val < node.info)
            node.left = insertRec(node.left, val); // ir a la izquierda
        else
            node.right = insertRec(node.right, val); // ir a la derecha

        return node;
    }

    // PREGUNTA 3a: Búsqueda en un Árbol Binario de Búsqueda

    /**
     * Busca un valor específico en el árbol binario de búsqueda.
     *
     * ¿CÓMO FUNCIONA?
     * 1. Empezamos en la raíz (nodo principal)
     * 2. Si el valor buscado es menor, vamos a la izquierda
     * 3. Si el valor buscado es mayor, vamos a la derecha
     * 4. Si lo encontramos, retornamos true
     * 5. Si llegamos a null (vacío), no existe en el árbol
     *
     * EJEMPLO:
     * Buscar 60 en el árbol:
     * 50 ← (60 > 50, ir a derecha)
     * / \
     * 30 70 ← (60 < 70, ir a izquierda)
     * / \ / \
     * 20 40 60 80 ← Encontrado!
     *
     * Visitas: 3 nodos (50 → 70 → 60)
     *
     * CONTADOR DE FRECUENCIAS:
     * - "searchBinaryCalls": Número de llamadas recursivas totales
     * - "searchBinaryVisits": Número de NODOS VISITADOS (no null)
     *
     * ORDEN DE MAGNITUD: O(h) donde h = altura del árbol
     */
    public boolean searchBinary(NodeTree node, int datum) {
        incr("searchBinaryCalls"); // cuenta cada llamada recursiva

        if (node != null) {
            incr("searchBinaryVisits"); // cuenta cada nodo que visitamos

            if (datum < node.info) {
                // El valor buscado es menor → ir a la izquierda
                return searchBinary(node.left, datum);

            } else if (datum > node.info) {
                // El valor buscado es mayor → ir a la derecha
                return searchBinary(node.right, datum);

            } else {
                // ¡Encontrado! datum == node.info
                System.out.println("✓ Nodo encontrado en el árbol");
                return true;
            }
        } else {
            // Llegamos a null → el valor no existe
            System.out.println("✗ Nodo no encontrado en el árbol");
            return false;
        }
    }

    //
    // PREGUNTA 3b: Recorrido en Postorden

    /**
     * Recorre el árbol en POSTORDEN (izquierda-derecha-raíz).
     *
     * EJEMPLO visual:
     * 50(7) ← último en visitarse
     * / \
     * 30(3) 70(6) ← visitados después de sus hijos
     * / \ / \
     * 20(1) 40(2) 60(4) 80(5) ← visitados primero
     *
     * Orden de visita: 20 → 40 → 30 → 60 → 80 → 70 → 50
     *
     *
     * CONTADOR DE FRECUENCIAS:
     * - "traversePostorderCalls": Llamadas recursivas totales (incluye null)
     * - "postorderVisits": NODOS REALES visitados (no cuenta null)
     *
     * ORDEN DE MAGNITUD: O(n)
     *
     */
    public void traversePostorder(NodeTree node) {
        incr("traversePostorderCalls"); // cuenta cada llamada (incluye nulls)

        if (node != null) {
            // PASO 1: Procesar TODO el subárbol IZQUIERDO
            traversePostorder(node.left);

            // PASO 2: Procesar TODO el subárbol DERECHO
            traversePostorder(node.right);

            // PASO 3: Finalmente procesar la RAÍZ (este nodo)
            incr("postorderVisits"); // incrementar SOLO si es nodo real
            System.out.println("  Visitando nodo: " + node.info);
        }
        // Si node == null, simplemente retornamos (caso base)
    }

    /**
     * Devuelve una copia del mapa de frecuencias.
     * Útil para análisis de rendimiento.
     */
    public Map<String, Integer> getFreq() {
        return new HashMap<>(freq);
    }
}
