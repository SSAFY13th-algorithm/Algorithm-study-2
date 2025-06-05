import java.io.*;
import java.util.*;

public class Main {
    /*
     * 1. 집에 있는 모든 온풍기에서 바람이 한 번 나옴
     * 2. 온도가 조절됨
     * 3. 온도가 1 이상인 가장 바깥쪽 칸의 온도가 1씩 감소
     * 4. 초콜릿을 하나 먹는다.
     * 5. 조사하는 모든 칸의 온도가 K 이상이 되었는지 검사. 모든 칸의 온도가 K이상이면 테스트를 중단하고, 아니면 1부터 다시 시작한다.
     * */

    private static int r, c, k;
    //현재 온도를 저장하는 2차원 배열
    private static int[][] map;
    //온도를 체크할 좌표를 저장하는 리스트 {행, 열}
    private static List<int[]> target = new ArrayList<>();
    //머신의 정보를 저장하는 리스트 {행, 열, 방향}
    private static List<int[]> machines = new ArrayList<>();
    //머신의 방향에 따른 확산 방향 정보를 저장하는 3차원 배열
    private static int[][][] machineDir = {{},
            {{-1,1},{0,1},{1,1}}, //오른쪽
            {{-1,-1},{0,-1},{1,-1}},//왼쪽
            {{-1,-1},{-1,0},{-1,1}},//위
            {{1,-1},{1,0},{1,1}}};//아래
    //머신 작동으로 온도가 확산될 때 확인해야 하는 벽의 정보(0: 상, 1: 하, 2: 좌, 3: 우)
    //각 배열에서 1번째와 3번째는 확인해야하는 벽이 2개 인데,
    //이 중 첫번째는 확산하는 칸 기준 벽, 두번째는 확산당하는 칸 기준 벽을 의미
    //예를 들어 오른쪽의 1번째는 {0,3}인데 이는 확산하는 칸의 0(상)번 벽, 확산당하는 칸의 3(우)번 벽이 없어야 함을 의미
    private static int[][][] checkWall = {{},
            {{0,3},{1},{2,3}}, //오른쪽
            {{0,1},{3},{2,1}},//왼쪽
            {{3,2},{0},{1,2}},//위
            {{3,0},{2},{1,0}}};//아래
    //벽 정보를 저장하는 set 자료구조 ("행 열 벽 방향")
    //벽 방향(0:상, 1:우, 2:하, 3:좌)
    private static Set<String> wallSet = new HashSet<>();
    //온도 조절 시 방향 = 벽 방향
    private static int[][] d = {{-1,0},{0,1},{1,0},{0,-1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //초기화
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        //온도 체크할 위치 정보와 머신 정보 저장
        for(int i=0; i<r; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<c; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(num == 5) target.add(new int[] {i, j});
                else if(num > 0) machines.add(new int[] {i, j, num});
            }
        }

        //벽 정보 저장
        int n = Integer.parseInt(br.readLine());
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int dir = Integer.parseInt(st.nextToken());

            wallSet.add(x+" "+y+" "+dir);

            int nx = x+d[dir][0];
            int ny = y+d[dir][1];
            if(OOB(nx, ny)) continue;

            if(dir == 0) wallSet.add(nx+" "+ny+" "+2);
            else wallSet.add(nx+" "+ny+" "+3);
        }

        //현재 온도 전부 0으로 초기화
        map = new int[r][c];
        //초콜릿 섭취 횟수
        int count = 0;
        //반복 시작
        while(true) {
            turnOn();
            tempControl();
            edgeDown();
            count++;
            if(checkTemp()) break;
            if(count == 101) break;
        }

//        printMap();
        System.out.println(count);
    }

    //디버깅을 위한 맵 출력 메소드
    private static void printMap() {
        System.out.println("------------Map------------");
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    //집에 있는 모든 온풍기에서 바람이 한 번 나옴
    private static void turnOn() {
        for(int[] machine : machines) {
            int x = machine[0];
            int y = machine[1];
            int[][] dir = machineDir[machine[2]];
            int[][] wall = checkWall[machine[2]];

            int nx = x + dir[1][0];
            int ny = y + dir[1][1];
            if(OOB(nx, ny)) continue;

            boolean[][] visited = new boolean[r][c];
            Queue<int[]> q = new LinkedList<>();
            //{행, 열, 상승 온도}
            q.offer(new int[] {nx, ny, 5});
            map[nx][ny] += 5;
            visited[nx][ny] = true;

            while(!q.isEmpty()) {
                int[] info = q.poll();

                x = info[0];
                y = info[1];
                int up = info[2];

                //상승 온도가 1보다 작거나 같으면 확산 종료
                if(up <= 1) break;

                //예시
                //dir: {{1,-1},{1,0},{1,1}}};//아래
                //wall: {{3,0},{2},{1,0}}};//아래
                for(int i=0; i<dir.length; i++) {
                    nx = x + dir[i][0];
                    ny = y + dir[i][1];
                    //범위를 벗어나거나 방문했다면 패스
                    if(OOB(nx, ny) || visited[nx][ny]) continue;
                    //벽이 있어 확산할 수 없다면 패스
                    if(i != 1) {
                        if(wallSet.contains(x+" "+y+" "+wall[i][0])) continue;
                        if(wallSet.contains(nx+" "+ny+" "+wall[i][1])) continue;
                    }
                    else if(wallSet.contains(x+" "+y+" "+wall[i][0])) continue;

                    q.offer(new int[] {nx, ny, up-1});
                    map[nx][ny] += up-1;
                    visited[nx][ny] = true;
                }
            }
        }

    }

    //온도가 조절됨
    private static void tempControl() {
        //조절된 온도를 저장하는 임시 map
        int[][] temp = new int[r][c];

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                int temper = map[i][j];
                temp[i][j] += temper;
                for(int idx=0; idx<d.length; idx++) {
                    //벽이 있다면 해당 방향으로 조절 안 됨
                    if(wallSet.contains(i+" "+j+" "+idx)) continue;
                    int nx = i+d[idx][0];
                    int ny = j+d[idx][1];
                    //범위를 벗어나거나 해당 온도보다 낮다면 조절 안 됨
                    if(OOB(nx, ny) || map[nx][ny] >= temper) continue;
                    //조절되는 양
                    int decre = (temper-map[nx][ny])/4;
                    temp[nx][ny] += decre;
                    temp[i][j] -= decre;
                }
            }
        }

        map = temp;
    }

    //온도가 1 이상인 가장 바깥쪽 칸의 온도가 1씩 감소
    private static void edgeDown() {
        //좌우
        for(int i=0; i<r; i++) {
            if(map[i][0] >= 1) map[i][0]--;
            if(map[i][c-1] >= 1) map[i][c-1]--;
        }

        //상하
        for(int j=1; j<c-1; j++) {
            if(map[0][j] >= 1) map[0][j]--;
            if(map[r-1][j] >= 1) map[r-1][j]--;
        }
    }

    //조사하는 모든 칸의 온도가 K 이상이 되었는지 검사. 모든 칸의 온도가 K이상이면 테스트를 중단
    private static boolean checkTemp() {
        for(int[] point : target) {
            if(map[point[0]][point[1]] < k) return false;
        }

        return true;
    }


    //Out Of Bound
    private static boolean OOB(int x, int y) {
        if(x<0 || x>=r || y<0 || y>=c) return true;
        return false;
    }
}
