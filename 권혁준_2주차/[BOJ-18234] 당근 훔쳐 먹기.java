import java.util.*;
import java.io.*;

class Carrot{
	int w,p;
	Carrot(int w, int p){
		this.w = w;
		this.p = p;
	}
}

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
	

	public static void main(String[] args) throws Exception {

		nextLine();
		int N = nextInt();
		long T = nextLong();
		
		Carrot[] carrots = new Carrot[N];
		for(int i=0;i<N;i++) {
			nextLine();
			int w = nextInt(), p = nextInt();
			carrots[i] = new Carrot(w,p);
		}
		Arrays.sort(carrots, (a,b) -> a.p - b.p);
		
		long ans = 0;
		for(int i=0;i<N;i++) {
			Carrot now = carrots[i];
			ans += now.w + (long)now.p*(T-N+i);
		}
		bw.write(ans+"\n");
		
		bwEnd();
	}

}
