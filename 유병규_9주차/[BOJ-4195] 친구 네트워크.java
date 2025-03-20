import java.io.*;
import java.util.*;

public class Main {
	private static Map<String, Object> map;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        
        StringBuilder sb = new StringBuilder();
        for(int testCase=0; testCase<T; testCase++) {
        	map = new HashMap<>();
        	int[] groupCnt = new int[100000];
        	int groupNum = 0;
        	
        	int repeat = Integer.parseInt(br.readLine());
        	for(int i=0; i<repeat; i++) {
        		String[] names = br.readLine().split(" ");
        		
        		if(map.containsKey(names[0]) && map.containsKey(names[1])) {
        			String root1 = getRoot(names[0]);
        			String root2 = getRoot(names[1]);
        			
        			if(!root1.equals(root2)) {
        				groupCnt[(int) map.get(root1)] += groupCnt[(int) map.get(root2)];
        				groupCnt[(int) map.get(root2)] = 0;
        				map.put(root2, root1);
        			}
        		}
        		else if(map.containsKey(names[0])) {
        			String root = getRoot(names[0]);
        			map.put(names[1], root);
        			groupCnt[(int) map.get(root)]++;
        		}
        		else if(map.containsKey(names[1])) {
        			String root = getRoot(names[1]);
        			map.put(names[0], root);
        			groupCnt[(int) map.get(root)]++;
        		}
        		else {
        			map.put(names[0], groupNum);
        			map.put(names[1], names[0]);
        			groupCnt[groupNum] += 2;
        			groupNum++;
        		}
        		
    			sb.append(groupCnt[(int) map.get(getRoot(names[0]))]).append("\n");
        	}
        }
        
        System.out.println(sb.toString().trim());
    }

	private static String getRoot(String key) {
		Object value = map.get(key); 
		if(value instanceof Integer) return key;
		String root = getRoot((String) value); 
		map.put(key, root);
		return root;
	}
}
