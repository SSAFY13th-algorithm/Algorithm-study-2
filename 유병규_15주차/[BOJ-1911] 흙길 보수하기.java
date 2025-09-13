import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        int[][] water = new int[n][2];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            water[i][0] = Integer.parseInt(st.nextToken());
            water[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(water, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        int result = 0;
        int pre = 0;
        for(int i=0; i<n; i++) {
            //널빤지의 끝 위치와 웅덩이의 시작 위치 중 더 큰 값으로 설정
            pre = Math.max(pre, water[i][0]);
            //하나씩 놓기
            while(pre < water[i][1]) {
                result++;
                pre += l;
            }
        }

        System.out.println(result);
    }
}
