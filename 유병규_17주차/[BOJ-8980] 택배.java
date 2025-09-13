import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        List<int[]> order = new ArrayList<>();
        
        int m = Integer.parseInt(br.readLine());
        for(int i=0; i<m; i++) {
        	st = new StringTokenizer(br.readLine());
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int d = Integer.parseInt(st.nextToken());
        	
        	order.add(new int[] {a, b, d});
        }
        //도착지 기준 정렬
        Collections.sort(order, (o1, o2) -> Integer.compare(o1[1], o2[1]));
        
        //트럭에 실은 박스들
        int[] village = new int[n+1];
        
        //트럭 출발
        int result = 0;
        for(int idx=0; idx<m; idx++) {
        	int[] info = order.get(idx);
        	
        	int start = info[0];
        	int end = info[1];
        	int amount = info[2];
        	
        	int maxLoad = 0;
        	for(int i=start; i<end; i++) {
        		maxLoad = Math.max(village[i], maxLoad);
        	}
        	
        	int canLoad = Math.min(amount, c-maxLoad);
        	
        	result += canLoad;
        	for(int i=start; i<end; i++) {
        		village[i] += canLoad;
        	}
        }
        
        System.out.println(result);
    }
}
