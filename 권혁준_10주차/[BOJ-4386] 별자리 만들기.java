import java.util.*;
import java.io.*;

class Edge{
	int s, e;
	double c;
	Edge(int s, int e, double c){
		this.s = s;
		this.e = e;
		this.c = c;
	}
}

class Main {
	
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
	
	static int N;
	static double[][] A;
	static int[] r;
	static List<Edge> E;
	
	static int f(int x) {return x==r[x] ? x : (r[x]=f(r[x]));}
	
	public static void main(String[] args) throws Exception {
			
		ready();
		solve();
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		N = nextInt();
		
		r = new int[N];
		for(int i=0;i<N;i++) r[i] = i;
		
		A = new double[N][2];
		for(int i=0;i<N;i++) for(int j=0;j<2;j++) A[i][j] = nextDouble();
		
		E = new ArrayList<>();
		
	}
	
	static void solve() throws Exception{

		for(int i=0;i<N;i++) for(int j=0;j<i;j++) {
			double dist = Math.sqrt((A[i][0]-A[j][0])*(A[i][0]-A[j][0]) + (A[i][1]-A[j][1])*(A[i][1]-A[j][1]));
			E.add(new Edge(i,j,dist));
		}
		Collections.sort(E, (a,b) -> Double.compare(a.c, b.c));
		
		double ans = 0;
		for(Edge e : E) {
			int x = f(e.s), y = f(e.e);
			if(x != y) {
				ans += e.c;
				r[x] = y;
			}
		}
		bw.write(ans + "\n");
		
	}
	
}
