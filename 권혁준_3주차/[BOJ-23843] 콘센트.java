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

	static int N, M;
	static PriorityQueue<Integer> PQ;
	static long ans = 0;
	

	public static void main(String[] args) throws Exception {
		
		ready();
		solve();
		
		bwEnd();
	}
	
	static void ready() throws Exception{
		
		nextLine();
		N = nextInt();
		M = nextInt();
		PQ = new PriorityQueue<>((a,b) -> b-a);
		
		nextLine();
		for(int i=0;i<N;i++) PQ.offer(nextInt());
		
	}
	
	static void solve() throws Exception{
		
		while(!PQ.isEmpty()) {
			
			List<Integer> temp = new ArrayList<>();
			for(int i=0;i<M && !PQ.isEmpty();i++) temp.add(PQ.poll());
			int last = temp.get(temp.size()-1);
			for(int i:temp) {
				int v = i - last;
				if(v > 0) PQ.add(v);
			}
			ans += last;
			
		}
		
		bw.write(ans+"\n");

	}

}
