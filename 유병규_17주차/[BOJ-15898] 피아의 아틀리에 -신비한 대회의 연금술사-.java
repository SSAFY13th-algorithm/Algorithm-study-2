import java.io.*;
import java.util.*;

public class Main {
	private static final int ORDER_SIZE = 3;
	private static final int MATTER_SIZE = 4;
	private static final int KILN_SIZE = 5;
	
	private static int n;
	private static Matter[][] matters;
	private static int[][] order;
	private static int result;
	private static Matter kiln;
    private static int[][] position = {{0,0},{0,1},{1,0},{1,1}};
    private static boolean[] visited;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        matters = new Matter[n][4];
        for(int i=0; i<n; i++) {
        	matters[i][0] = new Matter(MATTER_SIZE);
        }
        
        //각 재료 초기화
        for(int repeat=0; repeat<n; repeat++) {
        	//재료
        	Matter matter = matters[repeat][0];
        	//효능 초기화
        	for(int i=0; i<MATTER_SIZE; i++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		for(int j=0; j<MATTER_SIZE; j++) {
        			matter.value[i][j] = Integer.parseInt(st.nextToken());
        		}
        	}
        	//원소 초기화
        	for(int i=0; i<MATTER_SIZE; i++) {
        		StringTokenizer st = new StringTokenizer(br.readLine());
        		for(int j=0; j<MATTER_SIZE; j++) {
        			matter.element[i][j] = st.nextToken().charAt(0);
        		}
        	}
        }
        
        //돌린거 저장
        for(int i=0; i<n; i++) {
        	for(int j=1; j<4; j++) {
        		matters[i][j] = rotate(matters[i][j-1]);
        	}
        }
        
        //가마 초기화
        kiln = new Matter(KILN_SIZE);
        for(int i=0; i<KILN_SIZE; i++) {
        	for(int j=0; j<KILN_SIZE; j++) {
        		kiln.element[i][j] = 'W';
        	}
        }
        
        order = new int[ORDER_SIZE][3];
        visited = new boolean[n];
        result = 0;
        dfs(0);
        
        System.out.println(result);
    }
    
    private static Matter rotate(Matter matter) {
    	Matter temp = new Matter(MATTER_SIZE);
    	
    	for(int i=0; i<MATTER_SIZE; i++) {
    		for(int j=0; j<MATTER_SIZE; j++) {
    			temp.value[i][j] = matter.value[MATTER_SIZE-1-j][i];
    			temp.element[i][j] = matter.element[MATTER_SIZE-1-j][i];
    		}
    	}
    	
    	return temp;
    }
    
    private static void dfs(int dept) {
    	if(dept == ORDER_SIZE) {
    		kiln.clear();
    		for(int i=0; i<3; i++) {
    			Matter matter = matters[order[i][0]][order[i][1]];
    			putMatter(matter, position[order[i][2]][0], position[order[i][2]][1]);
    		}
    		
    		int total = calcul();
    		result = Math.max(result, total);
    		return;
    	}
    	
    	for(int i=0; i<n; i++) {
    		if(visited[i]) continue;
    		visited[i] = true;
    		order[dept][0] = i;
    		for(int repeat=0; repeat<4; repeat++) {
    			order[dept][1] = repeat;
    			for(int idx=0; idx<position.length; idx++) {
    				order[dept][2] = idx;
        			dfs(dept+1);
        		}
    		}
    		visited[i] = false;
    	}
    }

	private static void putMatter(Matter matter, int x, int y) {
		for(int i=0; i<MATTER_SIZE; i++) {
			for(int j=0; j<MATTER_SIZE; j++) {
				int ni = i+x, nj = j+y;
				//품질 변경
				int value = kiln.value[ni][nj] + matter.value[i][j];
				if(value < 0) value = 0;
				if(value > 9) value = 9;
				kiln.value[ni][nj] = value;
				
				//원소 변경
				if(matter.element[i][j] == 'W') continue;
				kiln.element[ni][nj] = matter.element[i][j];
			}
		}
	}

	private static int calcul() {
		int r=0, b=0, g=0, y=0;
		
        for(int i=0; i<KILN_SIZE; i++) {
        	for(int j=0; j<KILN_SIZE; j++) {
        		int value = kiln.value[i][j];
        		switch(kiln.element[i][j]) {
        		case 'R': r += value; break;
        		case 'B': b += value; break;
        		case 'G': g += value; break;
        		case 'Y': y += value; break;
        		default: break;
        		}
        	}
        }
		
		return 7*r + 5*b + 3*g + 2*y;
	}

	private static class Matter{
    	int[][] value;
    	char[][] element;
    	
    	public Matter(int size) {
    		value = new int[size][size];
    		element = new char[size][size];
    	}

    	public void clear() {
    		for(int i=0; i<value.length; i++) {
    			Arrays.fill(value[i], 0);
    			Arrays.fill(element[i], 'W');
    		}
    	}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<value.length; i++) {
				sb.append("[");
				for(int j=0; j<value.length-1; j++) {
					sb.append(value[i][j]+""+element[i][j]+", ");
				}
				sb.append(value[i][value.length-1]+""+element[i][value.length-1]+"");
				sb.append("]\n");
			}
			return sb.toString().trim();
		}
    }
}
