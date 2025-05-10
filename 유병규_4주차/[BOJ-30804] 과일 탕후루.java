import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        int[] stick = new int[n];
        for(int i=0; i<n; i++) {
            stick[i] = Integer.parseInt(st.nextToken());
        }

        Map<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int max = 0;
        for(int right=0; right<n; right++) {
            map.put(stick[right], map.getOrDefault(stick[right], 0) + 1);

            while(map.size() >= 3) {
                map.put(stick[left], map.get(stick[left])-1);
                if(map.get(stick[left]) == 0) map.remove(stick[left]);
                left++;
            }

            max = Math.max(max, right - left +1);
        }

        System.out.println(max);
    }
}
