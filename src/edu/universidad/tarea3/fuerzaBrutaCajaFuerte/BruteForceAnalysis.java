package edu.universidad.tarea3.fuerzaBrutaCajaFuerte;

import java.util.List;

/**
 * PROBLEMA 1: Análisis de fuerza bruta para caja fuerte de 4 dígitos
 *
 * Esta clase analiza cuántos intentos necesita un atacante para abrir
 * una caja fuerte con clave de 4 dígitos (0-9) en tres escenarios diferentes.
 *
 * CONCEPTOS:
 * - Combinación: El orden NO importa {1,2,3,4} = {4,3,2,1}
 * - Permutación: El orden SÍ importa, 1234 ≠ 4321
 * - Secuencia: Permite repeticiones como 1111, 2222, etc.
 */
public class BruteForceAnalysis {
    private static final int N = 10; // dígitos disponibles (0 al 9)
    private static final int K = 4;  // longitud de la clave

    public void runAll() {
        imprimirEncabezado();

        // ========== ESCENARIO 1: COMBINACIÓN ==========
        System.out.println("\n------ ESCENARIO 1: Combinación (sin orden, sin repetición) ------");

        System.out.println("\nEl ORDEN de los dígitos NO importa");
        System.out.println("Los dígitos NO pueden repetirse");
        System.out.println("\nEJEMPLOS:");
        System.out.println("  Si la clave es {1, 2, 3, 4}");
        System.out.println("  También funciona: {4, 3, 2, 1} o {2, 4, 1, 3}");
        System.out.println("  Todas son LA MISMA combinación");

        CombinationGenerator cg = new CombinationGenerator(N, K);
        List<int[]> combs = cg.generateAll();
        long combsEsperadas = CombinationGenerator.binomial(N, K);

        System.out.println("\n--- RESULTADOS");
        System.out.println("Combinaciones generadas: " + combs.size());
        System.out.println("Fórmula matemática: C(10,4) = " + combsEsperadas);
        System.out.println("Contador de intentos (peor caso): " + cg.getAttempts());
        System.out.println("\nOrden de magnitud: O(C(n,k) × k)");
        System.out.println("  Para n=10, k=4 → O(210)");
        System.out.println("  Es el método MÁS EFICIENTE de los tres.");

        mostrarEjemplosCombinaciones(combs);

        // ========== ESCENARIO 2: PERMUTACIÓN ==========
        System.out.println("\n\n------ ESCENARIO 2: Permutación (con orden, sin repetición) ------");

        System.out.println("\nEl ORDEN de los dígitos SÍ importa");
        System.out.println("Los dígitos NO pueden repetirse");
        System.out.println("\nEJEMPLOS:");
        System.out.println("  Si la clave es 1234");
        System.out.println("  4321 es DIFERENTE (orden distinto)");
        System.out.println("  1432 es DIFERENTE (orden distinto)");

        PermutationGenerator pg = new PermutationGenerator(N, K);
        List<int[]> perms = pg.generateAll();
        long permsEsperadas = PermutationGenerator.permutations(N, K);

        System.out.println("\n--- RESULTADOS");
        System.out.println("Permutaciones generadas: " + perms.size());
        System.out.println("Fórmula matemática: P(10,4) = 10×9×8×7 = " + permsEsperadas);
        System.out.println("Contador de intentos (peor caso): " + pg.getAttempts());
        System.out.println("\nOrden de magnitud: O(P(n,k) × k)");
        System.out.println("  Para n=10, k=4 → O(5,040)");
        System.out.println("  Más intentos que combinación, menos que secuencia.");

        mostrarEjemplosPermutaciones(perms);

        // ========== ESCENARIO 3: SECUENCIA ==========
        System.out.println("\n\n------ ESCENARIO 3: Secuencia (con orden, con repetición) ------");

        System.out.println("\nEl ORDEN de los dígitos SÍ importa");
        System.out.println("Los dígitos SÍ pueden repetirse");
        System.out.println("Este es el caso REAL de las cajas fuertes");
        System.out.println("\nEJEMPLOS:");
        System.out.println("  TODAS estas claves son VÁLIDAS:");
        System.out.println("  1234 -> todos diferentes");
        System.out.println("  1111 -> todos iguales");
        System.out.println("  1234 != 4321 → el orden importa");
        System.out.println("  1223 -> con repeticiones");
        System.out.println("  0000 -> todos ceros");

        SequenceGenerator sg = new SequenceGenerator(N, K);
        List<int[]> seqs = sg.generateAll();
        long seqsEsperadas = (long) Math.pow(N, K);

        System.out.println("\n--- RESULTADOS");
        System.out.println("Secuencias generadas: " + seqs.size());
        System.out.println("Fórmula matemática: n^k = 10^4 = " + seqsEsperadas);
        System.out.println("Contador de intentos (peor caso): " + sg.getAttempts());
        System.out.println("\nOrden de magnitud: O(n^k × k)");
        System.out.println("  Para n=10, k=4 → O(10,000)");
        System.out.println("  Es el método con MÁS intentos posibles.");

        mostrarEjemplosSecuencias(seqs);

        // ========== RESUMEN
        imprimirResumen(combsEsperadas, permsEsperadas, seqsEsperadas);
    }

