import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final String DONE = "complete";
    private static final String NONDONE = "incomplete";


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int index = 1;
        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if(r == 0 && c==0) break;

            char[][] map = new char[r][c];
            int[] userPoint = new int[2];
            int total = 0;
            int successBox = 0;

            for(int i=0; i<r; i++) {
                String line = br.readLine();
                for(int j=0; j<c; j++) {
                    map[i][j] = line.charAt(j);
                    if(map[i][j] == '+') total++;
                    else if(map[i][j] == 'B') {
                        total++;
                        successBox++;
                    }
                    else if(map[i][j] == 'W') {
                        total++;
                        userPoint[0] = i;
                        userPoint[1] = j;
                    }
                    else if(map[i][j] == 'w') {
                        userPoint[0] = i;
                        userPoint[1] = j;
                    }
                }
            }

            String inputKeys = br.readLine();

            boolean isSuccess = false;
            for(char key : inputKeys.toCharArray()) {
                if(successBox == total) {
                    isSuccess = true;
                    break;
                }

                successBox = move(r, c, map, userPoint, successBox, key);
            }
            if(successBox == total) {
                isSuccess = true;
            }

            sb.append("Game "+index + ": ");

            if(isSuccess) sb.append(DONE);
            else sb.append(NONDONE);

            sb.append("\n");
            for(int i=0; i<r; i++) {
                for(int j=0; j<c; j++) {
                    sb.append(map[i][j]+"");
                }
                sb.append("\n");
            }
            index++;

        }
        System.out.println(sb.toString().trim());
    }

    private static int move(int r, int c, char[][] map, int[] userPoint, int successBox, char key) {
        int[] d = parseKey(key);
        int nx = userPoint[0] + d[0];
        int ny = userPoint[1] + d[1];

        if(nx<0 || nx>=r || ny<0 || ny>=c || map[nx][ny] == '#')
            return successBox;

        if(map[nx][ny] == '.') {
            map[nx][ny] = 'w';

            moveUser(map, userPoint, nx, ny);
            return successBox;
        }

        if(map[nx][ny] == '+') {
            map[nx][ny] = 'W';

            moveUser(map, userPoint, nx, ny);
            return successBox;
        }

        // 1(userPoint) 2(nx, ny) 3(boxNx, boxNy)
        int boxNx = nx + d[0];
        int boxNy = ny + d[1];
        if(boxNx<0 || boxNx>=r || boxNy<0 || boxNy>=c || map[boxNx][boxNy] == '#' || map[boxNx][boxNy] == 'b')
            return successBox;

        if(map[boxNx][boxNy] == '.') {
            //3
            map[boxNx][boxNy] = 'b';
            //2
            if(map[nx][ny] == 'B') {
                map[nx][ny] = 'W';
                successBox--;
            }
            else map[nx][ny] = 'w';
            moveUser(map, userPoint, nx, ny);
            return successBox;
        }

        if(map[boxNx][boxNy] == '+') {
            //3
            map[boxNx][boxNy] = 'B';
            successBox++;
            //2
            if(map[nx][ny] == 'B') map[nx][ny] = 'W';
            else map[nx][ny] = 'w';
            //1
            moveUser(map, userPoint, nx, ny);
            return successBox;
        }
        return successBox;
    }

    private static void moveUser(char[][] map, int[] userPoint, int nx, int ny) {
        if(map[userPoint[0]][userPoint[1]] == 'W') {
            map[userPoint[0]][userPoint[1]] = '+';
        }
        else map[userPoint[0]][userPoint[1]] = '.';

        userPoint[0] = nx;
        userPoint[1] = ny;
    }

    private static int[] parseKey(char key) {
        if(key == 'U') return new int[] {-1, 0};
        if(key == 'D') return new int[] {1, 0};
        if(key == 'L') return new int[] {0, -1};
        return new int[] {0, 1};
    }
}
