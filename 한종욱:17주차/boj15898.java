import java.io.*;
import java.util.*;

/**
 * 백준 15898번: 피아의 아틀리에 신비한 대회의 연금술사
 * 문제 난이도: Gold 1
 * 문제 유형: 브루트포스, 구현
 *
 * 문제 요약:
 * - 5×5 크기의 가마에 서로 다른 3개의 재료를 순서대로 넣어 최고의 폭탄을 만드는 문제
 * - 각 재료는 4×4 크기이며, 회전(0°, 90°, 180°, 270°)하거나 위치(좌상단 기준 {0,0}, {0,1}, {1,0}, {1,1})를 선택 가능
 * - 각 칸에는 품질(숫자 -9~9)과 원소(R, B, G, Y, W)가 있음
 * - 폭탄의 품질 = 7×R + 5×B + 3×G + 2×Y (각 원소별 품질의 합)
 *
 * 시간복잡도: O(n³ × 4⁶)
 * - n개의 재료 중 3개 선택: O(n³)
 * - 각 재료의 회전(4) 및 위치(4) 조합: O(4⁶)
 */
public class boj15898{
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 재료를 놓을 수 있는 4가지 위치 (좌상단 좌표 기준)
    private static final int[][] poses = {
        {0, 0}, {0, 1}, {1, 0}, {1, 1}
    };

    private static Status[] status;           // 선택된 3개 재료의 상태(회전, 위치) 정보
    private static Cell[][][] ingredient;     // 입력받은 모든 재료 정보 [재료번호][행][열]
    private static Cell[][] resultCopy;       // 회전 연산 시 임시 저장용 배열
    private static Cell[][] map;              // 5×5 가마
    private static int n, max;                // 재료 개수, 최대 폭탄 품질

    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화

