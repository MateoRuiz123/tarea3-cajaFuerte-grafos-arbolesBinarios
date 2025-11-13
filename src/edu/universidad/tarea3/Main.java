package edu.universidad.tarea3;

import edu.universidad.tarea3.fuerzaBrutaCajaFuerte.BruteForceAnalysis;
import edu.universidad.tarea3.grafos.Graphs;
import edu.universidad.tarea3.arbolesBinarios.BinarySearchTree;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("      TAREA 3 - MENÚ PRINCIPAL");
            System.out.println("========================================");
            System.out.println("1. Fuerza bruta - caja fuerte 4 dígitos");
            System.out.println("2. Grafos - operaciones con listas de adyacencia");
            System.out.println("3. Árbol binario - búsqueda y recorrido");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            String opcion = sc.nextLine().trim();

            if (opcion.equals("0"))
                break;

            switch (opcion) {
                case "1" -> menuFuerzaBruta();
                case "2" -> menuGrafos();
                case "3" -> menuArboles();
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    // * PROBLEMA 1: Análisis de fuerza bruta para caja fuerte
    private static void menuFuerzaBruta() {
        System.out.println("\n--- PROBLEMA 1: Fuerza Bruta");
        System.out.println("Analizaremos cuántos intentos necesita un atacante");
        System.out.println("para abrir una caja fuerte de 4 dígitos (0-9)");
        System.out.println("\n¿Continuar? (s/n): ");

        String respuesta = sc.nextLine().trim().toLowerCase();
        if (respuesta.equals("s") || respuesta.equals("si")) {
            new BruteForceAnalysis().runAll();
        } else {
            System.out.println("Operación cancelada.");
        }

        esperarEnter();
    }

    // * PROBLEMA 2: Operaciones con grafos
    private static void menuGrafos() {
        System.out.println("\n--- PROBLEMA 2: Grafos ---");

        // Crear el grafo con capacidad suficiente
        Graphs grafo = new Graphs(20);
        Map<Integer, Integer> valorAIndice = new HashMap<>(); // mapeo de valor -> índice
        int primerNodo = -1; // para recordar el origen

        while (true) {
            System.out.println("\n--- Menú de Grafos ---");
            System.out.println("1. Agregar vértice");
            System.out.println("2. Agregar arista (conexión entre dos vértices)");
            System.out.println("3. Ver grafo actual");
            System.out.println("4. Encontrar nodo con más conexiones");
            System.out.println("5. Eliminar un nodo");
            System.out.println("6. Calcular longitud de camino");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

            String op = sc.nextLine().trim();

            if (op.equals("0"))
                break;

            switch (op) {
                case "1" -> {
                    // Agregar vértice
                    System.out.print("Ingrese el valor del vértice (número entero): ");
                    try {
                        int valor = Integer.parseInt(sc.nextLine().trim());

                        // Verificar si ya existe
                        if (valorAIndice.containsKey(valor)) {
                            System.out.println("Ese valor ya existe en el grafo.");
                        } else {
                            int indice = grafo.addVertex(valor);
                            if (indice == -1) {
                                System.out.println("El grafo está lleno.");
                            } else {
                                valorAIndice.put(valor, indice);
                                if (primerNodo == -1)
                                    primerNodo = indice;
                                System.out.println("Vértice " + valor + " agregado correctamente.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                case "2" -> {
                    // Agregar arista
                    if (valorAIndice.size() < 2) {
                        System.out.println("Necesita al menos 2 vértices para crear una arista.");
                    } else {
                        System.out.println("Vértices disponibles: " + valorAIndice.keySet());
                        System.out.print("Ingrese el primer vértice: ");
                        try {
                            int v1 = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Ingrese el segundo vértice: ");
                            int v2 = Integer.parseInt(sc.nextLine().trim());

                            if (!valorAIndice.containsKey(v1) || !valorAIndice.containsKey(v2)) {
                                System.out.println("Uno o ambos vértices no existen.");
                            } else if (v1 == v2) {
                                System.out.println("No puede conectar un vértice consigo mismo.");
                            } else {
                                boolean ok = grafo.addEdge(valorAIndice.get(v1), valorAIndice.get(v2));
                                if (ok) {
                                    System.out.println("Arista entre " + v1 + " y " + v2 + " creada.");
                                } else {
                                    System.out.println("No se pudo crear la arista.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar números válidos.");
                        }
                    }
                }

                case "3" -> {
                    // Ver grafo
                    System.out.println("\n--- Estado actual del grafo ---");
                    grafo.printGraph();
                }

                case "4" -> {
                    // Nodo con más conexiones
                    if (valorAIndice.isEmpty()) {
                        System.out.println("El grafo está vacío.");
                    } else {
                        System.out.println("\nBuscando el nodo con más conexiones...");
                        int idx = grafo.nodeWithMaxAdjacents();
                        if (idx == -1) {
                            System.out.println("No hay nodos en el grafo.");
                        } else {
                            System.out.println("El nodo con más conexiones es el índice " + idx);
                            System.out.println("  (Este nodo tiene el mayor número de vecinos)");
                        }
                    }
                }

                case "5" -> {
                    // Eliminar nodo
                    if (valorAIndice.isEmpty()) {
                        System.out.println("El grafo está vacío.");
                    } else {
                        System.out.println("Vértices disponibles: " + valorAIndice.keySet());
                        System.out.print("Ingrese el vértice a eliminar: ");
                        try {
                            int valor = Integer.parseInt(sc.nextLine().trim());
                            if (!valorAIndice.containsKey(valor)) {
                                System.out.println("Ese vértice no existe.");
                            } else {
                                int idx = valorAIndice.get(valor);
                                boolean ok = grafo.removeNode(idx);
                                if (ok) {
                                    valorAIndice.remove(valor);
                                    System.out.println("Vértice " + valor + " eliminado.");
                                    System.out.println("  (Todas sus conexiones también fueron removidas)");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar un número válido.");
                        }
                    }
                }

                case "6" -> {
                    // Longitud de camino
                    if (valorAIndice.size() < 2) {
                        System.out.println("Necesita al menos 2 vértices.");
                    } else {
                        System.out.println("Vértices disponibles: " + valorAIndice.keySet());
                        System.out.print("Vértice origen: ");
                        try {
                            int origen = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Vértice destino: ");
                            int destino = Integer.parseInt(sc.nextLine().trim());

                            if (!valorAIndice.containsKey(origen) || !valorAIndice.containsKey(destino)) {
                                System.out.println("Uno o ambos vértices no existen.");
                            } else {
                                int longitud = grafo.pathLength(
                                        valorAIndice.get(origen),
                                        valorAIndice.get(destino));
                                if (longitud == -1) {
                                    System.out.println("✗ No existe camino entre " + origen + " y " + destino);
                                } else {
                                    System.out.println("Longitud del camino: " + longitud + " aristas");
                                    System.out.println(
                                            "  (Esto significa que hay " + longitud + " conexiones entre ambos nodos)");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar números válidos.");
                        }
                    }
                }

                default -> System.out.println("Opción inválida.");
            }
        }

        // Mostrar estadísticas finales
        System.out.println("\n--- Estadísticas del grafo ---");
        System.out.println("Operaciones realizadas: " + grafo.getFreq());
    }

    // * PROBLEMA 3: Análisis de algoritmos en árboles binarios
    private static void menuArboles() {
        System.out.println("\n--- PROBLEMA 3: Árboles Binarios ---");
        System.out.println("Crearemos un árbol binario de búsqueda.\n");

        BinarySearchTree arbol = new BinarySearchTree();

        while (true) {
            System.out.println("\n--- Menú de Árboles ---");
            System.out.println("1. Insertar valor");
            System.out.println("2. Buscar valor (muestra contador de visitas)");
            System.out.println("3. Recorrido postorden (muestra contador)");
            System.out.println("4. Ver estadísticas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Opción: ");

            String op = sc.nextLine().trim();

            if (op.equals("0"))
                break;

            switch (op) {
                case "1" -> {
                    // Insertar
                    System.out.print("Ingrese el valor a insertar: ");
                    try {
                        int valor = Integer.parseInt(sc.nextLine().trim());
                        arbol.insert(valor);
                        System.out.println("Valor " + valor + " insertado.");
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                case "2" -> {
                    // Buscar
                    if (arbol.root == null) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        System.out.print("Ingrese el valor a buscar: ");
                        try {
                            int valor = Integer.parseInt(sc.nextLine().trim());
                            System.out.println("\nIniciando búsqueda...");

                            // Limpiar contador anterior
                            arbol.resetFreq();
                            boolean encontrado = arbol.searchBinary(arbol.root, valor);

                            Map<String, Integer> stats = arbol.getFreq();
                            System.out.println("\n--- Análisis de la búsqueda ---");
                            System.out.println("Resultado: " + (encontrado ? "ENCONTRADO" : "NO ENCONTRADO"));
                            System.out.println("Nodos visitados: " + stats.getOrDefault("searchBinaryVisits", 0));
                            System.out.println("\nExplicación: En un árbol binario de búsqueda,");
                            System.out.println("en cada paso descartamos la mitad del árbol.");
                            System.out.println("Por eso es eficiente: O(log n) en promedio.");

                        } catch (NumberFormatException e) {
                            System.out.println("Debe ingresar un número válido.");
                        }
                    }
                }

                case "3" -> {
                    // Postorden
                    if (arbol.root == null) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        System.out.println("\nRecorrido postorden (izquierda-derecha-raíz):");
                        arbol.resetFreq();
                        arbol.traversePostorder(arbol.root);

                        Map<String, Integer> stats = arbol.getFreq();
                        System.out.println("\n--- Análisis del recorrido ---");
                        System.out.println("Nodos visitados: " + stats.getOrDefault("postorderVisits", 0));
                        System.out.println("\nExplicación: El recorrido postorden visita TODOS");
                        System.out.println("los nodos exactamente una vez: O(n)");
                    }
                }

                case "4" -> {
                    // Estadísticas
                    System.out.println("\n--- Estadísticas del árbol ---");
                    System.out.println(arbol.getFreq());
                }

                default -> System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Utilidad para pausar la ejecución hasta que el usuario presione Enter
     */
    private static void esperarEnter() {
        System.out.println("\nPresione Enter para continuar...");
        sc.nextLine();
    }
}
