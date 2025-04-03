import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class boj4195 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // Union-Find를 위한 맵들
    private static Map<String, String> map;  // 부모 노드를 저장하는 맵 (parent)
    private static Map<String, Integer> size;  // 각 집합의 크기를 저장하는 맵

    private static int f;  // 친구 관계의 수

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());  // 테스트 케이스 수

        // 각 테스트 케이스 처리
        for (int i = 0; i < t; i++) {
            init();  // 초기화 및 친구 관계 처리
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 각 테스트 케이스의 초기화 및 처리
    private static void init() throws IOException {
        f = Integer.parseInt(br.readLine());  // 친구 관계의 수
        map = new HashMap<>();  // 부모 노드 맵 초기화
        size = new HashMap<>();  // 집합 크기 맵 초기화

        // 각 친구 관계 처리
        for (int i = 0; i < f; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String name1 = st.nextToken();  // 첫 번째 친구 이름
            String name2 = st.nextToken();  // 두 번째 친구 이름

            // 처음 등장하는 이름이면 자기 자신을 부모로 초기화
            if (!map.containsKey(name1)) map.put(name1, name1);
            if (!map.containsKey(name2)) map.put(name2, name2);

            // 처음 등장하는 이름이면 집합 크기를 1로 초기화
            if (!size.containsKey(name1)) size.put(name1, 1);
            if (!size.containsKey(name2)) size.put(name2, 1);

            // 두 친구를 같은 집합으로 합침
            union(name1, name2);

            // 합친 후의 집합 크기 출력
            sb.append(Math.max(size.get(find(name1)), size.get(find(name2)))).append("\n");
        }
    }

    // 두 집합을 합치는 메소드
    private static void union(String name1, String name2) {
        String N1 = find(name1);  // name1의 루트 찾기
        String N2 = find(name2);  // name2의 루트 찾기

        // 이미 같은 집합이면 합치지 않음
        if (N1.equals(N2)) return;

        // 더 작은 집합을 더 큰 집합에 합침 (최적화)
        if (size.get(N1) < size.get(N2)) {
            map.put(N1, N2);  // N1의 부모를 N2로 설정
            size.put(N2, size.get(N2) + size.get(N1));  // N2 집합의 크기 증가
        } else {
            map.put(N2, N1);  // N2의 부모를 N1으로 설정
            size.put(N1, size.get(N2) + size.get(N1));  // N1 집합의 크기 증가
        }
    }

    // 경로 압축을 사용하여 요소의 루트를 찾는 메소드
    private static String find(String x) {
        if (map.get(x).equals(x)) {
            return x;  // 자기 자신이 루트인 경우
        } else {
            String parent = find(map.get(x));  // 재귀적으로 루트 찾기
            map.put(x, parent);  // 경로 압축: 루트를 바로 가리키도록 설정
            return parent;
        }
    }
}