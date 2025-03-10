import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * 햄버거 먹는 문제 - 재귀적 구조의 햄버거에서 특정 위치까지 먹었을 때 패티의 개수를 계산
 */
public class boj16974 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static long[] burger;   // 각 레벨별 햄버거의 총 길이를 저장하는 배열
    private static long[] patties;  // 각 레벨별 햄버거에 포함된 패티의 총 개수를 저장하는 배열
    private static int n;           // 햄버거의 레벨
    private static long x;          // 먹을 햄버거의 길이

    public static void main(String[] args) throws IOException {
        // 입력을 받고 초기화
        init();

        // 패티 개수 계산 및 출력
        bw.write(countPatties(n, x) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 햄버거와 패티 배열을 초기화하는 메소드
     */
    private static void init() throws IOException {
        // 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 햄버거 레벨
        x = Long.parseLong(st.nextToken());    // 먹을 햄버거 길이

        // 배열 초기화
        burger = new long[n + 1];   // 각 레벨별 햄버거 길이
        patties = new long[n + 1];  // 각 레벨별 패티 개수

        // 레벨 0 햄버거는 패티 하나 (P)
        burger[0] = 1;  // 패티 하나의 길이
        patties[0] = 1; // 패티 개수

        // 각 레벨별 햄버거 길이와 패티 수 계산
        // 레벨 L 햄버거 = B + 레벨 L-1 햄버거 + P + 레벨 L-1 햄버거 + B
        // B는 빵(길이 1), P는 패티(길이 1)
        for (int i = 1; i <= n; i++) {
            burger[i] = 2 * burger[i - 1] + 3;  // 총 길이: 이전 레벨 햄버거 2개 + 빵 2개 + 패티 1개
            patties[i] = 2 * patties[i - 1] + 1; // 패티 수: 이전 레벨 패티 수 2배 + 중간 패티 1개
        }
    }

    /**
     * 레벨 level의 햄버거에서 x만큼 먹었을 때 먹은 패티의 개수를 계산하는 메소드
     *
     * @param level 햄버거의 레벨
     * @param x 먹은 햄버거의 길이
     * @return 먹은 패티의 개수
     */
    private static long countPatties(int level, long x) {
        // 기저 사례: 레벨 0 햄버거는 패티 하나
        if (level == 0) return patties[0];

        // 첫 번째 빵만 먹었을 경우
        if (x == 1) return 0;

        // 햄버거 구조: B + [레벨 L-1 햄버거] + P + [레벨 L-1 햄버거] + B

        // 케이스 1: 첫 번째 빵과 첫 번째 레벨 L-1 햄버거의 일부를 먹은 경우
        if (x <= 1 + burger[level - 1]) {
            return countPatties(level - 1, x - 1);  // 빵 이후부터의 패티 개수 계산
        }

        // 케이스 2: 첫 번째 빵, 첫 번째 레벨 L-1 햄버거, 중간 패티까지 먹은 경우
        if (x == 1 + burger[level - 1] + 1) {
            return patties[level - 1] + 1;  // 첫 번째 레벨 L-1 햄버거의 패티 + 중간 패티
        }

        // 케이스 3: 첫 번째 빵, 첫 번째 레벨 L-1 햄버거, 중간 패티, 두 번째 레벨 L-1 햄버거의 일부를 먹은 경우
        if (x <= 1 + burger[level - 1] + 1 + burger[level - 1]) {
            // 첫 번째 레벨 L-1 햄버거의 패티 + 중간 패티 + 두 번째 레벨 L-1 햄버거에서 먹은 패티
            return patties[level - 1] + 1 + countPatties(level - 1, x - (1 + burger[level - 1] + 1));
        }

        // 케이스 4: 햄버거 전체를 먹은 경우
        return patties[level];  // 전체 패티 개수
    }
}