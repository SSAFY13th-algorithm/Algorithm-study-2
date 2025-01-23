import java.util.*;
import java.io.*;

public class Main {
	static StringTokenizer st;
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int[] A = new int[N+2];
		A[0] = -1000000001;
		A[N+1] = 1000000001;
		for(int i=1;i<=N;i++) A[i] = nextInt();
		
		int[] L = new int[N+2];
		int[] R = new int[N+2];
		L[0] = 1;
		for(int i=1;i<=N;i++) {
			if(A[i] < A[i-1]) break;
			L[i] = 1;
		}
		R[N+1] = 1;
		for(int i=N;i>=1;i--) {
			if(A[i] > A[i+1]) break;
			R[i] = 1;
		}
		
		int ans = 0;
		for(int i=1;i<=N;i++) {
			if(A[i-1] <= A[i+1] && L[i-1]==1 && R[i+1]==1) ans++;
		}
		bw.write(ans+"\n");

		
		bw.close();
	}

}
