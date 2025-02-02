import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private static int[][] d = {{-1,-1}, {-1,1}, {1,-1}, {1,1}};
    private static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n+1][n+1];
        for(int i=1; i<=n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] cloud = {{n,1}, {n,2}, {n-1,1}, {n-1,2}};
        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            moveCloud(cloud, direction, distance);

            rain(map, cloud);

            copyWater(map, cloud);

            cloud = makecloud(map, cloud);
        }

        int result = 0;
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=n; j++) {
                result += map[i][j];
            }
        }

        System.out.println(result);
    }

    private static void moveCloud(int[][] cloud, int direction, int distance) {
        int[] d = new int[2];

        switch(direction) {
            case 1:
                d[1] = -distance;
                break;
            case 2:
                d[0] = -distance;
                d[1] = -distance;
                break;
            case 3:
                d[0] = -distance;
                break;
            case 4:
                d[0] = -distance;
                d[1] = distance;
                break;
            case 5:
                d[1] = distance;
                break;
            case 6:
                d[0] = distance;
                d[1] = distance;
                break;
            case 7:
                d[0] = distance;
                break;
            case 8:
                d[0] = distance;
                d[1] = -distance;
                break;
        }

        for(int[] point : cloud) {
            point[0] += d[0];
            point[1] += d[1];

            fitPoint(point);
        }
    }

    private static void fitPoint(int[] point) {
        while(point[0] < 1) {
            point[0] += n;
        }
        while(point[0] > n) {
            point[0] -= n;
        }
        while(point[1] < 1) {
            point[1] += n;
        }
        while(point[1] > n) {
            point[1] -= n;
        }
    }

    private static void rain(int[][] map, int[][] cloud) {
        for(int[] point : cloud) {
            map[point[0]][point[1]]++;
        }
    }

    private static void copyWater(int[][] map, int[][] cloud) {
        for(int[] point: cloud) {
            int count = 0;
            for(int i=0; i<d.length; i++) {
                int nx = point[0]+d[i][0];
                int ny = point[1]+d[i][1];

                if(nx<1 || nx>n || ny<1 || ny>n || map[nx][ny] == 0) continue;
                count++;
            }
            map[point[0]][point[1]] += count;
        }
    }

    private static int[][] makecloud(int[][] map, int[][] cloud) {
        List<int[]> list = new ArrayList<>();
        for(int i=1; i<=n; i++) {
            for(int j=1; j<=n; j++) {
                if(map[i][j] < 2 || contains(cloud, i, j)) continue;
                list.add(new int[] {i, j});
                map[i][j] -= 2;
            }
        }
        return list.toArray(int[][]::new);
    }

    private static boolean contains(int[][] cloud, int i, int j) {
        for(int[] point : cloud) {
            if(point[0] == i && point[1] == j) return true;
        }
        return false;
    }

}