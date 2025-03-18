import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class boj16564 {
    // 입력을 효율적으로 읽기 위한 BufferedReader
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 출력을 효율적으로 쓰기 위한 BufferedWriter
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 캐릭터들의 레벨을 저장할 배열
    private static int[] levels;

    // n: 캐릭터의 수, k: 사용 가능한 포인트, min: 가장 낮은 레벨
    private static int n, k, min;

    public static void main(String[] args) throws IOException {
        // 입력 데이터와 변수 초기화
        init();

        // 이진 탐색을 통해 달성 가능한 최대 레벨 찾기
        bw.write(binarySearch() + "\n");

        // 출력 버퍼 비우기 및 리소스 닫기
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 데이터 초기화 메서드
    private static void init() throws IOException {
        // 첫 줄에서 n(캐릭터 수)과 k(사용 가능한 포인트) 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 레벨 배열 생성 및 최소 레벨 찾기
        levels = new int[n];
        min = (int)1e9;

        // 각 캐릭터의 현재 레벨 입력 및 최소 레벨 갱신
        for (int i = 0; i < n; i++) {
            levels[i] = Integer.parseInt(br.readLine());
            min = Math.min(min, levels[i]);
        }
    }

    // 이진 탐색을 통해 최대 달성 가능한 레벨 찾는 메서드
    private static int binarySearch() {
        // 탐색 범위 설정: 최소 레벨부터 (최소 레벨 + 사용 가능한 포인트)까지
        int left = min;
        int right = min + k;

        while (left <= right) {
            // 중간값 계산 (오버플로우 방지를 위한 최적화된 방식)
            int mid = left + (right - left) / 2;

            // 현재 중간값 레벨로 모든 캐릭터를 업그레이드할 수 있는지 확인
            if (isPossible(mid)) {
                // 가능하다면 더 높은 레벨 탐색
                left = mid + 1;
            }
            else {
                // 불가능하다면 더 낮은 레벨 탐색
                right = mid - 1;
            }
        }

        // 최대로 달성 가능한 레벨 반환
        return right;
    }

    // 특정 목표 레벨로 모든 캐릭터를 업그레이드할 수 있는지 확인하는 메서드
    private static boolean isPossible(int target) {
        // 목표 레벨로 업그레이드하는 데 필요한 총 포인트 계산
        long needPoints = 0;
        for (int level : levels) {
            // 현재 레벨이 목표 레벨보다 낮은 경우 필요한 포인트 추가
            if (level < target) {
                needPoints += (target - level);
            }
        }

        // 필요한 포인트가 사용 가능한 포인트(k)보다 작거나 같은지 확인
        return needPoints <= k;
    }
}