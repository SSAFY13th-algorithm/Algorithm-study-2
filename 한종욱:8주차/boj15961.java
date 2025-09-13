import java.io.*;
import java.util.*;
public class boj15961 {
    // 입출력을 위한 버퍼 리더와 라이터 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 초밥 벨트 정보를 저장할 배열
    private static int[] sushi;

    // n: 접시의 수, d: 초밥의 가짓수, k: 연속해서 먹는 접시의 수, c: 쿠폰 번호
    private static int n, d, k, c;

    public static void main(String[] args) throws IOException {
        // 입력 및 초기화
        init();

        // 투 포인터 알고리즘으로 최대 초밥 가짓수 계산
        int answer = twoPointer();

        // 결과 출력 및 리소스 정리
        bw.write(answer + "");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받아 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 첫 번째 줄 입력: n, d, k, c
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 접시의 수
        d = Integer.parseInt(st.nextToken()); // 초밥의 가짓수
        k = Integer.parseInt(st.nextToken()); // 연속해서 먹는 접시의 수
        c = Integer.parseInt(st.nextToken()); // 쿠폰 번호

        // 초밥 벨트 배열 초기화
        sushi = new int[n];

        // 각 접시에 놓인 초밥 종류 입력
        for (int i = 0; i < n; i++) {
            sushi[i] = Integer.parseInt(br.readLine());
        }
    }

    /**
     * 투 포인터 알고리즘을 사용하여 최대 초밥 가짓수를 찾는 메소드
     * @return 먹을 수 있는 초밥의 최대 가짓수
     */
    private static int twoPointer() {
        int start = 0;  // 시작 포인터 (현재 윈도우의 첫 접시)
        int end = 0;    // 끝 포인터 (다음에 추가할 접시)
        int max = 0;    // 최대 초밥 가짓수
        int cnt = 0;    // 현재 윈도우에 있는 접시의 수

        // 각 초밥 종류별 개수를 저장할 해시맵
        Map<Integer, Integer> map = new HashMap<>();

        // 시작 포인터가 전체 접시 수를 넘지 않을 때까지 반복
        while (start < n) {
            // 현재 윈도우의 접시 수가 k보다 작으면 접시 추가
            if (cnt < k) {
                // 현재 end 포인터 위치의 초밥을 해시맵에 추가 (또는 개수 증가)
                map.put(sushi[end], map.getOrDefault(sushi[end], 0) + 1);
                cnt++; // 접시 수 증가
            }

            // 현재 윈도우의 접시 수가 k가 되면 최대값 계산 후 시작 포인터 이동
            if (cnt == k) {
                // 현재 윈도우에 있는 초밥 종류의 수
                int result = map.size();

                // 쿠폰 초밥이 현재 윈도우에 없다면 가짓수 1 증가
                if (!map.containsKey(c)) {
                    result++;
                }

                // 최대값 갱신
                max = Math.max(max, result);

                // 시작 포인터 위치의 초밥 제거 (또는 개수 감소)
                map.put(sushi[start], map.get(sushi[start]) - 1);
                cnt--; // 접시 수 감소

                // 해당 초밥의 개수가 0이 되면 해시맵에서 제거
                if (map.get(sushi[start]) == 0) map.remove(sushi[start]);

                // 시작 포인터 이동
                start++;
            }

            // 끝 포인터 이동 (원형 벨트이므로 모듈러 연산 사용)
            end = (end + 1) % n;
        }

        return max;
    }
}