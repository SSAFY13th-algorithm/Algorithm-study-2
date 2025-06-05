import java.io.*;
import java.util.*;

public class Main {
    private static int n, m;
    private static int[] cost;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        //cost[i]: i번 놀이기구 운행 시간
        cost = new int[m+1];
        long min = Integer.MAX_VALUE; //가장 작은 운행 시간
        for(int i=1; i<=m; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
            min = Math.min(min, cost[i]);
        }

        //놀이기구 개수보다 사람이 적다면 바로 반환
        if(n <= m) {
            System.out.println(n);
            return;
        }

        //이분 탐색(모든 사람이 탈 수 있는 최소 시간)
        long left = 0;
        long right = n*min;//모두 운행시간이 가장 작은 놀이기구를 탔을 때의 시간
        long minTime = right;//찾고자 하는 최소 시간

        while(left <= right) {
            long mid = left + (right-left)/2;

            long count = counting(mid);
            if(count >= n) {
                minTime = Math.min(minTime, mid);
                right = mid-1;
                continue;
            }
            left = mid+1;
        }

        //모든 사람이 탈 수 있는 시간의 1분 전에 총 몇 명이 탑승할 수 있는지
        long count = 0;
        long time = minTime-1;
        //wait[i]: 다음 사람이 i번 놀이기구를 타기 위해 기다려야 하는 대기 시간
        long[] wait = new long[m+1];
        for(int i=1; i<=m; i++) {
            //사람 수 계산
            if(time/cost[i] == 0) count += 1;
            else if(time%cost[i] == 0) count += time/cost[i];
            else count += time/cost[i]+1;
            //대기 시간 계산
            wait[i] = time%cost[i] == 0 ? 0 : cost[i] - time%cost[i];
        }

        //남은 사람 수
        long rest = n-count;
        //대기 시간이 가장 짧은 순으로 놀이기구 번호 정렬
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {
            if(wait[o1] == wait[o2]) return Integer.compare(o1, o2);
            return Long.compare(wait[o1], wait[o2]);
        });
        for(int i=1; i<=m; i++) {
            pq.add(i);
        }

        //마지막 사람이 타는 놀이기구 번호
        long result = 0;
        for(int i=0; i<rest; i++) {
            result = pq.poll();
        }
        System.out.println(result);
    }

    private static long counting(long time) {
        //time 동안 탈 수 있는 총 사람 수 계산
        long count = 0;
        for(int i=1; i<=m; i++) {
            if(time/cost[i] == 0) count += 1;
            else if(time%cost[i] == 0) count += time/cost[i];
            else count += time/cost[i]+1;
        }

        return count;
    }
}
