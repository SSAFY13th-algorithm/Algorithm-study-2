import java.io.*;
import java.util.*;

public class Main {
	private static int n, k;
	private static Set<String> set;
	private static int result;
	private static String goal;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        int[] number = new int[n];
        int[] temp = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
        	temp[i] = number[i] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(temp);
        goal = Arrays.toString(temp);
        
        set = new HashSet<>();
        set.add(Arrays.toString(number));
        
        result = Integer.MAX_VALUE;
        bfs(number);
        
        if(result == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(result);
    }
    
    private static void bfs(int[] arr) {
    	Queue<Node> q = new LinkedList<>();
    	q.offer(new Node(arr, 0));
    	set.add(Arrays.toString(arr));
    	
    	while(!q.isEmpty()) {
    		Node node = q.poll();
    		
    		if(node.count > result) continue;
    		if(goal.equals(node.toString())) {
    			result = node.count;
    			continue;
    		}
    		
    		for(int i=0; i+k-1<n; i++) {
    			node.swap(i, i+k-1);
    			if(set.contains(node.toString())) {
    				node.swap(i, i+k-1);
    				continue;
    			}
    			q.offer(new Node(node.arr, node.count+1));
    			set.add(node.toString());
    			node.swap(i, i+k-1);
    		}
    	}
    }
	
	private static class Node{
		int[] arr;
		int count;
		
		public Node(int[] arr, int count) {
			this.arr = Arrays.copyOf(arr, arr.length);
			this.count = count;
		}
		
		public void swap(int left, int right) {
			while(left < right) {
				int temp = arr[left];
				arr[left] = arr[right];
				arr[right] = temp;
				left++;
				right--;
			}
		}
		
		@Override
		public String toString() {
			return Arrays.toString(arr);
		}
	}
}
