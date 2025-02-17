import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class boj21610 {
    // 입출력을 위한 객체들
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 8방향 이동을 위한 방향 배열 (0번 인덱스는 dummy)
    // ←, ↖, ↑, ↗, →, ↘, ↓, ↙ 순서
    private static final int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    private static final int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};

    private static List<int[]> clouds = new ArrayList<>();    // 구름의 위치를 저장하는 리스트
    private static List<int[]> moves = new ArrayList<>();     // 이동 명령을 저장하는 리스트
    private static int[][] arr;    // 격자의 물의 양을 저장하는 배열
    private static int n, m;       // 격자의 크기 n, 이동 횟수 m

    public static void main(String[] args) throws IOException {
        setting();    // 입력 처리 및 초기 설정

        // 모든 이동 명령에 대해 시뮬레이션
        for (int[] move : moves) {
            moveClouds(move);  // 구름 이동
            raining();         // 비 내리기
            copyWater();       // 물 복사 버그
            makeClouds();      // 새로운 구름 생성
        }

        // 격자에 남아있는 물의 양의 합 출력
        bw.write(sum() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 입력 처리 및 초기 설정
    private static void setting() throws IOException {
        String[] input = br.readLine().split(" ");
        n = Integer.parseInt(input[0]);
        m = Integer.parseInt(input[1]);

        // 격자 초기화
        arr = new int[n + 1][n + 1];

        // 격자의 물의 양 입력
        for (int i = 1; i <= n; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= n; j++) {
                arr[i][j] = Integer.parseInt(input[j - 1]);
            }
        }

        // 이동 명령 입력
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");
            moves.add(new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])});
        }

        // 초기 구름 위치 설정
        clouds.add(new int[]{n, 1});
        clouds.add(new int[]{n, 2});
        clouds.add(new int[]{n - 1, 1});
        clouds.add(new int[]{n - 1, 2});
    }

    // 구름 이동
    private static void moveClouds(int[] move) {
        for (int i = 0; i < clouds.size(); i++) {
            // 방향과 거리에 따라 구름 이동
            clouds.get(i)[0] += dx[move[0]] * move[1];
            clouds.get(i)[1] += dy[move[0]] * move[1];

            // x좌표가 범위를 벗어난 경우 처리
            if (OOBX(clouds.get(i)[0])) {
                if (clouds.get(i)[0] < 1) {
                    clouds.get(i)[0] = n - Math.abs(clouds.get(i)[0]) % n;
                }
                else if (clouds.get(i)[0] > n) {
                    clouds.get(i)[0] = clouds.get(i)[0] % n;
                    if (clouds.get(i)[0] == 0) {
                        clouds.get(i)[0] = n;
                    }
                }
            }

            // y좌표가 범위를 벗어난 경우 처리
            if (OOBY(clouds.get(i)[1])) {
                if (clouds.get(i)[1] < 1) {
                    clouds.get(i)[1] = n - Math.abs(clouds.get(i)[1]) % n;
                }
                else if (clouds.get(i)[1] > n) {
                    clouds.get(i)[1] = clouds.get(i)[1] % n;
                    if (clouds.get(i)[1] == 0) {
                        clouds.get(i)[1] = n;
                    }
                }
            }
        }
    }

    // 격자 범위 체크
    private static boolean OOBX(int x) {
        return x < 1 || x > n;
    }

    private static boolean OOBY(int y) {
        return y < 1 || y > n;
    }

    // 비 내리기 (구름이 있는 칸의 물의 양 1 증가)
    private static void raining() {
        for (int[] cloud : clouds) {
            arr[cloud[0]][cloud[1]]++;
        }
    }

    // 물 복사 버그 (대각선 방향으로 거리가 1인 칸에 물이 있는 바구니 수만큼 물의 양 증가)
    private static void copyWater() {
        List<int[]> temp = new ArrayList<>();

        for (int[] cloud : clouds) {
            int count = 0;

            // 대각선 4방향 체크
            if (cloud[0] - 1 > 0 && cloud[1] - 1 > 0 && arr[cloud[0] - 1][cloud[1] - 1] > 0)
                count++;
            if (cloud[0] + 1 < n + 1 && cloud[1] - 1 > 0 && arr[cloud[0] + 1][cloud[1] - 1] > 0)
                count++;
            if (cloud[0] - 1 > 0 && cloud[1] + 1 < n + 1 && arr[cloud[0] - 1][cloud[1] + 1] > 0)
                count++;
            if (cloud[0] + 1 < n + 1 && cloud[1] + 1 < n + 1 && arr[cloud[0] + 1][cloud[1] + 1] > 0)
                count++;

            temp.add(new int[]{cloud[0], cloud[1], count});
        }

        // 물의 양 증가
        for (int[] element : temp) {
            arr[element[0]][element[1]] += element[2];
        }
    }

    // 새로운 구름 생성
    private static void makeClouds() {
        boolean[][] visited = new boolean[n + 1][n + 1];  // 이전 구름 위치 체크
        List<int[]> temp = new ArrayList<>();

        // 이전 구름 위치 표시
        for (int[] cloud : clouds) {
            visited[cloud[0]][cloud[1]] = true;
        }

        // 물의 양이 2 이상이고 구름이 사라진 칸이 아닌 곳에 구름 생성
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (!visited[i][j] && arr[i][j] > 1) {
                    temp.add(new int[]{i, j});
                    arr[i][j] -= 2;
                }
            }
        }
        clouds = temp;
    }

    // 격자에 있는 물의 양의 합 계산
    private static int sum() {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                result += arr[i][j];
            }
        }
        return result;
    }
}