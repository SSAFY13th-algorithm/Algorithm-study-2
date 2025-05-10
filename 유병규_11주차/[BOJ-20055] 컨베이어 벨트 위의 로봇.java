import java.io.*;
import java.util.*;

public class Main {
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());

        LinkedList<Block> up = new LinkedList<>();
        for(int i=0; i<n; i++) {
        	up.addFirst(new Block(Integer.parseInt(st.nextToken())));
        }
        LinkedList<Block> down = new LinkedList<>();
        for(int i=0; i<n; i++) {
        	down.addFirst(new Block(Integer.parseInt(st.nextToken())));
        }
        
        int step = 0;
        int count = 0;
        while(true) {
        	step++;
        	//1. 회전
        	down.add(up.poll());
        	up.add(down.poll());
        	up.peek().robot = false;
        	//2. 로봇 이동
        	for(int i=1; i<up.size(); i++) {
        		Block current = up.get(i);
        		if(!current.robot) continue;
        		Block next = up.get(i-1);
        		if(next.robot || next.durability == 0) continue;
        		current.robot = false;
        		next.robot = true;
        		next.durability--;
        		if(next.durability == 0) count++;
        		if(i-1 == 0) {
        			next.robot = false;
        		}
        	}
        	//3. 로봇 올리기
        	Block b = up.getLast();
        	if(b.durability != 0) {
        		b.robot = true;
        		b.durability--;
        		if(b.durability == 0) count++;
        	}
        	//4. 확인
        	if(count >= k) break;
        }
        
        System.out.println(step);
    }
    
    private static class Block{
    	int durability;
    	boolean robot;
    	
    	public Block(int durability) {
    		this.durability = durability;
    		this.robot = false;
    	}
    	
    	@Override
    	public String toString() {
    		return "["+durability+","+robot+"]";
    	}
    }
}
