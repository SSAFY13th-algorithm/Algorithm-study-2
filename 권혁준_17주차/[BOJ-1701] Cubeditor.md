```java
import java.util.*;
import java.io.*;

public class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		while(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static double nextDouble() throws Exception { return Double.parseDouble(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static char[] B;
	static int[] X;
	
	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
		
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		B = br.readLine().toCharArray();
		
	}
	
	static void solve() throws Exception {
		
		int max = 0;
		for(int l=0;l<B.length-1;l++) {
			char[] A = new char[B.length-l];
			for(int i=0;i<A.length;i++) A[i] = B[i+l];
			X = new int[A.length];
			for(int i=1;i<A.length;i++) {
				X[i] = X[i-1];
				while(X[i]>0 && A[X[i]] != A[i]) X[i] = X[X[i]-1];
				if(A[X[i]] == A[i]) X[i]++;
				max = Math.max(X[i], max);
			}			
		}
		bw.write(max + "\n");
		
	}
	
}
```
