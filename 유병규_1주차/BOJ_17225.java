import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    private static PriorityQueue<int[]> workList;

    private static List<Integer> sangmin = new ArrayList<>();
    private static List<Integer> jisoo = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");
        int a = Integer.parseInt(inputs[0]);
        int b = Integer.parseInt(inputs[1]);
        int n = Integer.parseInt(inputs[2]);

        workList = new PriorityQueue<>((o1, o2) -> {
            if(o1[1] < o2[1]) return -1;
            if(o1[1] > o2[1]) return 1;
            if(o1[0] < o2[0]) return -1;
            return 1;
        });

        int sangminTime=0, jisooTime=0;
        for(int i=0; i<n; i++) {
            String[] line = br.readLine().split(" ");
            int time = Integer.parseInt(line[0]);
            String color = line[1];
            int count = Integer.parseInt(line[2]);

            if(color.equals("B")) {
                time = sangminTime < time ? time : sangminTime;
                for(int j=0; j<count; j++) {
                    sangminTime = time+j*a;
                    int[] work = {0, time+j*a};
                    workList.offer(work);
                }
                sangminTime += a;
                continue;
            }
            time = jisooTime < time ? time : jisooTime;
            for(int j=0; j<count; j++) {
                jisooTime = time+j*b;
                int[] work = {1, time+j*b};
                workList.offer(work);
            }
            jisooTime += b;
        }

        int boxNum = 1;
        while(!workList.isEmpty()) {
            int[] work = workList.poll();

            if(work[0] == 0) {
                sangmin.add(boxNum++);
            }
            else {
                jisoo.add(boxNum++);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(sangmin.size()).append("\n");
        for(int num : sangmin) {
            sb.append(num).append(" ");
        }
        sb.append("\n").append(jisoo.size()).append("\n");
        for(int num : jisoo) {
            sb.append(num).append(" ");
        }

        System.out.println(sb.toString().trim());
    }
}