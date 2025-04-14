import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        int[] numbers = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
        	numbers[i] = Integer.parseInt(st.nextToken());
        }
        
        
        int count = 0;
        while(true) {
        	
        	for(int i=0; i<n; i++) {
        		if(numbers[i] == 0) continue;
        		if(numbers[i]%2 == 1) {
        			numbers[i]--;
        			count++;
        		}
        		numbers[i] /= 2;
        	}
        	boolean isAllZero = true;
        	for(int i=0; i<n; i++) {
        		if(numbers[i] == 0) continue;
        		isAllZero = false;
        		break;
        	}
        	if(isAllZero) break;
        	count++;
        }
        System.out.println(count);
    }
}
