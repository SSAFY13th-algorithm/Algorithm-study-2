import java.io.*;
import java.util.*;


public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        int max = 0;
        int[][] pillar = new int[n][2];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            pillar[i][0] = Integer.parseInt(st.nextToken());
            pillar[i][1] = Integer.parseInt(st.nextToken());

            max = Math.max(max, pillar[i][1]);
        }

        Arrays.sort(pillar, (o1,o2) ->{
            return o1[0] - o2[0];
        });

        int end = 0;
        for(int i=0; i<n; i++) {
            if(pillar[i][1] == max) end = i;
        }

        int total = 0;
        int preP = 0;
        int preH = 0;
        for(int i=0; i<=end; i++) {
            if(pillar[i][1] >= preH) {
                total += (pillar[i][0] - preP) * preH;
                preP = pillar[i][0];
                preH = pillar[i][1];
            }
        }

        total += max;

        preP = pillar[n-1][0];
        preH = pillar[n-1][1];
        for(int i=n-1; i>=end; i--) {
            if(pillar[i][1] >= preH) {
                total += (preP - pillar[i][0]) * preH;
                preP = pillar[i][0];
                preH = pillar[i][1];
            }
        }

        System.out.println(total);
    }
}
