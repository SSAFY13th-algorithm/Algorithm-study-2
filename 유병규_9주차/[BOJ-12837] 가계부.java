import java.io.*;
import java.util.*;

public class Main {
	private static long[] arr;
	private static long[] segmentTree;
	private static int treeSize;
	private static StringBuilder sb = new StringBuilder();
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        arr = new long[n+1];
        
        int h = (int) Math.ceil(Math.log(n)/Math.log(2));
        treeSize = (int) Math.pow(2, h+1);
        
        segmentTree = new long[treeSize];
        
       for(int i=0; i<q; i++) {
    	   st = new StringTokenizer(br.readLine());
    	   int oper = Integer.parseInt(st.nextToken());
    	  if(oper==1) {
    		  int a = Integer.parseInt(st.nextToken());
    		  long b = Long.parseLong(st.nextToken());
			  arr[a] += b;
    		  update(1, 1, n, a, b);
    	  }
    	  else {
    		  int a = Integer.parseInt(st.nextToken());
    		  int b = Integer.parseInt(st.nextToken());
    		  long sum = sum(1, 1, n, a, b);
    		  sb.append(sum).append("\n");
    	  }
       }
       System.out.println(sb.toString().trim());
    }
    
    private static void update(int node, int start, int end, int idx, long diff) {
    	if(idx < start || idx > end) return;
    	
    	segmentTree[node] += diff;
    	
    	if(start != end) {
    		update(node*2, start, (start+end)/2, idx, diff);
    		update(node*2+1, (start+end)/2+1, end, idx, diff);
    	}
    }
    
    private static long sum(int node, int start, int end, int left, int right) {
    	if(left > end || right < start) return 0;
    	
    	if(left <= start && right >= end) return segmentTree[node];
    	
    	return sum(node*2, start, (start+end)/2, left, right) + sum(node*2+1, (start+end)/2+1, end, left, right);
    }
}
