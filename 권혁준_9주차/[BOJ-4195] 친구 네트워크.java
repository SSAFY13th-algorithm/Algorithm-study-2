import java.util.*;
import java.io.*;

class Main {
	
	// IO field
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st = new StringTokenizer("");

	static void nextLine() throws Exception {st = new StringTokenizer(br.readLine());}
	static String nextToken() throws Exception {
		if(!st.hasMoreTokens()) nextLine();
		return st.nextToken();
	}
	static int nextInt() throws Exception { return Integer.parseInt(nextToken()); }
	static long nextLong() throws Exception { return Long.parseLong(nextToken()); }
	static void bwEnd() throws Exception {bw.flush();bw.close();}
	
	// Additional field
	
	static int F;
	static TreeMap<String, Integer> index;
	static int[] r, c;
	
	public static void main(String[] args) throws Exception {
		
		for(int t=nextInt();t-->0;) {
			
			ready();
			solve();
		}
	
		bwEnd();
		
	}
	
	static void ready() throws Exception{
		
		F = nextInt();
		index = new TreeMap<>();
		r = new int[F*2];
		c = new int[F*2];
		for(int i=0;i<F*2;i++) {
			r[i] = i;
			c[i] = 1;
		}
		
		
	}
	
	static void solve() throws Exception{
		
		
		int id = 0;
		for(int i=0;i<F;i++) {
			nextLine();
			String a = nextToken(), b = nextToken();
			if(!index.containsKey(a)) index.put(a, id++);
			if(!index.containsKey(b)) index.put(b, id++);
			int x = f(index.get(a)), y = f(index.get(b));
			if(x != y) {
				c[y] += c[x];
				r[x] = y;				
			}
			bw.write(c[y] + "\n");
		}
		
	}
	
	static int f(int x) { return x==r[x] ? x : (r[x]=f(r[x])); }
	
}
