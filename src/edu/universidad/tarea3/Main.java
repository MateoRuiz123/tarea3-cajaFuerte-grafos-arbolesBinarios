package edu.universidad.tarea3;

import edu.universidad.tarea3.fuerzaBrutaCajaFuerte.BruteForceAnalysis;
import edu.universidad.tarea3.grafos.Graphs;
import java.util.*;

/**
 * Menú principal para ejecutar los tres grandes bloques:
 * 1) Fuerza bruta (3 escenarios)
 * 2) Grafos (construcción por listas de adyacencia LDL + métodos solicitados)
 * 3) Árbol / búsquedas (searchBinary y postorder) - demo y frecuencias
 *
 * Pégalo en src y ejecuta desde IntelliJ.
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- TAREA 3 - Menú ---");
            System.out.println("1) Fuerza bruta - caja 4 dígitos");
            System.out.println("2) Grafos - crear, nodo con más adyacentes, eliminar, longitud de camino");
            System.out.println("3) Árbol binario - demo búsqueda y postorden (muestra contadores)");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String s = sc.nextLine().trim();
            if (s.equals("0")) { System.out.println("Saliendo..."); break; }
            switch (s) {
                case "1" -> {
                    new BruteForceAnalysis().runAll();
                }
                case "2" -> {
                    demoGrafos();
                }
                case "3" -> {
                    demoArboles();
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void demoGrafos() {
        System.out.println("Demo grafos: construimos un grafo de ejemplo (5 vértices).");
        Graphs g = new Graphs(10); // capacidad para 10 vertices
        // construcción simple: info etiquetada 10,20,30...
        int a = g.addVertex(10);
        int b = g.addVertex(20);
        int c = g.addVertex(30);
        int d = g.addVertex(40);
        int e = g.addVertex(50);
        g.addEdge(a,b); g.addEdge(a,c); g.addEdge(b,c); g.addEdge(c,d); g.addEdge(d,e);

        g.printGraph();
        int mx = g.nodeWithMaxAdjacents();
        System.out.println("Nodo con mayor adyacencia: idx=" + mx);
        System.out.println("Probamos pathLength desde origen " + a + " hasta " + e + " : longitud = " + g.pathLength(a,e));
        System.out.println("Eliminar nodo idx " + c + " ...");
        g.removeNode(c);
        g.printGraph();
        System.out.println("Freq grafos: " + g.getFreq());
    }

    private static void demoArboles() {
        System.out.println("Demo árbol: insertamos valores y probamos search y postorden.");
        edu.universidad.tarea3.arbolesBinarios.BinarySearchTree bst = new edu.universidad.tarea3.arbolesBinarios.BinarySearchTree();
        int[] vals = {50, 30, 70, 20, 40, 60, 80};
        for (int v : vals) bst.insert(v);
        // búsqueda
        boolean found = bst.searchBinary(bst.root, 60);
        System.out.println("Encontrado 60?: " + found);
        // postorden
        System.out.println("Recorrido postorden:");
        bst.traversePostorder(bst.root);
        System.out.println("Freq árbol: " + bst.getFreq());
    }
}
