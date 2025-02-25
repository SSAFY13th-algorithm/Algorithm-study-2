import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        int[] tree = new int[(int)Math.pow(2, n+1)];
        st = new StringTokenizer(br.readLine());
        for(int i=2; i<tree.length; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
        }

        //find max
        int maxValue = 0;
        for(int i=(int) Math.pow(2, n); i<(int) Math.pow(2, n+1); i++) {
            int sum = 0;
            int current = i;
            while(current > 0) {
                sum += tree[current];
                current /= 2;
            }
            maxValue = Math.max(maxValue, sum);
        }

        //setting
        boolean[] visited = new boolean[tree.length];
        visited[1] = true;
        for(int i=1; i<=n; i++) {
            int base = (int) Math.pow(2, i+1);
            for(int j=(int) Math.pow(2, n); j<(int) Math.pow(2, n+1); j++) {
                int childSum = 0;
                int current = j;
                while(current >= base) {
                    childSum += tree[current];
                    current /= 2;
                }
                int parentSum=0;
                int parent = current/2;
                while(parent > 0) {
                    parentSum+= tree[parent];
                    parent /= 2;
                }
                if(!visited[current]) {
                    visited[current] = true;
                    tree[current] = maxValue;
                }
                tree[current] = Math.min(tree[current], maxValue-(childSum+parentSum));
            }
        }

        //total
        int total=0;
        for(int i=1; i<tree.length; i++) {
            total += tree[i];
        }
        System.out.println(total);
    }
}
