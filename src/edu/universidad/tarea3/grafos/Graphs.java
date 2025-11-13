package edu.universidad.tarea3.grafos;

import edu.universidad.tarea3.estructuras.DoublyLinkedList;

import java.util.*;

/**
 * PROBLEMA 2: Implementación de Grafo con Listas de Adyacencia (LDL)
 *
 *
 * EJEMPLO VISUAL:
 * 10 ---- 20
 * | |
 * | |
 * 30 ---- 40
 *
 * Aquí tenemos:
 * - Vértices: 10, 20, 30, 40
 * - Aristas: (10-20), (10-30), (20-40), (30-40)
 */
public class Graphs {
    private Vertex[] vertices; // arreglo de vértices {info, head}
    private int maxV; // capacidad máxima de vértices
    private int count; // cantidad de vértices activos
    private Map<String, Integer> freq; // contadores de operaciones

    public Graphs(int maxVertices) {
        this.maxV = maxVertices;
        this.vertices = new Vertex[maxV];
        this.count = 0;
        this.freq = new HashMap<>();
    }

    /**
     * Incrementa un contador de frecuencia específico
     */
    private void incr(String k) {
        freq.put(k, freq.getOrDefault(k, 0) + 1);
    }

    /**
     * Agrega un nuevo vértice al grafo con la etiqueta 'info'.
     */
    public int addVertex(int info) {
        incr("addVertex");

        // Buscar el primer espacio disponible
        for (int i = 0; i < maxV; i++) {
            if (vertices[i] == null || !vertices[i].active) {
                vertices[i] = new Vertex(info);
                count++;
                return i; // retorna la posición donde se guardó
            }
        }
        return -1; // no hay espacio disponible
    }

    /**
     * Agrega una ARISTA (conexión) entre dos vértices.
     */
    public boolean addEdge(int u, int v) {
        incr("addEdge");

        // Validar que ambos índices sean válidos
        if (!validIndex(u) || !validIndex(v) || u == v)
            return false;

        // Evitar aristas duplicadas
        // Agregar v a la lista de u (u se conecta con v)
        if (!vertices[u].head.contains(v))
            vertices[u].head.addFront(v);

        // Agregar u a la lista de v (v se conecta con u)
        if (!vertices[v].head.contains(u))
            vertices[v].head.addFront(u);

        return true;
    }

    /**
     * Verifica si un índice es válido y el vértice está activo.
     */
    private boolean validIndex(int i) {
        return i >= 0 && i < maxV && vertices[i] != null && vertices[i].active;
    }

    // PREGUNTA 2a: Nodo con mayor número de nodos adyacentes

    /**
     * Encuentra el vértice con MÁS CONEXIONES (más vecinos).
     *
     *
     * EJEMPLO:
     * A --- B --- C
     * | |
     * D --- E
     *
     * Adyacentes de B: A, C, E (tiene 3 vecinos)
     * Adyacentes de D: A, E (tiene 2 vecinos)
     *
     * En este caso, B es el nodo con más adyacentes.
     *
     * ¿CÓMO FUNCIONA?
     * 1. Recorre TODOS los vértices del grafo
     * 2. Para cada uno, cuenta cuántos vecinos tiene (tamaño de su lista)
     * 3. Guarda el que tenga más
     */
    public int nodeWithMaxAdjacents() {
        incr("nodeWithMaxAdjacents");

        int mejorIndice = -1;
        int mayorCantidad = -1;

        // Revisar cada vértice activo
        for (int i = 0; i < maxV; i++) {
            if (validIndex(i)) {
                int cantidadVecinos = vertices[i].head.size();

                // Si tiene más vecinos que el actual campeón
                if (cantidadVecinos > mayorCantidad) {
                    mayorCantidad = cantidadVecinos;
                    mejorIndice = i;
                }
            }
        }

        return mejorIndice;
    }

    // PREGUNTA 2b: Eliminar un nodo

    /**
     * Elimina un vértice del grafo y TODAS sus conexiones.
     *
     *
     * EJEMPLO:
     * ANTES: DESPUÉS de eliminar B:
     * A --- B --- C A C
     * | | |
     * D --- E D --- E
     *
     * Ahora A y C ya no están conectados (porque B desapareció).
     *
     * ¿CÓMO FUNCIONA?
     * 1. Recorrer TODOS los demás vértices
     * 2. En cada uno, buscar y eliminar referencias al nodo eliminado
     * 3. Finalmente marcar el nodo como inactivo
     */
    public boolean removeNode(int idx) {
        incr("removeNode");

        if (!validIndex(idx))
            return false;

        // PASO 1: Eliminar referencias en las listas de otros vértices
        for (int i = 0; i < maxV; i++) {
            if (validIndex(i) && i != idx) {
                // Elimina 'idx' de la lista de adyacencia del vértice 'i'
                vertices[i].head.remove(idx);
            }
        }

        // PASO 2: Marcar el vértice como inactivo
        vertices[idx].active = false;
        vertices[idx].head = new DoublyLinkedList(); // liberar memoria
        count--;

        return true;
    }

