package edu.universidad.tarea3.fuerzaBrutaCajaFuerte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Generador de combinaciones (n choose k) sin repetición, orden no importa.
 * Para dígitos 0..9 y k=4, número de combinaciones: C(10,4) = 210.
 *
 * Contador de frecuencia: 'attempts' incrementa por cada combinación generada (peor caso -> 210).
 * Complejidad: O(C(n,k) * k) para generar (cada combinación se construye en k pasos).
 */
public class CombinationGenerator {
    private int n;
    private int k;
    private long attempts;
    private Map<String,Integer> freq;

    public CombinationGenerator(int n, int k) {
        this.n = n;
        this.k = k;
        this.attempts = 0;
        this.freq = new HashMap<>();
    }

    private void incr(String key) { freq.put(key, freq.getOrDefault(key,0)+1); }

    public List<int[]> generateAll() {
        incr("generateAll");
        List<int[]> res = new ArrayList<>();
        int[] comb = new int[k];
        backtrack(0, 0, comb, res);
        return res;
    }

    private void backtrack(int start, int depth, int[] comb, List<int[]> res) {
        if (depth == k) {
            attempts++;
            res.add(comb.clone());
            return;
        }
        for (int i = start; i < n; i++) {
            comb[depth] = i;
            backtrack(i + 1, depth + 1, comb, res);
        }
    }

    public long getAttempts() { return attempts; }
    public Map<String,Integer> getFreq() { return new HashMap<>(freq); }

    // calculo combinatorio (exacto)
    public static long binomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k > n - k) k = n - k;
        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - k + i) / i;
        }
        return res;
    }
}
