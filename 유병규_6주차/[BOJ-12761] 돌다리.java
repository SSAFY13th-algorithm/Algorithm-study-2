import java.io.*;
import java.util.*;


public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {n, 0});

        boolean[] visited = new boolean[100001];
        while(!queue.isEmpty()) {
            int[] current = queue.poll();

            if(current[0] == m) {
                System.out.println(current[1]);
                return;
            }


            if(current[0]+1 <= 100000 && !visited[current[0]+1]) {
                queue.offer(new int[] {current[0]+1, current[1]+1});
                visited[current[0]+1] = true;
            }
            if(current[0]+a <= 100000 && !visited[current[0]+a]) {
                queue.offer(new int[] {current[0]+a, current[1]+1});
                visited[current[0]+a] = true;
            }
            if(current[0]+b <= 100000 && !visited[current[0]+b]) {
                queue.offer(new int[] {current[0]+b, current[1]+1});
                visited[current[0]+b] = true;
            }
            if(current[0] != 0 && current[0]*a <= 100000 && !visited[current[0]*a]) {
                queue.offer(new int[] {current[0]*a, current[1]+1});
                visited[current[0]*a] = true;
            }
            if(current[0] != 0 && current[0]*b <= 100000 && !visited[current[0]*b]) {
                queue.offer(new int[] {current[0]*b, current[1]+1});
                visited[current[0]*b] = true;
            }
            if(current[0]-1 >= 0 && !visited[current[0]-1]) {
                queue.offer(new int[] {current[0]-1, current[1]+1});
                visited[current[0]-1] = true;
            }
            if(current[0]-a >= 0 && !visited[current[0]-a]) {
                queue.offer(new int[] {current[0]-a, current[1]+1});
                visited[current[0]-a] = true;
            }
            if(current[0]-b >= 0 && !visited[current[0]-b]) {
                queue.offer(new int[] {current[0]-b, current[1]+1});
                visited[current[0]-b] = true;
            }

        }
    }
}
