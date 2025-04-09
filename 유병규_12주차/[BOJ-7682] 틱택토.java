import java.io.*;
import java.util.*;

public class Main {
	private static final String VALID = "valid";
	private static final String INVALID = "invalid";
	private static char[][] map;
	private static List<int[]> xs = new ArrayList<>();
	private static List<int[]> os = new ArrayList<>();
	private static boolean[] xvisited;
	private static boolean[] ovisited;
	private static char[][] testMap;
	private static int[][] d = {{0,1},{1,1},{1,0},{1,-1},{0,-1}};
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringBuilder sb = new StringBuilder();
        while(true) {
        	String input = br.readLine();
        	if(input.equals("end")) {
        		System.out.println(sb.toString().trim());
        		return;
        	}
        	
        	map = new char[3][3];
        	xs.clear();
        	os.clear();
        	for(int i=0; i<3; i++) {
        		for(int j=0; j<3; j++) {
        			map[i][j] = input.charAt(i*3 + j);
        			if(map[i][j] == 'X') xs.add(new int[] {i,j});
        			else if(map[i][j] == 'O') os.add(new int[] {i,j});
        		}
        	}
        	
        	//case 1: O의 개수가 X보다 많은 경우 불가능
        	//case 2: X의 개수가 O보다 2개 이상 많은 경우 불가능
        	if(xs.size() < os.size() || xs.size()-os.size() > 1) {
        		sb.append(INVALID).append("\n");
        		continue;
        	}
        	//case 3: 꽉 안 찼는데 안 끝난 경우 불가능
        	if(xs.size()+os.size() < 9 && !done(map, xs.size())) {
        		sb.append(INVALID).append("\n");
    			continue;
        	}
        	
        	//case 4: 꽉 찼는데 안 끝난 경우 가능
        	if(!done(map, xs.size())) {
        		sb.append(VALID).append("\n");
        		continue;
        	}
        	
        	//case 5: 꽉 안 찼는데 끝난 경우 직접 시뮬
        	//case 6: 꽉 찼는데 끝난 경우 직접 시뮬
        	xvisited = new boolean[xs.size()];
        	ovisited = new boolean[os.size()];
        	testMap = new char[3][3];
        	boolean isDone = false;
        	for(int i=0; i<xs.size(); i++) {
        		xvisited[i] = true;
        		testMap[xs.get(i)[0]][xs.get(i)[1]] = 'X';
        		if(dfs(i, false, 1, 0)) {
        			sb.append(VALID).append("\n");
        			isDone = true;
        			break;
        		}
        		xvisited[i] = false;
        		testMap[xs.get(i)[0]][xs.get(i)[1]] = '.';
        	}

        	if(!isDone) sb.append(INVALID).append("\n");
        }
        
    }
    
    /**
     * @param x true면 x차례 false면 o차례
     * */
    private static boolean dfs(int current, boolean isX, int xCount, int oCount) {
    	if(done(testMap, xCount)) {
    		if(xCount != xs.size() || oCount != os.size()) return false;
    		return true;
    	}
    	
    	if(isX) {
    		for(int i=0; i<xs.size(); i++) {
    			if(xvisited[i]) continue;
    			xvisited[i] = true;
        		testMap[xs.get(i)[0]][xs.get(i)[1]] = 'X';
        		if(dfs(i, false, xCount+1, oCount)) return true;
        		xvisited[i] = false;
        		testMap[xs.get(i)[0]][xs.get(i)[1]] = '.';
    		}
    	}
    	else {
    		for(int i=0; i<os.size(); i++) {
    			if(ovisited[i]) continue;
    			ovisited[i] = true;
        		testMap[os.get(i)[0]][os.get(i)[1]] = 'O';
        		if(dfs(i, true, xCount, oCount+1)) return true;
        		ovisited[i] = false;
        		testMap[os.get(i)[0]][os.get(i)[1]] = '.';
    		}
    	}
    	
    	return xCount==xs.size() && oCount==os.size();
    }
    
	private static boolean done(char[][] map, int xCount) {
		if(xCount < 3) return false;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(map[i][j] == 'X') {
					if(find(map, 'X', i, j)) return true;
				}
				if(map[i][j] == 'O') {
					if(find(map, 'O', i, j)) return true;
				}
			}
		}
		
		return false;
	}

	
	private static boolean find(char[][] map, char target, int x, int y) {
		for(int i=0; i<d.length; i++) {
			int count = 1;
			int nx = x + d[i][0];
			int ny = y + d[i][1];
			
			while(nx>=0 && nx<3 && ny>=0 && ny<3) {
				if(map[nx][ny] == target) count++;
				nx = nx + d[i][0];
				ny = ny + d[i][1];
			}
			
			if(count == 3) return true;
		}
		
		return false;
	}
}
