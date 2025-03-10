import java.io.*;
import java.util.*;

public class boj10836 {
    // 입출력을 위한 버퍼 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 벌집의 크기와 성장 정보를 저장할 배열
    private static int[][] map;        // 벌집의 크기를 저장할 2차원 배열
    private static int[][] grow;       // 일별 성장 정보를 저장할 2차원 배열
    private static int n, m;           // n: 날짜 수, m: 벌집의 크기

    public static void main(String[] args) throws IOException {
        // 초기화
        init();

        // 모든 날짜에 대해 성장 처리
        for (int day = 0; day < n; day++) {
            firstGrow(day); // 테두리(첫 번째 열과 첫 번째 행)만 성장 처리
        }
        otherGrow(); // 나머지 내부 벌집 성장 처리 (모든 날짜가 끝난 후 한 번만)

        // 결과 출력을 위한 문자열 구성
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(map[i][j]).append(" ");
            }
            sb.setLength(sb.length() - 1); // 마지막 공백 제거
            sb.append("\n");
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력값을 받아 초기 설정을 하는 함수
     */
    private static void init() throws IOException {
        // m(벌집 크기)과 n(날짜 수) 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        // 벌집과 성장 정보 배열 초기화
        map = new int[m][m];   // m x m 크기의 벌집
        grow = new int[n][3];  // n일 동안의 성장 정보 (0, 1, 2 성장에 대한 개수)

        // 모든 벌집을 1로 초기화 (초기 크기)
        for (int i = 0; i < m; i++) {
            Arrays.fill(map[i], 1);
        }

        // 일별 성장 정보 입력 받기
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            grow[i][0] = Integer.parseInt(st.nextToken()); // 0만큼 자라는 개수
            grow[i][1] = Integer.parseInt(st.nextToken()); // 1만큼 자라는 개수
            grow[i][2] = Integer.parseInt(st.nextToken()); // 2만큼 자라는 개수
        }
    }

    /**
     * 테두리(첫 번째 열과 첫 번째 행)의 성장을 처리하는 함수
     * @param day 현재 처리할 날짜
     */
    private static void firstGrow(int day) {
        int index = 0; // 현재 적용할 성장 값(0, 1, 2)

        // 첫 번째 열 처리 (아래에서 위로, 맨 아래 제외)
        for (int i = m - 1; i > 0; i--) {
            // 현재 인덱스의 성장값이 모두 사용되었으면 다음 인덱스로 이동
            while (grow[day][index] == 0) index++;
            map[i][0] += index; // 현재 벌집에 성장값 적용
            grow[day][index]--; // 사용한 성장값 개수 감소
        }

        // 첫 번째 행 처리 (왼쪽에서 오른쪽으로)
        for (int i = 0; i < m; i++) {
            // 현재 인덱스의 성장값이 모두 사용되었으면 다음 인덱스로 이동
            while (grow[day][index] == 0) index++;
            map[0][i] += index; // 현재 벌집에 성장값 적용
            grow[day][index]--; // 사용한 성장값 개수 감소
        }
    }

    /**
     * 내부 벌집의 성장을 처리하는 함수
     * 내부 벌집은 왼쪽, 왼쪽 위, 위쪽 중 최댓값을 따라 자라지만,
     * 문제의 성장 패턴에 따라 항상 같은 열의 첫 번째 행 값이 최댓값이 됨
     */
    private static void otherGrow() {
        // 내부 모든 벌집 처리 (첫 번째 행과 열을 제외한 나머지)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < m; j++) {
                // 각 내부 벌집은 같은 열의 첫 번째 행 값과 동일하게 설정
                // 이는 성장 패턴의 특성 때문임 (항상 위쪽 값이 최댓값이 됨)
                map[i][j] = map[0][j];
            }
        }
    }
}