import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main17225 {
	
	static int present_num = 1;
	static int sangmin_work_num = 0;
	static int jisoo_work_num = 0;
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int sangmin = Integer.parseInt(st.nextToken());
        int jisoo = Integer.parseInt(st.nextToken());
        int yesterday_num = Integer.parseInt(st.nextToken());
        
        int[] order_time = new int [86400 + 1];
        int[] order_num = new int [86400 + 1];
        
        int[] sangmin_time_and_num = new int [86400 + 1]; 
        int[] jisoo_time_and_num = new int [86400 + 1]; 
        
        for (int i = 0; i < yesterday_num; i++) {
            st = new StringTokenizer(br.readLine());
            int t1 = Integer.parseInt(st.nextToken());
            String color = st.nextToken();
            int m1 = Integer.parseInt(st.nextToken());
            if (color.equals("B")) {
            	sangmin_time_and_num[t1] = m1;
            } else {
            	jisoo_time_and_num[t1] = m1;
            }
            
        }
        
        int[] sangmin_ret = new int [8640000 + 1];
        int[] jisoo_ret = new int [8640000 + 1];
        
        int time = 0;
        int sangmin_working = 0;
        int jisoo_working = 0;
        
        while (true) {
        	if (++time <= 86400) {
        		sangmin_work_num += sangmin_time_and_num[time];
        		jisoo_work_num += jisoo_time_and_num[time];
        	}
        	if (sangmin == 0) {
        		subtask1(sangmin_ret, sangmin_work_num);
        		sangmin_work_num = 0;
        	} else if (sangmin_working == 0 && sangmin_work_num > 0) {
        		sangmin_working = sangmin - 1;
        		sangmin_work_num--;
        		sangmin_ret[present_num++]++;
        		sangmin_ret[0]++;
        	} else if (sangmin_working > 0) {
        		sangmin_working--;
        	}
        	
        	if (jisoo == 0) {
        		subtask1(jisoo_ret, jisoo_work_num);
        		jisoo_work_num = 0;
        	} else if (jisoo_working == 0 && jisoo_work_num > 0) {
        		jisoo_working = jisoo - 1;
        		jisoo_work_num--;
        		jisoo_ret[present_num++]++;
        		jisoo_ret[0]++;
        	} else if (jisoo_working > 0) {
        		jisoo_working--;
        	}
        	
        	if (time > 86400 && sangmin_work_num == 0 && jisoo_work_num == 0)
        		break;
        }
       
                
        System.out.println(sangmin_ret[0]);
        StringBuffer sb = new StringBuffer("");
        for (int i = 1; i < sangmin_ret.length; i++) {
        	if (sangmin_ret[i] > 0) {
    			sb.append(i);
    			sb.append(" ");
        	}
        }
        System.out.println(sb);
        
        System.out.println(jisoo_ret[0]);
        StringBuffer sb2 = new StringBuffer("");
        for (int i = 1; i < jisoo_ret.length; i++) {
        	if (jisoo_ret[i] > 0) {
    			sb2.append(i);
    			sb2.append(" ");
        	}
        }
        System.out.println(sb2);
    }
    
    static void subtask1(int[] arr, int num) {
    	while (num-- > 0) {
    		arr[present_num++]++;
    		arr[0]++;
    	}
    }

}
