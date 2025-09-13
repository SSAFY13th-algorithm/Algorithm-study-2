import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static int nextInt() {return Integer.parseInt(st.nextToken());}
	static long nextLong() {return Long.parseLong(st.nextToken());}
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
    static int N;
    static int[] A;

	public static void main(String[] args) throws Exception {

		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
        N = Integer.parseInt(br.readLine());
        A = new int[N];
        nextLine();
        for(int i=0;i<N;i++) A[i] = nextInt();

	}
	
	static void solve() throws Exception{

        int ans = 0, s = 0, e = 0, t = 0;
        int[] cnt = new int[10];
        while(e < N){
            if(cnt[A[e]] == 0) t++;
            cnt[A[e]]++;
            e++;
            while(t>2){
                cnt[A[s]]--;
                if(cnt[A[s]] == 0) t--;
                s++;
            }
            ans = Math.max(ans, e-s);
        }
        bw.write(ans+"\n");

	}
	
}
