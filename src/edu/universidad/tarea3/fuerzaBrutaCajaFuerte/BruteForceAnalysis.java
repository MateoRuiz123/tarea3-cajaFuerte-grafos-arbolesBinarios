package edu.universidad.tarea3.fuerzaBrutaCajaFuerte;

import java.util.List;

/**
 * Clase que consolida el análisis de los 3 escenarios para la caja fuerte de 4 dígitos (0-9).
 *
 * Muestra:
 * - número exacto de intentos peor caso
 * - contador de frecuencia (attempts)
 * - comentario de orden de magnitud (Big-O)
 */
public class BruteForceAnalysis {
    private static final int N = 10; // dígitos 0..9
    private static final int K = 4;

    public void runAll() {
        System.out.println("=== Fuerza bruta: caja de 4 dígitos (0..9) ===");

        // Escenario 1
        CombinationGenerator cg = new CombinationGenerator(N, K);
        List<int[]> combs = cg.generateAll();
        long combsExpected = CombinationGenerator.binomial(N, K); // 210
        System.out.println("Escenario 1 (combinación, sin repetición, orden NO importa):");
        System.out.println(" - combinaciones generadas (peor caso): " + combs.size() + " (C(10,4) = " + combsExpected + ")");
        System.out.println(" - contador attempts (peor caso): " + cg.getAttempts());
        System.out.println(" - orden de magnitud: O(C(n,k) * k) ≈ O(210) -> constante (para n=10,k=4) pero crece combinatoriamente.");
        System.out.println();

        // Escenario 2
        PermutationGenerator pg = new PermutationGenerator(N, K);
        List<int[]> perms = pg.generateAll();
        long permsExpected = PermutationGenerator.permutations(N, K); // 5040
        System.out.println("Escenario 2 (permutación, sin repetición, orden SÍ importa):");
        System.out.println(" - permutaciones generadas (peor caso): " + perms.size() + " (P(10,4) = " + permsExpected + ")");
        System.out.println(" - contador attempts (peor caso): " + pg.getAttempts());
        System.out.println(" - orden de magnitud: O(P(n,k) * k) ≈ O(5040).");
        System.out.println();

        // Escenario 3
        SequenceGenerator sg = new SequenceGenerator(N, K);
        List<int[]> seqs = sg.generateAll();
        long seqsExpected = (long) Math.pow(N, K); // 10000
        System.out.println("Escenario 3 (secuencia, repetición permitida, orden SÍ importa):");
        System.out.println(" - secuencias generadas (peor caso): " + seqs.size() + " (n^k = " + seqsExpected + ")");
        System.out.println(" - contador attempts (peor caso): " + sg.getAttempts());
        System.out.println(" - orden de magnitud: O(n^k * k) ≈ O(10^4).");
        System.out.println();

        // resumen
        System.out.println("Resumen (peor caso):");
        System.out.println(" - Escenario 1: 210 intentos (C(10,4))");
        System.out.println(" - Escenario 2: 5040 intentos (10*9*8*7)");
        System.out.println(" - Escenario 3: 10000 intentos (10^4)");
    }
}
