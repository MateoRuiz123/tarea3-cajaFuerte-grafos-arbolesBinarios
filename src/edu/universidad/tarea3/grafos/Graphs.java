package edu.universidad.tarea3.grafos;

import edu.universidad.tarea3.estructuras.DoublyLinkedList;

import java.util.*;

/**
 * Implementa grafo con vector de Vertex {info, head} y listas de adyacencia (LDL).
 *
 * Se asume grafo no dirigido (si deseas dirigido, ajusta addEdge).
 *
 * Contadores de frecuencia: map 'freq' que incrementa en operaciones importantes.
 *
 * Complejidad (resumen junto a cada método).
 */
public class Graphs {
    private Vertex[] vertices; // vector de objetos {info, head}
    private int maxV;
    private int count; // cantidad de vértices activos
    private Map<String, Integer> freq;

    public Graphs(int maxVertices) {
        this.maxV = maxVertices;
        this.vertices = new Vertex[maxV];
        this.count = 0;
        this.freq = new HashMap<>();
    }

    private void incr(String k) { freq.put(k, freq.getOrDefault(k,0)+1); }

    // agrega un vértice con etiqueta 'info'. Retorna índice o -1 si lleno.
    // Complejidad: O(1)
    public int addVertex(int info) {
        incr("addVertex");
        for (int i = 0; i < maxV; i++) {
            if (vertices[i] == null || !vertices[i].active) {
                vertices[i] = new Vertex(info);
                count++;
                return i;
            }
        }
        return -1;
    }

    // agrega arista no dirigida entre índices u y v (si existen y activos)
    // Complejidad: O(1) para addFront
    public boolean addEdge(int u, int v) {
        incr("addEdge");
        if (!validIndex(u) || !validIndex(v) || u == v) return false;
        // evitar duplicados
        if (!vertices[u].head.contains(v)) vertices[u].head.addFront(v);
        if (!vertices[v].head.contains(u)) vertices[v].head.addFront(u);
        return true;
    }

    private boolean validIndex(int i) {
        return i >= 0 && i < maxV && vertices[i] != null && vertices[i].active;
    }

    // a) Indique cual es el nodo con mayor número de nodos adyacentes
    // Complejidad: O(V + E) (recorre todos los vértices y suma tamaños)
    public int nodeWithMaxAdjacents() {
        incr("nodeWithMaxAdjacents");
        int bestIdx = -1;
        int bestSize = -1;
        for (int i = 0; i < maxV; i++) {
            if (validIndex(i)) {
                int s = vertices[i].head.size();
                if (s > bestSize) {
                    bestSize = s;
                    bestIdx = i;
                }
            }
        }
        return bestIdx; // retorna índice en vector
    }

    // b) Elimine un nodo (marca inactive y elimina apariciones en adyacencias)
    // Complejidad: O(V + E) (hay que recorrer todas las listas y quitar referencias)
    public boolean removeNode(int idx) {
        incr("removeNode");
        if (!validIndex(idx)) return false;
        // quitar referencias en otras listas
        for (int i = 0; i < maxV; i++) {
            if (validIndex(i) && i != idx) {
                // remueve idx de la lista de adyacencia de i (O(deg(i)))
                vertices[i].head.remove(idx);
            }
        }
        // desactivar el vértice
        vertices[idx].active = false;
        vertices[idx].head = new DoublyLinkedList(); // liberar lista
        count--;
        return true;
    }

    // c) Indique la "longitud de un camino" entre origen (primer nodo creado) y un nodo dado
    //    No pide más corto; devolvemos la longitud del primer camino encontrado mediante DFS.
    //    Complejidad: O(V + E)
    public int pathLength(int originIdx, int targetIdx) {
        incr("pathLength");
        if (!validIndex(originIdx) || !validIndex(targetIdx)) return -1;
        boolean[] visited = new boolean[maxV];
        // DFS iterativa con stack que guarda pair (node, depth)
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{originIdx, 0});
        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int cur = p[0], depth = p[1];
            if (visited[cur]) continue;
            visited[cur] = true;
            if (cur == targetIdx) return depth;
            // empujar adyacentes (no importa orden)
            List<Integer> adj = vertices[cur].head.toList();
            for (int v : adj) {
                if (!visited[v]) {
                    stack.push(new int[]{v, depth + 1});
                }
            }
        }
        return -1; // no hay camino
    }

    // d) Cree el grafo a partir de una representación de listas de adyacencia:
    //    se recibe Map<info, List<infoAdj>>; retorna mapping de info->indice en vector.
    //    Complejidad: O(V + E)
    public Map<Integer,Integer> buildFromAdjacencyMap(Map<Integer, List<Integer>> adj) {
        incr("buildFromAdjacencyMap");
        Map<Integer,Integer> infoToIdx = new HashMap<>();
        // primer: crear vertices por cada key y nodos mencionados en listas
        for (Integer key : adj.keySet()) {
            if (!infoToIdx.containsKey(key)) {
                int idx = addVertex(key);
                infoToIdx.put(key, idx);
            }
            for (Integer v : adj.get(key)) {
                if (!infoToIdx.containsKey(v)) {
                    int idx = addVertex(v);
                    infoToIdx.put(v, idx);
                }
            }
        }
        // ahora agregar aristas (si falta capacidad, podría retornar error simple)
        for (Map.Entry<Integer, List<Integer>> e : adj.entrySet()) {
            int uIdx = infoToIdx.get(e.getKey());
            for (int vInfo : e.getValue()) {
                int vIdx = infoToIdx.get(vInfo);
                addEdge(uIdx, vIdx);
            }
        }
        return infoToIdx;
    }

    public Map<String,Integer> getFreq() {
        return new HashMap<>(freq);
    }

    // para debug: mostrar grafo en términos de indices y adyacencias
    public void printGraph() {
        System.out.println("Grafo (index : info) -> [adyacentes index...]");
        for (int i = 0; i < maxV; i++) {
            if (vertices[i] != null && vertices[i].active) {
                System.out.println(i + " : " + vertices[i].info + " -> " + vertices[i].head.toList());
            }
        }
    }
}
