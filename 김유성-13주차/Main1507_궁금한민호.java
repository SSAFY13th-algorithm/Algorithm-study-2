package study13week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main1507_궁금한민호 {
  static class Node implements Comparable<Node> {
    int num;
    int len;
    
    public Node(int num, int len) {
      this.num = num;
      this.len = len;
    }
    
    @Override
    public int compareTo(Node o) {
      return Integer.compare(this.len, o.len);
    }
  }

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static int N, dist[][], ret;

  public static void main(String[] args) throws IOException {
      init();
      if (!isOk()) {
        System.out.println(-1);
        return;
      }
      
      for (int i = 0; i < N - 1; i++) {
        for (int j = i + 1; j < N; j++) {
          int temp = dist[i][j];
          dist[i][j] = 0;
          dist[j][i] = 0;
          
          if (bfs(i, j) > temp) {// i - j 간선을 지운다.
            dist[i][j] = temp;
            dist[j][i] = temp;
          } else {
            ret -= temp;
          }
        }
      }
      
      System.out.println(ret);
  }
  
  static boolean isOk() {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (i == j) continue;
        for (int k = 0; k < N; k++) {
          if (j == k || i == k) continue;
          if (dist[i][j] > dist[i][k] + dist[k][j]) { // 직선 거리로 가는게 더 클 때
            return false;
          } 
        }
      }
    }
    return true;
  }
  
  static int bfs(int a, int b) {
    PriorityQueue<Node> pq = new PriorityQueue<>();
    int[][] distance = new int [N][N];
    for (int i = 0; i < N; i++) {
      Arrays.fill(distance[i], Integer.MAX_VALUE);
    }
    
    distance[a][a] = 0;
    pq.add(new Node(a, 0));
    
    while (!pq.isEmpty()) {
      Node n = pq.poll();
      
      if (n.num == b) {
        return n.len;
      }
      
      for (int i = 0; i < N; i++) {
        if (dist[n.num][i] > 0 && distance[n.num][i] > n.len) {
          distance[n.num][i] = n.len;
          pq.add(new Node(i, dist[n.num][i] + n.len));
        }
      }
    }
    
    return Integer.MAX_VALUE;
  }
  
  static void init() throws IOException {
    N = Integer.parseInt(br.readLine());
    
    dist = new int [N][N];
    for (int i = 0; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < N; j++) {
        dist[i][j] = Integer.parseInt(st.nextToken());
        ret += dist[i][j];
      }
    }
    ret /= 2;
  }
}
