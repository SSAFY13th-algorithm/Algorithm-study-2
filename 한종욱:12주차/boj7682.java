import java.io.*;

public class boj7682 {
    // 표준 입력을 위한 BufferedReader 객체 생성
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // 표준 출력을 위한 BufferedWriter 객체 생성
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    // 결과를 저장할 StringBuilder 객체 생성 (문자열 연산 효율성을 위해 사용)
    private static final StringBuilder sb = new StringBuilder();
    // 틱택토 게임판을 나타내는 2차원 배열
    private static char[][] map;

    public static void main(String[] args) throws IOException {
        // 무한 루프를 통해 여러 게임 케이스를 처리
        while (true) {
            String input = br.readLine(); // 한 줄 입력 받기
            if (input.equals("end")) break; // "end"가 입력되면 루프 종료

            // 입력된 문자열을 이용해 게임 종료 상태가 유효한지 검사
            if (endGame(input.toCharArray())) sb.append("valid");
            else sb.append("invalid");

            sb.append("\n"); // 결과 사이에 줄바꿈 추가
        }

        // 모든 결과를 출력하고 리소스 정리
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 틱택토 게임의 종료 상태가 유효한지 검사하는 메서드
     *
     * @param input 게임판 상태를 나타내는 문자 배열 (길이 9, 'X', 'O', '.'로 구성)
     * @return 게임의 종료 상태가 유효하면 true, 아니면 false
     */
    private static boolean endGame(char[] input) {
        // 3x3 게임판 초기화
        map = new char[3][3];
        int xcnt = 0; // 'X'의 개수를 저장할 변수
        int ocnt = 0; // 'O'의 개수를 저장할 변수
        int index = 0; // 입력 배열의 인덱스

        // 1차원 입력 배열을 2차원 게임판으로 변환하며 'X'와 'O'의 개수 세기
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map[i][j] = input[index++];
                if (map[i][j] == 'X') xcnt++;
                if (map[i][j] == 'O') ocnt++;
            }
        }

        // 규칙 1: 'X'는 'O'보다 0개 또는 1개 많아야 함 (X가 먼저 시작하므로)
        if (!(xcnt == ocnt || xcnt == ocnt + 1)) return false;

        // 규칙 2: 'O'가 이겼다면, 'X'와 'O'의 개수는 같아야 함
        // (O가 이긴 직후 게임이 종료되고, O는 항상 두 번째로 두므로)
        if (xcnt == ocnt && check8way(map, 'O') && !check8way(map, 'X')) return true;

        // 규칙 3: 'X'가 이겼다면, 'X'는 'O'보다 1개 많아야 함
        // (X가 이긴 직후 게임이 종료되고, X는 항상 첫 번째로 두므로)
        if (xcnt > ocnt && check8way(map, 'X') && !check8way(map, 'O')) return true;

        // 규칙 4: 게임판이 가득 찼고(X 5개, O 4개) 'O'가 이기지 않았다면 유효한 상태
        if (xcnt == 5 && ocnt == 4 && !check8way(map, 'O')) return true;

        // 위 조건들을 모두 만족하지 않으면 유효하지 않은 상태
        return false;
    }

    /**
     * 주어진 플레이어(X 또는 O)가 승리했는지 확인하는 메서드
     * 가로, 세로, 대각선 방향으로 같은 문자가 3개 연속되어 있는지 확인
     *
     * @param map 게임판 상태
     * @param c 확인할 플레이어 ('X' 또는 'O')
     * @return 해당 플레이어가 승리했으면 true, 아니면 false
     */
    private static boolean check8way(char[][] map, char c) {
        // 가로줄과 세로줄 확인
        for (int i = 0; i < 3; i++) {
            // i번째 가로줄이 모두 같은 문자인지 확인
            if (map[i][0] == c && map[i][0] == map[i][1] && map[i][1] == map[i][2]) return true;
            // i번째 세로줄이 모두 같은 문자인지 확인
            if (map[0][i] == c && map[0][i] == map[1][i] && map[1][i] == map[2][i]) return true;
        }

        // 좌상단에서 우하단 대각선 확인
        if (map[0][0] == c && map[0][0] == map[1][1] && map[1][1] == map[2][2]) return true;

        // 우상단에서 좌하단 대각선 확인
        if (map[0][2] == c && map[0][2] == map[1][1] && map[2][0] == map[1][1]) return true;

        // 승리 조건을 만족하지 않으면 false 반환
        return false;
    }
}