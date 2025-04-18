package study13week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main16940_BFS스페셜저지 {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static int N;
  static List<Integer>[] nodes;
  static boolean[] visited;
  static Queue<Integer> input = new LinkedList<>();
  static boolean isOk = true;

  public static void main(String[] args) throws IOException {
    init();
    solve(input.poll());
    if (isOk)
      System.out.println(1);
    else
      System.out.println(0);
  }

  static void solve(int index) {
    if (index != 1) {
      isOk = false;
      return;
    }
    Queue<HashSet<Integer>> q = new LinkedList<>();
    
    visited[index] = true;
    HashSet<Integer> set = new HashSet<>();
    for (int a: nodes[index]) {
      if (!visited[a])
        set.add(a);
    }
    q.add(set);
    
    while (!input.isEmpty()) {
      int size = q.size();
      
      while (size-- > 0) {
        HashSet<Integer> s = q.poll();
        
        while (!s.isEmpty()) {
          int next = input.poll();
          
          HashSet<Integer> new_set = new HashSet<>();
          if (!s.contains(next)) {
            isOk = false;
            return;
          }
          
          visited[next] = true;
          for (int a: nodes[next]) {
            if (!visited[a])
              new_set.add(a);
          }
          q.add(new_set);
          s.remove(next);
        }
        
      }
    }
  }

  static void init() throws IOException {
    N = Integer.parseInt(br.readLine());

    nodes = new ArrayList[N + 1];
    visited = new boolean[N + 1];

    for (int i = 1; i <= N; i++) {
      nodes[i] = new ArrayList<>();
    }

    for (int i = 0; i < N - 1; i++) {
      st = new StringTokenizer(br.readLine());
      int from = Integer.parseInt(st.nextToken());
      int to = Integer.parseInt(st.nextToken());

      nodes[from].add(to);
      nodes[to].add(from);
    }

    st = new StringTokenizer(br.readLine());
    for (int i = 0; i < N; i++)
      input.add(Integer.parseInt(st.nextToken()));

  }
}