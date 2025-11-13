package edu.universidad.tarea3.fuerzaBrutaCajaFuerte;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Generador de permutaciones sin repetición (nPk).
 * Para n=10,k=4 -> 10*9*8*7 = 5040.
 *
 * Contador: attempts (nPk).
 * Complejidad: O(P(n,k)*k)
 */
public class PermutationGenerator {
    private int n;
    private int k;
    private long attempts;
    private Map<String,Integer> freq;

    public PermutationGenerator(int n, int k) {
        this.n = n; this.k = k; this.attempts = 0; this.freq = new HashMap<>();
    }

    private void incr(String key) { freq.put(key, freq.getOrDefault(key,0)+1); }

    public List<int[]> generateAll() {
        incr("generateAll");
        List<int[]> res = new ArrayList<>();
        boolean[] used = new boolean[n];
        int[] perm = new int[k];
        backtrack(0, used, perm, res);
        return res;
    }

    private void backtrack(int depth, boolean[] used, int[] perm, List<int[]> res) {
        if (depth == k) {
            attempts++;
            res.add(perm.clone());
            return;
        }
        for (int i = 0; i < n; i++) {
            if (used[i]) continue;
            used[i] = true;
            perm[depth] = i;
            backtrack(depth + 1, used, perm, res);
            used[i] = false;
        }
    }

    public long getAttempts() { return attempts; }
    public Map<String,Integer> getFreq() { return new HashMap<>(freq); }

    public static long permutations(int n, int k) {
        long p = 1;
        for (int i = 0; i < k; i++) p *= (n - i);
        return p;
    }
}
