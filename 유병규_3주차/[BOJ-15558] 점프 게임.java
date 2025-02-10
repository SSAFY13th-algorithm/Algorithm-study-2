import java.io.*;
import java.util.*;

/*
 * 1에서 시작, n넘어가면 성공(성공하면 1, 아니면 0 출력)
 * 0이 위험, 1이 안전
 * 1. 앞으로 이동
 * 2. 뒤로 이동
 * 3. 옆 줄의 k칸 앞으로 이동
 * */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] line = new int[2][n];
        String input = br.readLine();
        for(int i=0; i<n; i++) {
            line[0][i] = input.charAt(i) - '0';
        }
        input = br.readLine();
        for(int i=0; i<n; i++) {
            line[1][i] = input.charAt(i) - '0';
        }

        //bfs
        boolean[][] visited = new boolean[2][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0,0,0});
        int[] move = {1, -1, k};

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            for(int i=0; i<3; i++) {
                int ny = current[1] + move[i];
                int nx = current[0];
                int time = current[2];

                if(i == 2) {
                    nx = 1 - current[0];
                }

                if(ny>=n) {
                    System.out.println(1);
                    return;
                }

                if(ny <= time) continue;
                if(visited[nx][ny]) continue;
                if(line[nx][ny] == 0) continue;

                visited[nx][ny] = true;
                queue.offer(new int[] {nx, ny, time+1});
            }
        }
        System.out.println(0);
    }

}
