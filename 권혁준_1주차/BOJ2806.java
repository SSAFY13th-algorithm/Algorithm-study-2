import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
}

public class Main {
	static StringTokenizer st;
	static int nextInt() { return Integer.parseInt(st.nextToken()); }
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.parseInt(br.readLine());
		String S = br.readLine();
		int[] dp1 = new int[N];
		int[] dp2 = new int[N];
		int last_a = -1, last_b = -1;
		
		if(S.charAt(0)=='A') {
			dp2[0] = 1;
			last_a = 0;
		}
		else {
			dp1[0] = 1;
			last_b = 0;
		}

		for(int i=1;i<N;i++) {
			if(S.charAt(i)=='A') {
				dp1[i] = dp1[i-1];
				dp2[i] = dp2[i-1] + 1;
				if(last_b != -1) dp2[i] = Math.min(dp2[i], dp2[last_b]+2);
				last_a = i;
			}
			else {
				dp2[i] = dp2[i-1];
				dp1[i] = dp1[i-1] + 1;
				if(last_a != -1) dp1[i] = Math.min(dp1[i], dp1[last_a]+2);
				last_b = i;
			}
			dp1[i] = Math.min(dp1[i], dp2[i]+1);
			dp2[i] = Math.min(dp2[i], dp1[i]+1);
		}
		bw.write(dp1[N-1]+"\n");
		
		
		bw.close();
	}

}
