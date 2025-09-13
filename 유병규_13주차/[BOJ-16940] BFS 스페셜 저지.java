import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //초기화
        int n = Integer.parseInt(br.readLine());

        List<Set<Integer>> graph = new ArrayList<>();
        for(int i=0; i<=n; i++) {
            graph.add(new HashSet<>());
        }
        for(int i=0; i<n-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] order = new int[n];
        for(int i=0; i<n; i++) {
            order[i] = Integer.parseInt(st.nextToken());
        }

        //result: 나올 수 있는 순서 집합
        List<Set<Integer>> result = new ArrayList<>();
        for(int i=0; i<=n; i++) {
            result.add(new HashSet<>());
        }
        // 이 문제는 1부터 시작
        result.get(0).add(1);
        //result 채우기
        boolean[] visited = new boolean[n+1];
        visited[1] = true;
        for(int i=0,idx=1; i<n; i++,idx++) {
            Set<Integer> set = graph.get(order[i]);
            for(int node : set) {
                if(visited[node]) continue;
                visited[node] = true;
                result.get(idx).add(node);
            }
        }

        //result(순서 집합)에 맞게 나오는지 체크
        int idx = 0;
        for(int i=0; i<n; i++) {
            Set<Integer> set = result.get(i);
            if(set.size() == 0) break;
            for(int j=0; j<set.size(); j++) {
                if(set.contains(order[idx])) {
                    idx++;
                    continue;
                }
                //만약 해당 순서에 나올 수 없는 숫자라면 0을 리턴
                System.out.println(0);
                return;
            }
        }
        //전부 통과했다면 1을 리턴
        System.out.println(1);
    }
}
