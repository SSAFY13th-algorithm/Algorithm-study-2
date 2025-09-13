import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class boj18234 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] carrots;   // 당근 정보 배열 [당근의 크기][성장 속도]
    private static int n;             // 당근의 개수
    private static long t;            // 주어진 시간

    public static void main(String[] args) throws IOException {
        setting();    // 입력 처리 및 초기 설정
        long answer = function();    // 수확할 수 있는 당근 크기의 합 계산

        // 결과 출력 및 스트림 닫기
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 및 초기 설정
    private static void setting() throws IOException {
        // 당근의 개수와 시간 입력
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        t = Long.parseLong(input[1]);

        // 당근 정보 배열 초기화
        carrots = new int[n][2];

        // 각 당근의 초기 크기와 성장 속도 입력
        for (int i = 0; i < n; i++) {
            input = br.readLine().split(" ");
            carrots[i][0] = Integer.parseInt(input[0]);  // 초기 크기
            carrots[i][1] = Integer.parseInt(input[1]);  // 성장 속도
        }

        // 당근을 성장 속도 기준으로 오름차순 정렬
        Arrays.sort(carrots, (o1, o2) -> o1[1] - o2[1]);
    }

    // 수확할 수 있는 당근 크기의 합 계산
    private static long function() {
        long answer = 0;

        // 성장 속도가 낮은 당근부터 순서대로 수확
        for (int i = 0; i < n; i++) {
            // i번째 당근의 최종 크기 계산
            // 초기 크기 + (성장 속도 * 남은 시간)
            // 남은 시간 = 전체 시간 - (아직 수확하지 않은 당근 수)
            answer += carrots[i][0] + (carrots[i][1] * (t - n + i));
        }
        return answer;
    }
}