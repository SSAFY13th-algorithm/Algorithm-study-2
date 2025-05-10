import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class boj23843 {
    // 입출력을 위한 BufferedReader와 BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // n: 작업의 개수, m: 처리할 수 있는 라인의 수
    private static int n, m;
    // 작업 시간들을 저장할 리스트
    private static List<Integer> list = new ArrayList<>();
    // 각 라인별 작업 시간의 합을 저장할 배열
    private static int[] sum;

    public static void main(String args[]) throws IOException {
        // 첫 줄에서 n과 m을 입력받음
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);
        sum = new int[m];

        // sum 배열을 0으로 초기화
        Arrays.fill(sum, 0);

        // 작업 시간들을 입력받아 리스트에 저장
        input = br.readLine().split(" ");
        for (String element : input) {
            list.add(Integer.parseInt(element));
        }
        // 작업 시간을 내림차순으로 정렬
        Collections.sort(list, Collections.reverseOrder());

        // 각 작업에 대해 function 메소드 호출
        for (int i : list) {
            function(i);
        }

        // 최종 결과를 구하기 위해 정렬
        Arrays.sort(sum);

        // 가장 큰 값(마지막 작업이 끝나는 시간)을 출력
        bw.write(sum[sum.length - 1] + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 각 작업을 라인에 배정하는 함수
    private static void function(int t) {
        // sum 배열을 정렬하여 가장 작은 값을 가진 라인에 현재 작업 배정
        Arrays.sort(sum);
        sum[0] += t;

        // minIndex() 함수 사용시 코드
//        int index = minIndex();
//        sum[index] += t;
    }

    // 현재 가장 적은 작업량을 가진 라인의 인덱스를 찾는 함수
    private static int minIndex() {
        int index = 0;
        int min = sum[index];

        // 비어있는 라인이 있다면 그 라인의 인덱스 반환
        for (int i = 0; i < m; i++) {
            if (sum[i] == 0) {
                return i;
            }

            // 현재까지의 최소값보다 작은 값을 발견하면 갱신
            if (sum[i] < min) {
                min = sum[i];
                index = i;
            }
        }

        return index;
    }
}