    private void imprimirEncabezado() {

        System.out.println("------ ANÁLISIS DE FUERZA BRUTA: CAJA FUERTE 4 DÍGITOS ------");
        System.out.println("\nSituación: Un atacante intenta abrir una caja fuerte");
        System.out.println("probando TODAS las combinaciones posibles (fuerza bruta).");
        System.out.println("\nDatos:");
        System.out.println("  Dígitos disponibles: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9");
        System.out.println("  Longitud de la clave: 4 dígitos");}

    private void mostrarEjemplosCombinaciones(List<int[]> combs) {
        System.out.println("\nPRIMERAS 5 COMBINACIONES generadas:");
        for (int i = 0; i < Math.min(5, combs.size()); i++) {
            System.out.print("  " + (i+1) + ". {");
            for (int j = 0; j < combs.get(i).length; j++) {
                System.out.print(combs.get(i)[j]);
                if (j < combs.get(i).length - 1) System.out.print(", ");
            }
            System.out.println("}");
        }
        System.out.println("  ... (total: " + combs.size() + " combinaciones)");
    }

    private void mostrarEjemplosPermutaciones(List<int[]> perms) {
        System.out.println("\nPRIMERAS 5 PERMUTACIONES generadas:");
        for (int i = 0; i < Math.min(5, perms.size()); i++) {
            System.out.print("  " + (i+1) + ". ");
            for (int d : perms.get(i)) {
                System.out.print(d);
            }
            System.out.println();
        }
        System.out.println("  ... (total: " + perms.size() + " permutaciones)");

        // Mostrar cómo cambia el orden
        System.out.println("\nNota: Observe cómo el ORDEN es importante:");
        System.out.println("  0123, 0132, 0213, 0231, 0312, 0321 -> todos DIFERENTES");
    }

    private void mostrarEjemplosSecuencias(List<int[]> seqs) {
        System.out.println("\nPRIMERAS 10 SECUENCIAS generadas:");
        for (int i = 0; i < Math.min(10, seqs.size()); i++) {
            System.out.print("  " + (i+1) + ". ");
            for (int d : seqs.get(i)) {
                System.out.print(d);
            }
            System.out.println();
        }
        System.out.println("  ...");

        // Mostrar casos especiales
        System.out.println("\nCasos ESPECIALES que SÍ son válidos aquí:");
        System.out.println("  0000, 1111, 2222, ..., 9999 -> todos repetidos");
        System.out.println("  1234, 4321 -> mismo dígitos, orden diferente");
        System.out.println("  1223, 5577 -> con algunas repeticiones");
    }

    private void imprimirResumen(long combs, long perms, long seqs) {

        System.out.println("\n\n    ============== RESUMEN COMPARATIVO ==============");

        System.out.println("\n┌─────────────────────────┬──────────────┬─────────────────┐");
        System.out.println("│ Escenario               │ Intentos     │ Complejidad     │");
        System.out.println("├─────────────────────────┼──────────────┼─────────────────┤");
        System.out.printf("│ 1. Combinación          │ %,12d │ O(210)          │%n", combs);
        System.out.printf("│ 2. Permutación          │ %,12d │ O(5,040)        │%n", perms);
        System.out.printf("│ 3. Secuencia (REAL)     │ %,12d │ O(10,000)       │%n", seqs);
        System.out.println("└─────────────────────────┴──────────────┴─────────────────┘");

        System.out.println("\n¿Cuál escenario requiere MÁS intentos?");
        System.out.println("-> Escenario 3 (secuencia): " + String.format("%,d", seqs) + " intentos");
        System.out.println("  Este es el caso REAL de las cajas fuertes.");

        System.out.println("\n¿Cuál escenario requiere MENOS intentos?");
        System.out.println("-> Escenario 1 (combinación): " + String.format("%,d", combs) + " intentos");
        System.out.println("  Pero este NO es realista para cajas reales.");
    }
}