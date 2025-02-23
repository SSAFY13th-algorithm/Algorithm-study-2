import java.io.*;
import java.util.*;

public class Main {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int n;
	private static String result = "-1";
	private static List<String> list = new ArrayList<>();
	private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	n = Integer.parseInt(st.nextToken());
    	int k = Integer.parseInt(st.nextToken());

    	backTracking(0);
    	if(list.size() >= k) {
    		result = list.get(k-1).substring(1, list.get(k-1).length());
    	}
    	System.out.println(result);
    }


    private static void backTracking(int currentSum) {
    	if(currentSum >= n) {
    		if(currentSum == n) {
    			list.add(sb.toString());
    		}
    		return;
    	}

    	for(int i=1; i<=3; i++) {
    		sb.append("+").append(i);
    		backTracking(currentSum+i);
    		sb.delete(sb.length()-2, sb.length());
    	}
    }
}
