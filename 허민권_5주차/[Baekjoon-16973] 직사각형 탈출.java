import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;

    static void readLine() throws IOException {
        st = new StringTokenizer(br.readLine());
    }

    static int nextInt() {
        return Integer.parseInt(st.nextToken());
    }

    static int[] arr;
    static int count;

    static int N;
    static int M;
    static int H;
    static int W;

    static int sr, sc; // 시작
    static int fr, fc; // 끝

    static int[][] board = new int[1001][1001];
    static boolean[][] checkBoard = new boolean[1001][1001];

    static boolean isOk(int y, int x) {
        return 1 <= y && y <= N && 1 <= x && x <= M;
    }

    static int[] dy = { -1, 1, 0, 0 };
    static int[] dx = { 0, 0, -1, 1 };

    static int bfs(int sy, int sx) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { sy, sx, 0 });

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int y = cur[0], x = cur[1];
            int curCount = cur[2];

            if (y == fr && x == fc) {
                return curCount;
            }

            for (int i = 0; i < 4; ++i) {
                // 0,1 일때는 위 아래
                // 0 위 배열 [sr][sc ~ sc+W) 까지 (-1,0) 비교
                int nextY = y + dy[i];
                int nextX = x + dx[i];
                int check = 0;
                if (i == 0) {
                    for (int j = 0; j < W; ++j) {
                        int lineY = y + dy[i];
                        int lineX = x + dx[i] + j;
                        if (isOk(lineY, lineX) && board[lineY][lineX] == 0)
                            check++;
                    }
                    if (check == W && !checkBoard[nextY][nextX]) {
                        checkBoard[nextY][nextX] = true;
                        queue.add(new int[] { nextY, nextX, curCount + 1 });
                    }
                }

                // 1 아래 배열 [sr+H-1][sc ~sc+W) 까지 (1,0) 비교
                if (i == 1) {
                    for (int j = 0; j < W; ++j) {
                        int lineY = y + H - 1 + dy[i];
                        int lineX = x + dx[i] + j;
                        if (isOk(lineY, lineX) && board[lineY][lineX] == 0)
                            check++;
                    }
                    if (check == W && !checkBoard[nextY][nextX]) {
                        checkBoard[nextY][nextX] = true;
                        queue.add(new int[] { nextY, nextX, curCount + 1 });
                    }
                }
                // 2,3 일때는 좌우
                // 2 왼쪽 배열 [sr~sr+H)[sr] 까지 (0,-1) 비교
                if (i == 2) {
                    for (int j = 0; j < H; ++j) {
                        int lineY = y + dy[i] + j;
                        int lineX = x + dx[i];
                        if (isOk(lineY, lineX) && board[lineY][lineX] == 0)
                            check++;
                    }
                    if (check == H && !checkBoard[nextY][nextX]) {
                        checkBoard[nextY][nextX] = true;
                        queue.add(new int[] { nextY, nextX, curCount + 1 });
                    }
                }
                // 3 오른쪽배열 [sr~sr+H)[sr+W-1] 까지 (0,1) 비교
                if (i == 3) {
                    for (int j = 0; j < H; ++j) {
                        int lineY = y + dy[i] + j;
                        int lineX = x + W - 1 + dx[i];
                        if (isOk(lineY, lineX) && board[lineY][lineX] == 0)
                            check++;
                    }
                    if (check == H && !checkBoard[nextY][nextX]) {
                        checkBoard[nextY][nextX] = true;
                        queue.add(new int[] { nextY, nextX, curCount + 1 });
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        readLine();
        N = nextInt();
        M = nextInt();

        for (int i = 1; i <= N; ++i) {
            readLine();
            for (int j = 1; j <= M; ++j) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        readLine();
        H = nextInt();
        W = nextInt();
        sr = nextInt();
        sc = nextInt();
        fr = nextInt();
        fc = nextInt();

        int ans = bfs(sr, sc);
        System.out.println(ans);

        // StringBuilder sb = new StringBuilder();

        // for (int i = 1; i <= N; ++i) {
        // for (int j = 1; j <= M; ++j) {
        // sb.append(board[i][j]).append(" ");
        // }
        // sb.append("\n");
        // }

    }
}
