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
    private static int[][] carrots;
    private static int n;
    private static long t;

    public static void main(String[] args) throws IOException {
        setting();
        long answer = function();
        // 결과 출력 및 스트림 닫기
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void setting() throws IOException {
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        t = Long.parseLong(input[1]);

        carrots = new int[n][2];

        for (int i = 0; i < n; i++) {
            input = br.readLine().split(" ");
            carrots[i][0] = Integer.parseInt(input[0]);
            carrots[i][1] = Integer.parseInt(input[1]);
        }

        Arrays.sort(carrots, (o1, o2) -> o1[1] - o2[1]);
    }

    private static long function() {
        long answer = 0;

        for (int i = 0; i < n; i++) {
            answer += carrots[i][0] + (carrots[i][1] * (t - n + i));
        }
        return answer;
    }
}