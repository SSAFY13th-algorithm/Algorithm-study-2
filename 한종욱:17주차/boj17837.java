import java.io.*;
import java.util.*;

/**
 * 백준 17837번: 새로운 게임 2
 * 문제 난이도: Gold 2
 * 문제 유형: 구현, 시뮬레이션
 *
 * 문제 요약:
 * - n×n 크기의 체스판에 k개의 말이 있음
 * - 체스판의 각 칸은 흰색(0), 빨간색(1), 파란색(2) 중 하나
 * - 말은 1번부터 k번까지 번호가 매겨져 있고, 이동 방향이 정해져 있음 (→, ←, ↑, ↓)
 * - 턴마다 1번 말부터 k번 말까지 순서대로 이동
 * - 말이 이동할 때는 그 말과 그 위에 있는 모든 말이 함께 이동
 * - 말이 4개 이상 쌓이면 게임 종료
 *
 * 이동 규칙:
 * - 흰색: 그대로 이동
 * - 빨간색: 이동 후 순서 반전
 * - 파란색 또는 체스판 밖: 이동 방향 반대로 바꾸고 한 칸 이동. 또 파란색/체스판 밖이면 이동하지 않음
 *
 * 시간복잡도: O(1000 × k × k) = O(100,000)
 */
public class boj17837 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    // 방향 배열: 0은 dummy, 1:오른쪽, 2:왼쪽, 3:위쪽, 4:아래쪽
    private static final int[][] dir = {
        {0, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, 0}
    };

    private static Cell[][] map;       // 체스판 (각 칸의 색상과 해당 칸에 쌓여있는 말들의 정보)
    private static Piece[] pieces;     // 말들의 정보 (위치, 방향, 쌓인 순서)
    private static int n, k, answer;   // 체스판 크기, 말의 개수, 턴 수

    public static void main(String[] args) throws IOException {
        init();  // 입력 및 초기화

        // 게임 시작 (최대 1000턴까지 진행)
        while (true) {
            if (!play()) break;  // 게임이 종료되면 반복 중단
            answer++;            // 턴 수 증가
            if (answer > 1000) break;  // 1000턴 초과하면 중단
        }

        // 결과 출력: 1000턴 초과면 -1, 아니면 턴 수
        if (answer > 1000) answer = -1;
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 입력을 받고 필요한 배열들을 초기화하는 함수
     */
    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());  // 체스판 크기
        k = Integer.parseInt(st.nextToken());  // 말의 개수
        answer = 1;  // 턴 수 (1부터 시작)

        // 체스판 및 말 배열 초기화 (1-indexed)
        map = new Cell[n + 1][n + 1];
        pieces = new Piece[k + 1];

        // 체스판 색상 입력 (0:흰색, 1:빨간색, 2:파란색)
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = new Cell(Integer.parseInt(st.nextToken()));
            }
        }

        // 말 정보 입력 (위치, 방향)
        for (int i = 1; i <= k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());

            pieces[i] = new Piece(x, y, dir);
            map[x][y].pieces.add(i);  // 해당 위치의 말 목록에 추가
        }
    }

    /**
     * 한 턴을 진행하는 함수
     * @return 게임 계속 진행 여부 (true: 계속, false: 종료)
     */
    private static boolean play() {
        // 1번부터 k번 말까지 순서대로 이동
        for (int i = 1; i <= k; i++) {
            Piece piece = pieces[i];
            if (piece == null) continue;  // 존재하지 않는 말은 건너뜀

            // 현재 말과 그 위에 쌓인 말들을 선택
            List<Integer> movePiece = select(piece);

            // 이동할 위치 계산
            int nx = piece.x + dir[piece.dir][0];
            int ny = piece.y + dir[piece.dir][1];

            // 이동할 위치가 파란색이거나 체스판 밖인 경우
            if (OOB(nx, ny) || map[nx][ny].color == 2) {
                // 방향 전환 (반대 방향으로)
                if (piece.dir == 1) piece.dir = 2;
                else if (piece.dir == 2) piece.dir = 1;
                else if (piece.dir == 3) piece.dir = 4;
                else if (piece.dir == 4) piece.dir = 3;

                // 새 방향으로 이동할 위치 재계산
                nx = piece.x + dir[piece.dir][0];
                ny = piece.y + dir[piece.dir][1];

                // 또 파란색이거나 체스판 밖이면 이동하지 않음
                if (OOB(nx, ny) || map[nx][ny].color == 2) {
                    piece.index = map[piece.x][piece.y].pieces.size();  // 인덱스 갱신
                    move(piece.x, piece.y, movePiece, piece.index);     // 제자리에 다시 놓음
                }
                // 흰색인 경우
                else if (map[nx][ny].color == 0) {
                    piece.index = map[nx][ny].pieces.size();  // 새 위치에서의 인덱스
                    move(nx, ny, movePiece, piece.index);     // 이동
                }
                // 빨간색인 경우
                else {
                    piece.index = map[nx][ny].pieces.size();     // 새 위치에서의 인덱스
                    moveReverse(nx, ny, movePiece, piece.index); // 이동 후 순서 반전
                }
            }
            // 흰색인 경우
            else if (map[nx][ny].color == 0) {
                piece.index = map[nx][ny].pieces.size();  // 새 위치에서의 인덱스
                move(nx, ny, movePiece, piece.index);     // 이동
            }
            // 빨간색인 경우
            else {
                piece.index = map[nx][ny].pieces.size();     // 새 위치에서의 인덱스
                moveReverse(nx, ny, movePiece, piece.index); // 이동 후 순서 반전
            }

            // 게임 종료 조건 확인 (말이 4개 이상 쌓였는지)
            if (finish()) return false;
        }
        return true;  // 게임 계속 진행
    }

    /**
     * 좌표가 체스판 밖인지 확인하는 함수
     */
    private static boolean OOB(int nx, int ny) {
        return nx < 1 || nx > n || ny < 1 || ny > n;
    }

    /**
     * 현재 말과 그 위에 쌓인 말들을 선택하는 함수
     * @param piece 기준이 되는 말
     * @return 기준 말과 그 위에 쌓인 말들의 번호 리스트
     */
    private static List<Integer> select(Piece piece) {
        List<Integer> temp = new ArrayList<>();

        // 현재 말의 인덱스부터 그 위에 쌓인 모든 말을 선택
        for (int i = piece.index; i < map[piece.x][piece.y].pieces.size(); i++) {
            temp.add(map[piece.x][piece.y].pieces.get(i));
        }

        // 선택한 말들을 현재 위치에서 제거
        for (int i = 0; i < temp.size(); i++) {
            map[piece.x][piece.y].pieces.remove(piece.index);
        }

        return temp;
    }

    /**
     * 말들을 새 위치로 이동시키는 함수 (순서 유지)
     * @param nx 새 위치 x좌표
     * @param ny 새 위치 y좌표
     * @param movePiece 이동할 말들의 번호 리스트
     * @param index 새 위치에서의 시작 인덱스
     */
    private static void move(int nx, int ny, List<Integer> movePiece, int index) {
        // 새 위치에 말들 추가
        map[nx][ny].pieces.addAll(movePiece);

        // 이동한 모든 말의 위치, 인덱스 정보 갱신
        for (int i = 0; i < movePiece.size(); i++) {
            int pieceNum = movePiece.get(i);
            pieces[pieceNum].x = nx;
            pieces[pieceNum].y = ny;
            pieces[pieceNum].index = index + i;  // 인덱스 갱신
        }
    }

    /**
     * 말들을 새 위치로 이동시키고 순서를 반전시키는 함수 (빨간색 칸용)
     * @param nx 새 위치 x좌표
     * @param ny 새 위치 y좌표
     * @param movePiece 이동할 말들의 번호 리스트
     * @param index 새 위치에서의 시작 인덱스
     */
    private static void moveReverse(int nx, int ny, List<Integer> movePiece, int index) {
        // 이동할 말들의 순서 반전 (빨간색 칸의 규칙)
        Collections.reverse(movePiece);

        // 새 위치에 순서 반전된 말들 추가
        map[nx][ny].pieces.addAll(movePiece);

        // 이동한 모든 말의 위치, 인덱스 정보 갱신
        for (int i = 0; i < movePiece.size(); i++) {
            int pieceNum = movePiece.get(i);
            pieces[pieceNum].x = nx;
            pieces[pieceNum].y = ny;
            pieces[pieceNum].index = index + i;  // 인덱스 갱신
        }
    }

    /**
     * 게임 종료 조건을 확인하는 함수 (말이 4개 이상 쌓였는지)
     * @return 게임 종료 여부 (true: 종료, false: 계속)
     */
    private static boolean finish() {
        for (int i = 1; i <= k; i++) {
            Piece piece = pieces[i];
            if (piece == null) continue;

            // 해당 위치에 말이 4개 이상 쌓였으면 게임 종료
            if (map[piece.x][piece.y].pieces.size() >= 4) return true;
        }
        return false;
    }

    /**
     * 체스판의 각 칸 정보를 저장하는 클래스
     */
    static class Cell {
        int color;               // 칸의 색상 (0:흰색, 1:빨간색, 2:파란색)
        List<Integer> pieces;    // 해당 칸에 쌓인 말들의 번호 리스트

        Cell(int color) {
            this.color = color;
            pieces = new ArrayList<>();
        }
    }

    /**
     * 말의 정보를 저장하는 클래스
     */
    static class Piece {
        int x;      // 말의 현재 x좌표
        int y;      // 말의 현재 y좌표
        int dir;    // 말의 이동 방향 (1:오른쪽, 2:왼쪽, 3:위쪽, 4:아래쪽)
        int index = 0;  // 해당 칸에서 말의 쌓인 순서(인덱스)

        public Piece(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
}