import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        //최소 거리 정보
        int[][] map = new int[n][n];
        //현재 도로 정보
        int[][] graph = new int[n][n];
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                map[i][j] = graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //필수 도로의 시간의 합 = 결과 값
        int result = 0;
        //다익스트라를 위한 자료 구조
        int[] dist = new int[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(dist[o1], dist[o2]));
        //모든 도로를 하나씩 제거해보며 해당 도로가 최소한 있어야 하는 필수 도로인지 판별
        //i와 j를 잇는 도로를 제거했을 때 i -> j로 가는 최단 거리를 계산
        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                //다익스트라를 위한 초기화
                pq.clear();
                Arrays.fill(dist, (int) 1e9);
                //i에서 출발
                dist[i] = 0;
                boolean[] visited = new boolean[n];
                pq.offer(i);

                while(!pq.isEmpty()) {
                    int current = pq.poll();

                    if(visited[current]) continue;
                    visited[current] = true;
                    //j까지의 최단거리만 구하면 됨
                    if(current == j) break;

                    for(int node=0; node<n; node++) {
                        if(visited[node]) continue;
                        //i와 j를 잇는 도로는 없다는 가정
                        if(current==i && node==j) continue;
                        if(current==j && node==i) continue;

                        if(dist[node] <= dist[current] + graph[current][node]) continue;
                        dist[node] = dist[current] + graph[current][node];

                        pq.offer(node);
                    }
                }

                //i와 j를 잇는 도로를 제거했을 때 i -> j로 가는 최단 거리가 다음과 같을 때
                //1. 제거 전보다 클 때 해당 도로는 필수 도로이다.
                if(dist[j] > map[i][j]) {
                    result += map[i][j];
//        			System.out.println((i+1)+"->"+(j+1));
                    continue;
                }
                //2. 제거 전보다 작다면 문제가 잘못됨.
                if(dist[j] < map[i][j]) {
                    System.out.println(-1);
                    return;
                }
                //3. 제거 전과 같다면 해당 도로는 필수 도로가 아니므로 도로 정보를 INF 값으로 설정하여 없는 도로로 설정
                graph[i][j] = (int) 1e9;
                graph[j][i] = (int) 1e9;
            }
        }

        System.out.println(result);
    }
}
