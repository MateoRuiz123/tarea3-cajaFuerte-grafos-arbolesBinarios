package edu.universidad.tarea3.fuerzaBrutaCajaFuerte;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Generador de secuencias con repetición (n^k).
 * Para n=10,k=4 -> 10^4 = 10000.
 *
 * Contador: attempts
 * Complejidad: O(n^k * k)
 */
public class SequenceGenerator {
    private int n;
    private int k;
    private long attempts;
    private Map<String,Integer> freq;

    public SequenceGenerator(int n, int k) {
        this.n = n; this.k = k; this.attempts = 0; this.freq = new HashMap<>();
    }

    private void incr(String key) { freq.put(key, freq.getOrDefault(key,0)+1); }

    public List<int[]> generateAll() {
        incr("generateAll");
        List<int[]> res = new ArrayList<>();
        int[] buf = new int[k];
        backtrack(0, buf, res);
        return res;
    }

    private void backtrack(int depth, int[] buf, List<int[]> res) {
        if (depth == k) {
            attempts++;
            res.add(buf.clone());
            return;
        }
        for (int i = 0; i < n; i++) {
            buf[depth] = i;
            backtrack(depth + 1, buf, res);
        }
    }

    public long getAttempts() { return attempts; }
    public Map<String,Integer> getFreq() { return new HashMap<>(freq); }
}
