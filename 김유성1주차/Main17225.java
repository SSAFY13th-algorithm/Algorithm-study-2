import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main17225 {
	
	static int present_num = 1; // 선물의 번호
	static int sangmin_work_num = 0; // 상민의 포장해야 할 선물 수
	static int jisoo_work_num = 0; // 지수의 포장해야 할 선물 수
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int sangmin = Integer.parseInt(st.nextToken());
        int jisoo = Integer.parseInt(st.nextToken());
        int yesterday_num = Integer.parseInt(st.nextToken());
        
        // 상민과 지수의 주문시간에 받은 선물의 수
        int[] sangmin_time_and_num = new int [86400 + 1]; 
        int[] jisoo_time_and_num = new int [86400 + 1]; 
        
        // 입력값을 상민, 지수인 경우로 나눠서 저장
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
        
        // 상민, 지수의 선물 포장 번호를 저장할 배열. 최대로 받을 수 있는 수만큼 설정
        int[] sangmin_ret = new int [8640000 + 1];
        int[] jisoo_ret = new int [8640000 + 1];
        
        int time = 0; // 현재 시간
        int sangmin_working = 0; // 상민이 일하고 있는지
        int jisoo_working = 0; // 지수가 일하고 있는지
        
        while (true) {
        	// 각 시간마다 주문이 있으면 일거리를 추가
        	if (++time <= 86400) {
        		sangmin_work_num += sangmin_time_and_num[time];
        		jisoo_work_num += jisoo_time_and_num[time];
        	}
        	if (sangmin == 0) { // 능률이 0일 경우(한번에 다 할 때)
        		subtask1(sangmin_ret, sangmin_work_num);
        		sangmin_work_num = 0;
        	} else if (sangmin_working == 0 && sangmin_work_num > 0) { // 일하지 않고 있고, 일거리가 있을 때
        		sangmin_working = sangmin - 1; // 일거리를 받았을 때, 바로 시작 하므로 시간을 1 빼준다.
        		sangmin_work_num--;
        		sangmin_ret[present_num++]++;
        		sangmin_ret[0]++;
        	} else if (sangmin_working > 0) { // 현재 일하고 있으면, 남은 시간을 계산.
        		sangmin_working--;
        	}
        	
        	// 이름만 다를 뿐, 위와 같은 로직.
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
        	
        	// 주문시간을 초과했고, 이후에 둘 다 일거리가 없으면 끝.
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
    
    // 일 능률이 0이면 한번에 다 포장
    static void subtask1(int[] arr, int num) {
    	while (num-- > 0) {
    		arr[present_num++]++;
    		arr[0]++;
    	}
    }

}