    // PREGUNTA 2c: Longitud de camino entre dos nodos

    /**
     * Calcula la LONGITUD de un camino entre dos vértices.
     *
     * EJEMPLO:
     * A --- B --- C --- D
     *
     * Camino de A a D: A → B → C → D
     * Longitud: 3 (tres aristas: A-B, B-C, C-D)
     *
     * IMPORTANTE: No buscamos el camino MÁS CORTO, solo UN camino válido.
     *
     * ¿CÓMO FUNCIONA?
     * Usamos búsqueda en profundidad (DFS) con una pila:
     * 1. Empezamos en el nodo origen con profundidad 0
     * 2. Visitamos vecinos y aumentamos profundidad en 1
     * 3. Si encontramos el destino, retornamos la profundidad
     * 4. Si no hay camino, retornamos -1
     */
    public int pathLength(int originIdx, int targetIdx) {
        incr("pathLength");

        // Validar índices
        if (!validIndex(originIdx) || !validIndex(targetIdx))
            return -1;

        // Caso especial: mismo nodo
        if (originIdx == targetIdx)
            return 0;

        boolean[] visitado = new boolean[maxV];

        // Pila para DFS: guarda pares [nodo, profundidad]
        Deque<int[]> pila = new ArrayDeque<>();
        pila.push(new int[] { originIdx, 0 });

        while (!pila.isEmpty()) {
            int[] actual = pila.pop();
            int nodoActual = actual[0];
            int profundidad = actual[1];

            // Si ya lo visitamos, saltar
            if (visitado[nodoActual])
                continue;
            visitado[nodoActual] = true;

            // ¿Llegamos al destino?
            if (nodoActual == targetIdx) {
                return profundidad; // esta es la longitud del camino
            }

            // Agregar todos los vecinos a la pila
            List<Integer> vecinos = vertices[nodoActual].head.toList();
            for (int vecino : vecinos) {
                if (!visitado[vecino]) {
                    pila.push(new int[] { vecino, profundidad + 1 });
                }
            }
        }

        return -1; // no hay camino entre origen y destino
    }

    // PREGUNTA 2d: Crear grafo desde mapa de adyacencias

    /**
     * Construye el grafo a partir de un mapa de listas de adyacencia.
     *
     * FORMATO DE ENTRADA:
     * Map donde:
     * - Clave: valor del vértice
     * - Valor: lista de valores de vértices adyacentes
     *
     * EJEMPLO:
     * {
     * 10: [20, 30],
     * 20: [10, 40],
     * 30: [10],
     * 40: [20]
     * }
     *
     * Esto crea el grafo:
     * 10 --- 20 --- 40
     * |
     * 30
     */
    public Map<Integer, Integer> buildFromAdjacencyMap(Map<Integer, List<Integer>> adj) {
        incr("buildFromAdjacencyMap");
        Map<Integer, Integer> valorAIndice = new HashMap<>();

        // PASO 1: Crear todos los vértices mencionados
        for (Integer clave : adj.keySet()) {
            if (!valorAIndice.containsKey(clave)) {
                int idx = addVertex(clave);
                valorAIndice.put(clave, idx);
            }
            // También crear vértices de los adyacentes
            for (Integer vecino : adj.get(clave)) {
                if (!valorAIndice.containsKey(vecino)) {
                    int idx = addVertex(vecino);
                    valorAIndice.put(vecino, idx);
                }
            }
        }

        // PASO 2: Crear todas las aristas
        for (Map.Entry<Integer, List<Integer>> entrada : adj.entrySet()) {
            int idxU = valorAIndice.get(entrada.getKey());
            for (int valorV : entrada.getValue()) {
                int idxV = valorAIndice.get(valorV);
                addEdge(idxU, idxV);
            }
        }

        return valorAIndice;
    }

    /**
     * Retorna el mapa de contadores de frecuencia.
     * Útil para analizar el rendimiento de las operaciones.
     */
    public Map<String, Integer> getFreq() {
        return new HashMap<>(freq);
    }

    /**
     * Imprime el grafo en formato legible.
     * Muestra cada vértice y sus conexiones.
     */
    public void printGraph() {
        System.out.println("\n--- Estructura del Grafo ---");
        System.out.println("Formato: Índice : Valor → [vecinos...]");
        System.out.println("-------------------------------");

        for (int i = 0; i < maxV; i++) {
            if (vertices[i] != null && vertices[i].active) {
                System.out.println("  " + i + " : " + vertices[i].info +
                        " → " + vertices[i].head.toList());
            }
        }
        System.out.println("-------------------------------");
    }
}
