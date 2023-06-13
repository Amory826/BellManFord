import java.util.*;

public class BellManFord_demo {
    private static final int INF = 1000000001;
    private static int[][] dist; // Khoang cach giua 2 thanh pho
    private static int[][] dp; // dp[i][j]: cho phi nho nhat de tham cac thanh pho tu i đến j
    private static int n; // so luong thanh pho
    private static int[] path; // Mang luu tru duong di ngan nhat

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so luong thanh pho can di :");
        n = sc.nextInt();
        Random random = new Random();
        dist = new int[n][n];
        dp = new int[n][1 << n];    
        path = new int[n + 1]; // Khởi tạo mảng path sau khi đã biết giá trị của n
        // nhap khoang cach giua 2 thanh pho
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    int temp = random.nextInt(100) + 1;
                    dist[i][j] = temp;
                    dist[j][i] = temp;
                }
            }
        }
        System.out.println("Ma tran duoc chuyen doi nhu sau: ");

        Formatter ft = new Formatter(System.out);
        // in ra ma tran thanh pho
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ft.format("%5s", dist[i][j]);
            }
            System.out.println();
        }

        
        List<Integer> path = new ArrayList<>();
        int result = tsp(0, 1, path);
        System.out.println("Chi phi nho nhat de tham thanh pho : " + result);
        System.out.print("Duong di ngan nhat: ");
        Queue<Integer> q = new LinkedList<>();
        int k = n - 1;
        int[] b = new int[1000001];
        for (int i = 0; i < 1000001; i++) {
            b[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            if (b[path.get(i)] == 0) {
                if (q.size() < k) {
                    q.add(path.get(i));
                    b[path.get(i)] = 1;
                } else {
                    b[path.get(i)] = 1;
                    b[q.peek()] = 0;
                    q.add(path.get(i));
                    q.poll();
                }
            }
        }
        System.out.print("0 -> ");
        while (!q.isEmpty()) {
            System.out.print(q.peek() + " -> ");
            q.poll();
        }
        System.out.println("0");
        ft.close();
        sc.close();
    }

    private static int tsp(int pos, int mask, List<Integer> Path) {
        if (mask == (1 << n) - 1) {
            // Da tham het cac thanh pho, them thanh pho dau tien vao duong di va tra ve chi
            // phi
            Path.add(0, pos);
            return dist[pos][0];
        }

        if (dp[pos][mask] != 0) {
            return dp[pos][mask];// Neu da tinh toan
        }

        int minCost = INF;
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {
                int cost = dist[pos][i] + tsp(i, mask | (1 << i), Path);
                if (cost < minCost) {
                    minCost = cost;
                }
            }
        }
        printPath(pos, mask, minCost);
        

        return minCost;
    }

    private static void printPath(int pos, int mask, int cost) {
        if (mask == (1 << n) - 1) {
            // Neu da tham het cac thanh pho, in ra duong di ngan nhat
            path[n] = pos;
            for (int i = 0; i <= n; i++) {
                System.out.print(path[i]);
            }
            System.out.println();
            return;

        }
        // Tim kiem ca thanh pho chua duoc tham
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {
                int newCost = cost + dist[pos][i];
                if (newCost < dp[i][mask | (1 << i)]) {
                    // Neu chi phi tu thanh pho hien tai den thanh pho moi nho hon chi phi hien tai
                    // cap nhat lai duong di ngan nhat
                    dp[i][mask | (1 << i)] = newCost;
                    path[pos] = i;
                    printPath(i, mask | (1 << i), newCost);
                }
            }
        }
    }
}
