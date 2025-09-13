import java.io.*;
import java.util.*;

public class Main {
	private static String number;
	private static int count;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        number = br.readLine();
        
        dfs(0);
        if(count == 0) System.out.println(1);
        else System.out.println(count);
    }
    
    private static void dfs(int index) {
    	if(index >= number.length()) {
    		count++;
    		return;
    	}
    	if(number.charAt(index) == '0') {
    		return;
    	}
    	dfs(index+1);    		
    	if(index+1 < number.length()) {
    		int num = (number.charAt(index)-'0')*10+(number.charAt(index+1)-'0');
    		if(num <= 34) {
    			dfs(index+2);
    		}
    	}
    }
}
