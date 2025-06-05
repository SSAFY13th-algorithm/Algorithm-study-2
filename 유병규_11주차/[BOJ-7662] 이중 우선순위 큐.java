import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for(int test=0; test<T; test++) {
        	int k = Integer.parseInt(br.readLine());
        	
        	TreeMap<Integer, Integer> treemap = new TreeMap<>();
        	
        	for(int i=0; i<k; i++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		String oper = st.nextToken();
        		int value = Integer.parseInt(st.nextToken());
        		
        		if(oper.equals("I")) {
        			treemap.put(value, treemap.getOrDefault(value, 0)+1);
        			continue;
        		}
        		if(treemap.isEmpty()) continue;
        		int key = (value == 1) ? treemap.lastKey() : treemap.firstKey();
        		int count = treemap.get(key);
        		
        		if(count == 1) treemap.remove(key);
        		else treemap.put(key, count-1);
        	}
        	
        	if(treemap.isEmpty()) {
        		sb.append("EMPTY").append("\n");
        		continue;
        	}
        	sb.append(treemap.lastKey()).append(" ").append(treemap.firstKey()).append("\n");
        }
        
        System.out.println(sb.toString().trim());
    }
}
