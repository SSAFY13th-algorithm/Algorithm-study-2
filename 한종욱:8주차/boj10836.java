import java.io.*;
import java.util.*;
import java.awt.*;
public class Main {
    // 입출력을 위한 버퍼 리더와 라이터 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 맵과 성장 정보를 저장할 2차원 배열
    private static int[][] map;    // 각 위치의 높이를 저장하는 배열
    private static int[][] grow;   // 성장 정보를 저장하는 배열

    // 맵 크기(m)와 날짜 수(n)
    private static int n, m;

    public static void main(String[] args) throws IOException {
        // 초기화 함수 호출
        init();

        // n일 동안 첫 번째 성장 처리
        for (int day = 0; day < n; day++) {
            firstGrow(day);
        }

        // 다른 위치의 성장 처리
        otherGrow();

        // 결과 맵을 문자열로 변환하여 출력 준비
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(map[i][j]).append(" ");
            }
            // 마지막 공백 제거하고 줄바꿈 추가
            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }

        // 최종 결과 출력 및 리소스 해제
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기화 함수: 입력을 받아 맵과 성장 정보 초기화
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());  // 맵 크기
        n = Integer.parseInt(st.nextToken());  // 날짜 수

        // 맵과 성장 정보 배열 초기화
        map = new int[m][m];      // m x m 크기의 맵
        grow = new int[n][3];     // n일 동안의 성장 정보(각 일자별로 3가지 성장 유형)

        // 맵을 1로 초기화 (모든 위치의 초기 높이는 1)
        for (int i = 0; i < m; i++) {
            Arrays.fill(map[i], 1);
        }

        // n일 동안의 성장 정보 입력 받기
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            grow[i][0] = Integer.parseInt(st.nextToken());  // 첫 번째 성장 유형의 수
            grow[i][1] = Integer.parseInt(st.nextToken());  // 두 번째 성장 유형의 수
            grow[i][2] = Integer.parseInt(st.nextToken());  // 세 번째 성장 유형의 수
        }
    }

    // 첫 번째 성장 처리 함수 (특정 일자의 성장 정보 적용)
    private static void firstGrow(int day) {
        int index = 0;  // 성장 유형 인덱스

        // 왼쪽 세로 경계선(아래에서 위로) 성장 처리
        for (int i = m - 1; i > 0; i--) {
            // 해당 성장 유형의 남은 수가 0이면 다음 유형으로 이동
            while (grow[day][index] == 0) index++;
            // 현재 위치에 성장 유형 인덱스만큼 높이 증가
            map[i][0] += index;
            // 해당 성장 유형 사용 횟수 감소
            grow[day][index]--;
        }

        // 위쪽 가로 경계선(왼쪽에서 오른쪽으로) 성장 처리
        for (int i = 0; i < m; i++) {
            // 해당 성장 유형의 남은 수가 0이면 다음 유형으로 이동
            while (grow[day][index] == 0) index++;
            // 현재 위치에 성장 유형 인덱스만큼 높이 증가
            map[0][i] += index;
            // 해당 성장 유형 사용 횟수 감소
            grow[day][index]--;
        }
    }

    // 나머지 위치의 성장 처리 함수
    private static void otherGrow() {
        // 첫 번째 행을 제외한 나머지 행들을 처리
        for (int i = 1; i < m; i++) {
            // 첫 번째 열을 제외한 나머지 열들을 처리
            for (int j = 1; j < m; j++) {
                // 각 위치의 높이를 위쪽 경계(0행)의 같은 열 값으로 설정
                map[i][j] = map[0][j];
            }
        }
    }
}