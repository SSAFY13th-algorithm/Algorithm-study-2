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

    static List<Integer> A, B, C;
    static boolean[] RB;

    public static void main(String[] args) throws Exception {

        ready();
        solve();

        bwEnd();

    }

    static void ready() throws Exception {

        A = new ArrayList<>();
        B = new ArrayList<>();
        C = new ArrayList<>();
        char[] temp = nextToken().toCharArray();
        for(int i=0;i<temp.length;i++) {
            if(temp[i] == 'A') A.add(i);
            if(temp[i] == 'B') B.add(i);
            if(temp[i] == 'C') C.add(i);
        }
        RB = new boolean[B.size()];

    }

    static void solve() throws Exception {

        int ans = 0;
        for(int i=0, j=0;i<B.size() && j<C.size();i++) {
            while(j < C.size() && B.get(i) > C.get(j)) j++;
            if(j < C.size()) {
                ans++;
                RB[i] = true;
                j++;
            }
        }

        for(int i=0, j=0;i<A.size() && j<B.size();i++) {
            while(j < B.size() && (RB[j] || A.get(i) > B.get(j))) j++;
            if(j < B.size()) {
                ans++;
                j++;
            }
        }

        bw.write(ans + "\n");

    }

}
```
