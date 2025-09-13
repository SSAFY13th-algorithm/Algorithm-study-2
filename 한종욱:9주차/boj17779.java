import java.io.*;
import java.util.*;

public class boj17779 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    private static int[][] map;  // 원본 구역의 인구 정보를 저장하는 배열
    private static int[][] arr;  // 구역 번호를 표시하는 배열
    private static int n, sum;   // n: 맵의 크기, sum: 전체 인구 수

    public static void main(String[] args) throws IOException {
        init();  // 초기 데이터 설정

        int answer = separate();  // 선거구 나누기
        bw.write(String.valueOf(answer));  // 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하는 메소드
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 맵의 크기 입력
        sum = 0;  // 전체 인구 수 초기화

        map = new int[n + 1][n + 1];  // 1-indexed 인구 정보 배열
        arr = new int[n + 1][n + 1];  // 구역 번호 표시 배열

        // 인구 정보 입력
        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                sum += map[i][j];  // 전체 인구 수 누적
            }
        }
    }

    // 선거구를 나누고 인구 차이의 최솟값을 계산하는 메소드
    private static int separate() {
        int min = sum;  // 인구 차이 최솟값 (초기값은 최대값으로)

        // 기준점 (x, y)와 경계의 길이 d1, d2를 모든 가능한 조합으로 탐색
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int d1 = 1; d1 <= n; d1++) {
                    for (int d2 = 1; d2 <= n; d2++) {
                        // 경계가 맵 내부에 위치하는지 확인
                        if (1 <= i && i + d1 + d2 <= n && j + d2 <= n && 1 <= j - d1) {
                            // 각 선거구의 인구 수 계산
                            int one = oneArea(i, j, d1, d2);    // 1번 선거구
                            int two = twoArea(i, j, d1, d2);    // 2번 선거구
                            int three = threeArea(i, j, d1, d2); // 3번 선거구
                            int four = fourArea(i, j, d1, d2);   // 4번 선거구
                            int five = sum - (one + two + three + four); // 5번 선거구

                            // 최대 인구와 최소 인구의 차이 계산 및 최솟값 갱신
                            min = Math.min(min, max(one, two, three, four, five) - min(one, two, three, four, five));
                        }
                    }
                }
            }
        }
        return min;
    }

    // 5개 값 중 최댓값을 반환하는 메소드
    private static int max(int one, int two, int three, int four, int five) {
        int max = one;
        if (two > max) max = two;
        if (three > max) max = three;
        if (four > max) max = four;
        if (five > max) max = five;
        return max;
    }

    // 5개 값 중 최솟값을 반환하는 메소드
    private static int min(int one, int two, int three, int four, int five) {
        int min = one;
        if (two < min) min = two;
        if (three < min) min = three;
        if (four < min) min = four;
        if (five < min) min = five;
        return min;
    }

    // 1번 선거구의 인구 수를 계산하는 메소드
    private static int oneArea(int x, int y, int d1, int d2) {
        int result = 0;

        // 1번 선거구의 윗부분
        for (int i = 1; i < x; i++) {
            for (int j = 1; j <= y; j++) {
                result += map[i][j];
                arr[i][j] = 1;
            }
        }

        // 1번 선거구의 아랫부분 (경계선 고려)
        int temp = 1;
        for (int i = x; i < x + d1; i++) {
            for (int j = 1; j <= y - temp; j++) {
                result += map[i][j];
                arr[i][j] = 1;
            }
            temp++;
        }
        return result;
    }

    // 2번 선거구의 인구 수를 계산하는 메소드
    private static int twoArea(int x, int y, int d1, int d2) {
        int result = 0;

        // 2번 선거구의 윗부분
        for (int i = 1; i <= x; i++) {
            for (int j = y + 1; j <= n; j++) {
                result += map[i][j];
                arr[i][j] = 2;
            }
        }

        // 2번 선거구의 아랫부분 (경계선 고려)
        int temp = 2;
        for (int i = x + 1; i <= x + d2; i++) {
            for (int j = y + temp; j <= n; j++) {
                result += map[i][j];
                arr[i][j] = 2;
            }
            temp++;
        }

        return result;
    }

    // 3번 선거구의 인구 수를 계산하는 메소드
    private static int threeArea(int x, int y, int d1, int d2) {
        int result = 0;

        // 3번 선거구 (경계선 고려)
        int temp = 0;
        for (int i = x + d1; i <= n; i++) {
            for (int j = 1; j < y - d1 + temp; j++) {
                result += map[i][j];
                arr[i][j] = 3;
            }
            if (i < x + d1 + d2) temp++;
        }

        return result;
    }

    // 4번 선거구의 인구 수를 계산하는 메소드
    private static int fourArea(int x, int y, int d1, int d2) {
        int result = 0;

        // 4번 선거구 (경계선 고려)
        int temp = 0;
        for (int i = x + d2 + 1; i <= n; i++) {
            for (int j = y + d2 - temp; j <= n; j++) {
                result += map[i][j];
                arr[i][j] = 4;
            }
            if (i <= x + d1 + d2) temp++;
        }

        return result;
    }
}