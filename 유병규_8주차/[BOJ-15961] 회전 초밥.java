import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        int[] sushi = new int[n];
        for(int i=0; i<n; i++) {
        	sushi[i] = Integer.parseInt(br.readLine());
        }
        
        Map<Integer, Integer> map = new HashMap<>();
        map.put(c, 1);
        
        for(int i=0; i<k; i++) {
        	map.put(sushi[i], map.getOrDefault(sushi[i], 0)+1);
        }
        
        int max = Math.max(map.size(), 1);
        int start = 0;
        int end = k;
        do {
        	map.put(sushi[end], map.getOrDefault(sushi[end], 0)+1);
        	map.put(sushi[start], map.get(sushi[start])-1);
        	if(map.get(sushi[start]) == 0) map.remove(sushi[start]);
        	
        	max = Math.max(map.size(), max);
        	start = (start+1)%n;
        	end = (end+1)%n;
        } while(start != 0);
        
        System.out.println(max);
    }
}
