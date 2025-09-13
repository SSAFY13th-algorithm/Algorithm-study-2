import java.util.*;
import java.io.*;

public class Main {
	static StringTokenizer st;
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		st = new StringTokenizer(br.readLine());
		int N = nextInt(), M = nextInt();
		
		int[] A = new int[M];
		int[] B = new int[M];
		int[] C = new int[N+1];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = nextInt(), b = nextInt();
			A[i] = a;
			B[i] = b;
			C[a]++;
			C[b]++;
		}
		
		int ans = 1234567890;
		for(int i=0;i<M;i++) {
			int[] T = new int[N+1];
			int a = A[i], b = B[i];
			for(int j=0;j<M;j++) {
				if(i == j) continue;
				if(A[j] == a) T[B[j]] += a;
				if(A[j] == b) T[B[j]] += b;
				if(B[j] == a) T[A[j]] += a;
				if(B[j] == b) T[A[j]] += b;
			}
			for(int j=1;j<=N;j++) {
				if(j == a || j == b || T[j] != a+b) continue;
				ans = Math.min(ans, C[a] + C[b] + C[j] - 6);
			}
		}
		bw.write((ans==1234567890 ? -1 : ans)+"\n");
		
		bw.close();
	}

}
