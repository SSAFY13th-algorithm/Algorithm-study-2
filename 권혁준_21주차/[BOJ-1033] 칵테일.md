```java
import java.math.BigInteger;
import java.util.*;
import java.io.*;

class IOController {
    BufferedReader br;
    BufferedWriter bw;
    StringTokenizer st;

    public IOController() {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer("");
    }

    String nextLine() throws Exception {
        String line = br.readLine();
        st = new StringTokenizer(line);
        return line;
    }

    String nextToken() throws Exception {
        while (!st.hasMoreTokens()) nextLine();
        return st.nextToken();
    }

    int nextInt() throws Exception {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws Exception {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws Exception {
        return Double.parseDouble(nextToken());
    }

    void close() throws Exception {
        bw.flush();
        bw.close();
    }

    void write(String content) throws Exception {
        bw.write(content);
    }

}

public class Main {

    static IOController io;

    //

    static int N;
    static long[] A;
    static List<int[]>[] V;

    public static void main(String[] args) throws Exception {

        io = new IOController();

        init();
        solve();

        io.close();

    }

    public static void init() throws Exception {

        N = io.nextInt();
        V = new List[N];
        for(int i=0;i<N;i++) V[i] = new ArrayList<>();
        for(int i=1;i<N;i++) {
            int a = io.nextInt(), b = io.nextInt(), c = io.nextInt(), d = io.nextInt();
            long g = gcd(c,d);
            c /= g;
            d /= g;
            V[a].add(new int[]{b, c, d});
            V[b].add(new int[]{a, d, c});
        }
        A = new long[N];
        Arrays.fill(A, 1);

    }

    static void solve() throws Exception {

        dfs(0,-1);
        for(int i=0;i<N;i++) io.write(A[i] + " ");

    }

    static void dfs(int n, int p) {
        for(int[] e:V[n]) if(e[0] != p) {
            int i = e[0], a = e[1], b = e[2];
            dfs(i,n);
            A[n] *= A[i] * a;
        }
        change(n,p);
        long g = subgcd(n, p);
        div(n,p,g);
    }

    static long gcd(long a, long b) {
        if(a < b) {
            long temp = a;
            a = b;
            b = temp;
        }
        return a%b == 0 ? b : gcd(b, a%b);
    }

    static long subgcd(int n, int p) {
        long g = A[n];
        for(int[] e:V[n]) if(e[0] != p) {
            g = gcd(subgcd(e[0], n), g);
        }
        return g;
    }

    static void div(int n, int p, long g) {
        A[n] /= g;
        for(int[] e:V[n]) if(e[0] != p) div(e[0],n,g);
    }

    static void change(int n, int p) {
        for(int[] e:V[n]) if(e[0] != p) {
            int i = e[0], a = e[1], b = e[2];
            A[i] = A[n]*b/a;
            change(e[0],n);
        }
    }

}
```
