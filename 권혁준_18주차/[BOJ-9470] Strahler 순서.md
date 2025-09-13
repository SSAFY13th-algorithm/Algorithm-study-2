```java
import java.util.*;
import java.io.*;

public class Main {

    // IO field
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");

    static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
    static String nextToken() throws Exception {
        while(!st.hasMoreTokens()) nextLine();
        return st.nextToken();
    }
    static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
    static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
    static double nextDouble() throws Exception { return Double.parseDouble(nextToken()); }
    static void bwEnd() throws Exception {bw.flush();bw.close();}

    // Additional field

    static int K, M, P;
    static int[] d;
    static List<Integer>[] V, C;

    public static void main(String[] args) throws Exception {

        int T = nextInt();
        for(int t=1;t<=T;solve(t++)) ready();

        bwEnd();

    }

    static void ready() throws Exception{

        K = nextInt();
        M = nextInt();
        P = nextInt();
        V = new List[M+1];
        C = new List[M+1];
        for(int i=1;i<=M;i++) {
            V[i] = new ArrayList<>();
            C[i] = new ArrayList<>();
        }
        d = new int[M+1];
        for(int i=0;i<P;i++) {
            int a = nextInt(), b = nextInt();
            V[a].add(b);
            d[b]++;
        }

    }

    static void solve(int tc) throws Exception {

        Queue<Integer> Q = new LinkedList<>();
        for(int i=1;i<=M;i++) if(d[i] == 0) {
            Q.offer(i);
            C[i].add(1);
        }

        while(!Q.isEmpty()) {
            int n = Q.poll();
            Collections.sort(C[n], (a,b) -> b-a);
            int c = C[n].get(0);
            if(C[n].size() > 1 && C[n].get(0).equals(C[n].get(1))) c++;
            if(n == M){
                bw.write(tc + " " + c + "\n");
                return;
            }
            for(int i:V[n]) {
                if(--d[i] == 0) Q.offer(i);
                C[i].add(c);
            }
        }

    }

}
```