        // 1. n개의 재료 중 3개 선택 (순열, 순서 중요)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (i != j && j != k && i != k) {  // 서로 다른 3개 재료 선택
                        function(new int[]{i, j, k});  // 선택된 재료로 모든 경우의 수 탐색
                    }
                }
            }
        }

        // 결과 출력
        bw.write(max + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 필요한 배열들을 초기화하는 함수
     */
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 재료의 개수

        // 배열 초기화
        ingredient = new Cell[n][4][4];       // n개 재료 (각 4×4)
        status = new Status[3];               // 3개 선택된 재료의 상태
        resultCopy = new Cell[4][4];          // 회전 연산용 임시 배열
        map = new Cell[5][5];                 // 5×5 가마

        // Cell 객체 생성
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    ingredient[i][j][k] = new Cell();
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                resultCopy[i][j] = new Cell();
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[i][j] = new Cell();
            }
        }

        // Status 객체 생성
        for (int i = 0; i < 3; i++) {
            status[i] = new Status();
        }

        // 재료 정보 입력
        for (int i = 0; i < n; i++) {
            // 품질(숫자) 입력
            for (int j = 0; j < 4; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 4; k++) {
                    ingredient[i][j][k].num = Integer.parseInt(st.nextToken());
                }
            }

            // 원소(색상) 입력
            for (int j = 0; j < 4; j++) {
                String[] input = br.readLine().split(" ");
                for (int k = 0; k < 4; k++) {
                    ingredient[i][j][k].color = input[k].charAt(0);
                }
            }
        }
    }

    /**
     * 주어진 3개 재료로 만들 수 있는 모든 경우의 수를 탐색하는 함수
     * @param indexes 선택된 3개 재료의 인덱스 배열
     */
    private static void function(int[] indexes) {
        // 2. 각 재료의 회전(4가지)과 위치(4가지)에 대한 모든 조합 탐색 - 6중 for문
        for (int i = 0; i < 4; i++) {         // 첫 번째 재료 회전 (0°, 90°, 180°, 270°)
            for (int j = 0; j < 4; j++) {     // 두 번째 재료 회전
                for (int k = 0; k < 4; k++) { // 세 번째 재료 회전
                    for (int l = 0; l < 4; l++) {     // 첫 번째 재료 위치 (4가지)
                        for (int m = 0; m < 4; m++) { // 두 번째 재료 위치
                            for (int n = 0; n < 4; n++) { // 세 번째 재료 위치
                                // 각 재료의 상태(회전, 위치) 설정
                                status[0].deg = i;
                                status[1].deg = j;
                                status[2].deg = k;
                                status[0].pos = poses[l];
                                status[1].pos = poses[m];
                                status[2].pos = poses[n];

                                // 가마 초기화 (new 대신 reset으로 메모리 효율화)
                                for (int p = 0; p < 5; p++) {
                                    for (int q = 0; q < 5; q++) {
                                        map[p][q].reset();
                                    }
                                }

                                // 3개 재료를 순서대로 가마에 넣음
                                for (int index = 0; index < 3; index++) {
                                    // 재료를 회전
                                    Cell[][] temp = rotate(ingredient[indexes[index]], status[index].deg);
                                    // 회전된 재료를 가마에 추가
                                    addIngredient(map, temp, status[index].pos);
                                }

                                // 폭탄 품질 계산 및 최댓값 갱신
                                int result = getResult(map);
                                max = Math.max(max, result);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 가마의 최종 상태에서 폭탄 품질을 계산하는 함수
     * @param initMap 최종 가마 상태
     * @return 폭탄 품질 (7×R + 5×B + 3×G + 2×Y)
     */
    private static int getResult(Cell[][] initMap) {
        int r = 0;  // 빨강(R) 원소 품질 합
        int b = 0;  // 파랑(B) 원소 품질 합
        int g = 0;  // 녹색(G) 원소 품질 합
        int y = 0;  // 노랑(Y) 원소 품질 합

        // 각 칸의 원소별로 품질 합산
        for (int p = 0; p < 5; p++) {
            for (int q = 0; q < 5; q++) {
                if (initMap[p][q].color == 'R') r += initMap[p][q].num;
                if (initMap[p][q].color == 'B') b += initMap[p][q].num;
                if (initMap[p][q].color == 'G') g += initMap[p][q].num;
                if (initMap[p][q].color == 'Y') y += initMap[p][q].num;
            }
        }

        // 폭탄 품질 계산: 7×R + 5×B + 3×G + 2×Y
        return 7*r + 5*b + 3*g + 2*y;
    }

    /**
     * 가마에 재료를 추가하는 함수
     * @param map 현재 가마 상태
     * @param ingredient 추가할 재료
     * @param pos 재료를 놓을 위치 (좌상단 좌표)
     */
    private static void addIngredient(Cell[][] map, Cell[][] ingredient, int[] pos) {
        for (int i = pos[0]; i < pos[0] + 4; i++) {
            for (int j = pos[1]; j < pos[1] + 4; j++) {
                // 품질 갱신: 기존 품질 + 재료 품질
                map[i][j].num += ingredient[i - pos[0]][j - pos[1]].num;
                if (map[i][j].num < 0) map[i][j].num = 0;  // 품질 최소값: 0
                if (map[i][j].num > 9) map[i][j].num = 9;  // 품질 최대값: 9

                // 원소 갱신: 흰색(W)이 아닌 재료의 원소로 덮어씀
                if (ingredient[i - pos[0]][j - pos[1]].color != 'W') {
                    map[i][j].color = ingredient[i - pos[0]][j - pos[1]].color;
                }
            }
        }
    }

    /**
     * 재료를 회전시키는 함수
     * @param cell 회전시킬 재료
     * @param rotation 회전 각도 (0: 0°, 1: 90°, 2: 180°, 3: 270°)
     * @return 회전된 재료
     */
    private static Cell[][] rotate(Cell[][] cell, int rotation) {
        // 회전이 없는 경우 원본 복사하여 반환
        if (rotation == 0) return copy(cell);

        Cell[][] result = new Cell[4][4];

        // 회전 각도에 따라 좌표 변환
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                switch (rotation) {
                    case 1: // 90° 시계 방향
                        result[j][3 - i] = cell[i][j];
                        break;
                    case 2: // 180°
                        result[3 - i][3 - j] = cell[i][j];
                        break;
                    case 3: // 270° 시계 방향 (= 90° 반시계 방향)
                        result[3 - j][i] = cell[i][j];
                        break;
                }
            }
        }

        return result;
    }

    /**
     * 재료 배열을 복사하는 함수 (메모리 효율화를 위해 새 객체 생성 없이 재사용)
     * @param cell 원본 재료
     * @return 복사된 재료
     */
    private static Cell[][] copy(Cell[][] cell) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                resultCopy[i][j].setFrom(cell[i][j]);
            }
        }

        return resultCopy;
    }

    /**
     * 재료의 상태(회전, 위치)를 저장하는 클래스
     */
    static class Status {
        int deg;    // 회전 각도 (0: 0°, 1: 90°, 2: 180°, 3: 270°)
        int[] pos;  // 위치 좌표 (좌상단 기준)

        public Status() {
        }
    }

    /**
     * 가마와 재료의 각 칸 정보를 저장하는 클래스
     */
    static class Cell {
        int num;    // 품질 (-9 ~ 9)
        char color; // 원소 (R: 빨강, B: 파랑, G: 녹색, Y: 노랑, W: 흰색)

        public Cell() {
            this.num = 0;
            this.color = 'W';
        }

        /**
         * 칸 초기화 (흰색, 0)
         */
        public void reset() {
            this.num = 0;
            this.color = 'W';
        }

        /**
         * 다른 Cell의 정보를 복사
         */
        public void setFrom(Cell other) {
            this.num = other.num;
            this.color = other.color;
        }
    }
}