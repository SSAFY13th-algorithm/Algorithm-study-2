import java.io.*;
import java.util.*;

/**
 * 백준 8980번: 택배
 * 문제 난이도: Gold 1
 * 문제 유형: 그리디, 정렬
 *
 * 문제 요약:
 * - N개의 마을을 지나는 트럭이 있음 (1번부터 N번까지 일직선상 위치)
 * - 트럭 용량은 C
 * - M개의 택배 박스가 있으며, 각 박스는 시작 마을, 도착 마을, 박스 개수로 정의
 * - 트럭이 1번 마을에서 출발하여 N번 마을까지 이동하면서 최대한 많은 박스를 배송
 * - 트럭이 지나온 마을로 되돌아갈 수 없음
 *
 * 시간복잡도: O(n × m log(m))
 * - n: 마을의 수
 * - m: 박스 정보의 수
 */
public class boj8980 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static PriorityQueue<int[]> truck;  // 현재 트럭에 실린 박스 정보 (도착지, 개수)
    private static List<List<int[]>> packages;  // 각 마을에서 보낼 박스 정보 (packages[i]: i번 마을에서 보낼 박스 목록)
    private static int n, c, m;       // n: 마을 수, c: 트럭 용량, m: 박스 정보 수

    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화

        int answer = greedy();  // 그리디 알고리즘으로 최대 배송량 계산

        // 결과 출력
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 필요한 데이터 구조를 초기화하는 함수
     * 시간복잡도: O(m log m) - 각 마을마다 박스를 도착지 기준 정렬
     */
    private static void init() throws IOException {
        // 입력 처리: 마을 수(n), 트럭 용량(c), 박스 정보 수(m)
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 마을 수
        c = Integer.parseInt(st.nextToken());  // 트럭 용량
        m = Integer.parseInt(br.readLine());   // 박스 정보 수

        // 각 마을에서 보낼 박스 정보를 저장할 리스트 초기화
        packages = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            packages.add(new ArrayList<>());
        }

        // 현재 트럭에 실려있는 박스 정보를 관리할 우선순위 큐 (도착지 기준 오름차순 정렬)
        truck = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[0], o2[0]));

        // 박스 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());  // 시작 마을
            int end = Integer.parseInt(st.nextToken());    // 도착 마을
            int capacity = Integer.parseInt(st.nextToken());  // 박스 개수

            // 시작 마을에 박스 정보 추가: [도착 마을, 박스 개수]
            packages.get(start).add(new int[]{end, capacity});
        }

        // 각 마을의 박스 정보를 도착지 기준으로 정렬 (가까운 도착지 우선)
        for (int i = 1; i <= n; i++) {
            Collections.sort(packages.get(i), (o1, o2) -> Integer.compare(o1[0], o2[0]));
        }
    }

    /**
     * 그리디 알고리즘으로 최대 배송량을 계산하는 함수
     * 시간복잡도: O(n × m log m)
     * @return 배송할 수 있는 최대 박스 개수
     */
    private static int greedy() {
        int result = 0;  // 배송한 박스 총 개수

        // 1번 마을부터 n번 마을까지 순회
        for (int i = 1; i <= n; i++) {
            // 현재 마을에 도착하면 필요한 처리를 위한 임시 리스트와 배열
            List<int[]> list = new ArrayList<>();  // 새로운 배송 계획을 저장할 리스트
            int[] temp = new int[n + 1];           // 각 도착지별 박스 개수를 합산할 배열

            // 1. 현재 마을(i)이 도착지인 박스 처리: 트럭에서 내려서 배송 완료 처리
            if (!truck.isEmpty() && truck.peek()[0] == i) {
                int[] pack = truck.poll();  // 현재 마을이 도착지인 박스를 트럭에서 내림
                result += pack[1];          // 배송 완료된 박스 개수를 결과에 더함
            }

            // 2. 현재 트럭에 실린 모든 박스를 임시 배열에 저장 (도착지별 합산)
            while (!truck.isEmpty()) {
                int[] pack = truck.poll();
                temp[pack[0]] += pack[1];  // 같은 도착지를 가진 박스들을 합산
            }

            // 3. 현재 마을(i)에서 보낼 모든 박스를 임시 배열에 추가
            for (int[] pack : packages.get(i)) {
                temp[pack[0]] += pack[1];  // 같은 도착지를 가진 박스들을 합산
            }

            // 4. 도착지별 박스 정보를 리스트로 변환 (도착지가 빠른 순서로 정렬됨)
            for (int j = 1; j <= n; j++) {
                if (temp[j] > 0) list.add(new int[] {j, temp[j]});
            }

            // 5. 트럭 용량을 고려하여 새로운 배송 계획 수립
            int remain = c;  // 트럭 최대 용량으로 초기화

            // 도착지가 가까운 박스부터 트럭에 싣기 (그리디 핵심)
            for (int[] pack : list) {
                if (remain == 0) break;  // 트럭이 다 찼으면 종료

                if (remain >= pack[1]) {
                    // 해당 도착지 박스를 모두 실을 수 있는 경우
                    truck.add(pack);
                    remain -= pack[1];
                } else {
                    // 일부만 실을 수 있는 경우
                    truck.add(new int[]{pack[0], remain});
                    remain = 0;
                }
            }
        }

        return result;
    }
}