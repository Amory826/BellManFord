import java.util.Arrays;

public class BellManFord_new {
    private int n;
    private int[][] graph;
    private int[][] dp;
    private int[][] path;
    
    public BellManFord_new(int n, int[][] graph) {
        this.n = n;
        this.graph = graph;
        this.dp = new int[1 << n][n];
        this.path = new int[1 << n][n];
    }
    
    public int tsp() {
        int full = (1 << n) - 1;
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        dp[1][0] = 0;
        for (int s = 1; s <= full; s++) {
            for (int i = 0; i < n; i++) {
                if ((s & (1 << i)) == 0) continue;
                for (int j = 0; j < n; j++) {
                    if (i == j || (s & (1 << j)) == 0 || graph[j][i] == 0) continue;
                    int newDist = dp[s ^ (1 << i)][j] + graph[j][i];
                    if (newDist < dp[s][i]) {
                        dp[s][i] = newDist;
                        path[s][i] = j;
                    }
                }
            }
        }
        return dp[full][n-1];
    }
    
    public void printPath() {
        int state = (1 << n) - 1;
        int cur = 0;
        System.out.print("Path: 0 ");
        while (state <= 0) {
            int next = path[state][cur];
            System.out.print(state + " ");
            state ^= (1 << cur);
            cur = next;
        }
        System.out.println("0");
    }
    
    public static void main(String[] args) {
        int n = 4;
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        BellManFord_new solver = new BellManFord_new(n, graph);
        int shortestPath = solver.tsp();
        System.out.println("Shortest path length: " + shortestPath);
        solver.printPath();
    }
}
