import java.io.*;
import java.util.*;

public class boj20207 {
    // 입출력을 위한 BufferedReader, BufferedWriter, StringBuilder 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 1년의 날짜 수 상수로 선언
    private static final int DAY = 365;

    // 각 날짜별 예약 횟수를 저장할 배열과 입력받을 예약 수
    private static int[] calendar;
    private static int n;

    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화
        sb.append(solution());  // 문제 해결 및 결과 저장

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력을 받고 배열을 초기화하는 함수
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 예약 개수 입력
        calendar = new int[DAY + 2];  // 날짜별 예약 횟수를 저장할 배열 (366일 + 여유분 1일)

        // n개의 예약 정보 처리
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());  // 시작일
            int end = Integer.parseInt(st.nextToken());    // 종료일

            // 시작일부터 종료일까지 예약 횟수 증가
            for (int j = start; j <= end; j++) calendar[j]++;
        }
    }

    // 전체 면적(답)을 계산하는 함수
    private static int solution() {
        int weight = 0;   // 너비 (연속된 날짜의 수)
        int height = 0;   // 높이 (해당 구간의 최대 예약 횟수)
        int answer = 0;   // 총 면적

        // 모든 날짜를 순회하며 면적 계산
        for (int i = 1; i <= DAY + 1; i++) {
            if (calendar[i] > 0) {  // 예약이 있는 날인 경우
                height = Math.max(height, calendar[i]);  // 현재 구간의 최대 높이 갱신
                weight++;  // 너비 증가
            } else {  // 예약이 없는 날인 경우
                answer += height * weight;  // 현재까지의 구간 면적 계산하여 추가
                height = 0;  // 높이 초기화
                weight = 0;  // 너비 초기화
            }
        }
        return answer;
    }
}