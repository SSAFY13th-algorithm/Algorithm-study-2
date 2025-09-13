import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        int[] numbers = new int[2*m-1];
        Arrays.fill(numbers, 1);
        
        for(int i=0; i<n; i++) {
        	st = new StringTokenizer(br.readLine());
        	int count0 = Integer.parseInt(st.nextToken());
        	int count1 = Integer.parseInt(st.nextToken());
        	int count2 = Integer.parseInt(st.nextToken());
        	
        	int index = 0;
        	for(int j=0; j<count0; j++) {
        		numbers[index++] += 0;
        	}
        	for(int j=0; j<count1; j++) {
        		numbers[index++] += 1;
        	}
        	for(int j=0; j<count2; j++) {
        		numbers[index++] += 2;
        	}
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<m; i++) {
        	sb.append(numbers[m-1-i]).append(" ");
        	for(int j=m; j<2*m-1; j++) {
        		sb.append(numbers[j]).append(" ");
        	}
        	sb.append("\n");
        }
        System.out.println(sb.toString().trim());
    }
}
